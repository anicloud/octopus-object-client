package com.ani.client.agent.device.core.agent;


import com.ani.client.agent.device.core.account.Account;
import com.ani.client.agent.device.core.device.*;
import com.ani.client.agent.device.core.message.*;
import com.ani.client.agent.device.core.socket.IoHandler;
import com.ani.client.agent.device.core.socket.TcpClient;
import org.apache.log4j.Logger;
import sun.security.provider.MD5;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huangbin on 10/22/15.
 */

/**
 * The agent start connection, registry, auth procedures that are transparent to the device controller.
 * When the agent is ready it calls DeviceController.onReady(). And then the device business procedures start .
 * The whole events chain is as following: <br><br>
 * DeviceAgent.connect() --> DeviceController.getDeviceMaster() --> <br>
 * DeviceController.onUpdate() --> DeviceController.onReady() --> <br>
 * ...<br>
 * DeviceAgent.invokeAsync() --> DeviceController.onInvokeDone() --><br>
 * ...<br>
 * DeviceController.invokeASync() --> DeviceAgent.onInvokeDone() --><br>
 * ...<br>
 */
public class DeviceAgent implements Invokable, InvokeCallback {
    private static Logger LOG = Logger.getLogger(DeviceAgent.class);

    private Map<Long, FunctionInstance> instanceMap = new ConcurrentHashMap<>();

    private DeviceController controller;
    private final DeviceMaster deviceMaster;

    private MessageHandler messageHandler;
    private IoHandler ioHandler;

    private State state;
    private Error error;

    private enum Error {
        ERROR_NONE,
        ERROR_CONNECT,
        ERROR_REGISTER,
        ERROR_AUTH,
        ERROR_UPDATE,
        ERROR_CLOSE,
        ERROR_INVOKE,
        ERROR_UNKNOWN
    }

    private enum State {
        STATE_INIT,
        STATE_CONNECTING,
        STATE_CONNECTED,
        STATE_REGISTERING,
        STATE_REGISTERED,
        STATE_AUTHING,
        STATE_AUTHED,
        STATE_UPDATING,
        STATE_UPDATED,
        STATE_CLOSING,
        STATE_CLOSED
    }

    public DeviceAgent(DeviceController controller, DeviceMaster deviceMaster) {
        this.messageHandler = new MessageHandlerImpl();
        this.ioHandler = new IoHandler(new TcpClient());
        this.ioHandler.setMessageHandler(this.messageHandler);
        this.controller = controller;
        this.state = State.STATE_INIT;
        this.error = Error.ERROR_NONE;
        this.deviceMaster = deviceMaster;
    }

    /*
    *
    *   APIs of DeviceAgent
    *
    * */

    /**
     * Connect to server.
     */
    public void connect() {
        if (state == State.STATE_INIT || state == State.STATE_CLOSED) {
            state = State.STATE_CONNECTING;
            error = Error.ERROR_NONE;
            String host = "localhost";
            Integer port = 1222;
            try {
                ioHandler.getClient().connect(host, port);
            } catch (IOException e) {
                state = State.STATE_CLOSED;
                error = Error.ERROR_CONNECT;
                LOG.info(e);
//                e.printStackTrace();
                onStateChanged();
            }
        }
    }

    /**
     * Close connection.
     */
    public void close() {
        try {
            state = State.STATE_CLOSING;
            ioHandler.getClient().close();
        } catch (IOException e) {
            error = Error.ERROR_CLOSE;
            LOG.info(e);
            onStateChanged();
        }
    }

    /**
     * Update device master.
     *
     */

    public void update() {
        LOG.info("device update start");
        state = State.STATE_UPDATING;
        MessageContent content = new ContentUpdateRequest(deviceMaster);
        Message message = new Message(MessageType.UPDATE_REQUEST, content);
        sendMessage(message);
        onStateChanged();
    }

    /**
     * Create a FunctionInstance.
     *
     * @param function
     * @param accounts
     * @param inputArgValues
     * @param outputArgValues
     * @return
     */
    public FunctionInstance createFunctionInstance(Long deviceId, Function function, List<Account> accounts, List<Argument> inputArgValues, List<Argument> outputArgValues) {
        Long createTime = System.currentTimeMillis();
        Random random = new Random(createTime + deviceId + function.getId() + function.getGroupId());
        Long id = Math.abs(random.nextLong());
        while (instanceMap.containsKey(id)) {
            id = Math.abs(random.nextLong());
        }
        FunctionInstance instance = new FunctionInstance(id, createTime, deviceId, function, accounts, inputArgValues, outputArgValues);
        return instance;
    }

    /**
     * Implements of MessageHandler
     */
    private class MessageHandlerImpl implements MessageHandler {

        public void onMessage(Message message) {
            switch (message.type) {
                case REGISTER_RESPONSE:
                    if (state == State.STATE_REGISTERING) {
                        onRegisterResponse(message);
                    }
                    break;
                case AUTH_RESPONSE:
                    if (state == State.STATE_AUTHING) {
                        onAuthResponse(message);
                    }
                    break;
                case UPDATE_RESPONSE:
                    if (state == State.STATE_UPDATING) {
                        onUpdateResponse(message);
                    }
                    break;
                case INVOKE_REQUEST:
                    onInvokeRequest(message);
                    break;
                case INVOKE_RESPONSE:
                    onInvokeResponse(message);
                    break;
                default:
                    break;
            }
        }

        public void onConnect() {
            state = State.STATE_CONNECTED;
            onStateChanged();
        }

        public void onClose() {
            clear();
            state = State.STATE_CLOSED;
            onStateChanged();
        }
    }

    /*
    *
    *   Implements of Invokable
    *
    * */
    public void invokeSync(FunctionInstance instance, Long timeout) {
        try {
            Long startTime = System.currentTimeMillis();
            instance.setStartTime(startTime);
            ContentInvokeRequest content = new ContentInvokeRequest(instance);
            Message message = new Message(MessageType.INVOKE_REQUEST, content);
            sendMessage(message);
            instanceMap.put(instance.getInstanceId(), instance);
            synchronized (instance) {
                instance.wait(timeout);
            }
        } catch (Exception e) {
            instance.setResult(ResultType.ERROR);
            e.printStackTrace();
        } finally {
            instanceMap.remove(instance.getInstanceId());
        }
    }

    public void invokeAsync(FunctionInstance instance) {
        Long startTime = System.currentTimeMillis();
        instance.setStartTime(startTime);
        instanceMap.put(instance.getInstanceId(), instance);

        ContentInvokeRequest content = new ContentInvokeRequest(instance);
        Message message = new Message(MessageType.INVOKE_REQUEST, content);
        sendMessage(message);
    }

    /*
    *
    *   Implements of InvokeHandler
    *
    * */
    public void onInvokeDone(FunctionInstance instance) {
        ContentInvokeResponse contentResponse = new ContentInvokeResponse(instance.getResult(), instance);
        Message messageResponse = new Message(MessageType.INVOKE_RESPONSE, contentResponse);
        sendMessage(messageResponse);
    }

    /*
    *
    *   Local helper functions
    *
    * */
    private void onStateChanged() {
        if (error != Error.ERROR_NONE) {
            switch (error) {
                case ERROR_REGISTER:
                    controller.onError(DeviceController.AgentError.AGENT_ERROR_REGISTER);
                case ERROR_AUTH:
                    controller.onError(DeviceController.AgentError.AGENT_ERROR_AUTH);
                    break;
                case ERROR_UPDATE:
                    controller.onError(DeviceController.AgentError.AGENT_ERROR_UPDATE);
                    break;
                case ERROR_INVOKE:
                    controller.onError(DeviceController.AgentError.AGENT_ERROR_INVOKE);
                    break;
                case ERROR_CONNECT:
                case ERROR_CLOSE:
                    controller.onError(DeviceController.AgentError.AGENT_ERROR_CONNECT);
                    break;
                case ERROR_UNKNOWN:
                    break;
                default:
                    break;
            }
        } else {
            switch (state) {
                case STATE_INIT:
                    break;
                case STATE_CONNECTING:
                    break;
                case STATE_CONNECTED:
                    register();
                    break;
                case STATE_REGISTERING:
                    break;
                case STATE_REGISTERED:
                    auth();
                    break;
                case STATE_AUTHING:
                    break;
                case STATE_AUTHED:
                    update();
                    break;
                case STATE_UPDATING:
                    break;
                case STATE_UPDATED:
                    controller.onUpdate();
                    controller.onReady();
                    break;
                case STATE_CLOSED:
                    controller.onClose();
                    break;
                default:
                    break;
            }
        }
    }

    private void sendMessage(Message message) {
        try {
            ioHandler.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void register() {
        LOG.info("device register start");
        if (deviceMaster.getDeviceId() != -1l && deviceMaster.getToken() != null) {
            LOG.info("device register ok, already registered");
            state = State.STATE_REGISTERED;
        } else {
            state = State.STATE_REGISTERING;
            MessageContent content = new ContentRegisterRequest(
                    deviceMaster.getName(),
                    deviceMaster.getPhysicalId(),
                    deviceMaster.getPhysicalAddress(),
                    deviceMaster.getDescription());
            Message message = new Message(MessageType.REGISTER_REQUEST, content);
            sendMessage(message);
        }
        onStateChanged();
    }

    private void onRegisterResponse(Message message) {
        ContentRegisterResponse content = (ContentRegisterResponse) message.content;
        if (content.result == ResultType.ERROR) {
            LOG.info("device register error");
            error = Error.ERROR_REGISTER;
        } else if (content.result == ResultType.SUCCESS) {
            LOG.info("device register ok");
            deviceMaster.setDeviceId(content.deviceId);
            deviceMaster.setToken(content.token);
            controller.onUpdate();
            state = State.STATE_REGISTERED;
        }
        onStateChanged();
    }

    private byte[] signature(Long timestamp, byte[] token) {
        // String sign = null;
        byte[] sign = null;
        byte[] key = new byte[8];
//        LOG.info("timestamp: " + timestamp);
        for (int i=0; i<key.length; i++) {
            key[i] = (byte) ((timestamp >> (7-i)*8) & 0xff);
//            LOG.info("\t[" + i + "]" + key[i]);
        }
//        LOG.info("token: ");
//        for (int i=0; i<token.length; i++) {
//            LOG.info("\t[" + i + "]" + token[i]);
//        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacMD5");
        try {
            Mac mac = Mac.getInstance(secretKeySpec.getAlgorithm());
            mac.init(secretKeySpec);
            byte[] rawHmac = mac.doFinal(token);
            sign = rawHmac;
//            StringBuffer strHmac = new StringBuffer();
//            for (int i = 0; i < rawHmac.length; i++) {
//                strHmac.append(Integer.toHexString(rawHmac[i]));
//            }
//            sign = strHmac.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return sign;
        }
    }

    private void auth() {
        LOG.info("device auth start");
        state = State.STATE_AUTHING;
        Long timestamp = System.currentTimeMillis();
        byte[] sign = signature(timestamp, deviceMaster.getToken());
        MessageContent content = new ContentAuthRequest(deviceMaster.getDeviceId(), timestamp, sign);
        Message message = new Message(MessageType.AUTH_REQUEST, content);
        sendMessage(message);
        onStateChanged();
    }

    private void onAuthResponse(Message message) {
        ContentAuthResponse content = (ContentAuthResponse) message.content;
        if (content.result == ResultType.ERROR) {
            LOG.info("device auth error");
            error = Error.ERROR_AUTH;
        } else if (content.result == ResultType.SUCCESS) {
            LOG.info("device auth ok");
            state = State.STATE_AUTHED;
        }
        onStateChanged();
    }

    private void mergeDeviceMaster(DeviceMaster deviceMasterRemote) {
        // update name
        deviceMaster.setName(deviceMasterRemote.getName());
        // update description
        deviceMaster.setDescription(deviceMasterRemote.getDescription());
        // update owner
        deviceMaster.setOwner(deviceMasterRemote.getOwner());
        // update accounts
        deviceMaster.setAccounts(deviceMasterRemote.getAccounts());
        // update slaves
        deviceMaster.setSlaves(deviceMasterRemote.getSlaves());
    }

    private void onUpdateResponse(Message message) {
        ContentUpdateResponse content = (ContentUpdateResponse) message.content;
        if (content.result == ResultType.ERROR) {
            LOG.warn("device update error");
            error = Error.ERROR_UPDATE;
        } else if (content.result == ResultType.SUCCESS) {
            LOG.info("device update ok");
            mergeDeviceMaster(content.deviceMaster);
            controller.onUpdate();
            state = State.STATE_UPDATED;
        }
        onStateChanged();
    }

    private void onInvokeRequest(Message message) {
        LOG.info("invoke request");
        ContentInvokeRequest content = (ContentInvokeRequest) message.content;
        FunctionInstance instance = content.instance;
        instance.setInvokeCallback(this);
        try {
            if (instance.getFunction().getType() == ConnType.SYNC) {
                controller.invokeSync(instance, (long) 5000);
                ContentInvokeResponse contentResponse = new ContentInvokeResponse(instance.getResult(), instance);
                Message messageResponse = new Message(MessageType.INVOKE_RESPONSE, contentResponse);
                sendMessage(messageResponse);
            } else {
                controller.invokeAsync(instance);
            }
        } catch (Exception e) {
            LOG.info("invoke request failed");
            ContentInvokeResponse contentResponse = new ContentInvokeResponse(ResultType.ERROR, instance);
            Message messageResponse = new Message(MessageType.INVOKE_RESPONSE, contentResponse);
            sendMessage(messageResponse);
            e.printStackTrace();
        }
    }

    private void onInvokeResponse(Message message) {
        LOG.info("invoke response");
        ContentInvokeResponse content = (ContentInvokeResponse) message.content;
        FunctionInstance instanceResponse = content.instance;
        try {
            if (instanceMap.containsKey(instanceResponse.getInstanceId())) {
                FunctionInstance instanceLocal = instanceMap.remove(instanceResponse.getInstanceId());
                instanceLocal.setResult(content.result);
                instanceLocal.setOutputValues(instanceResponse.getOutputValues());
                if (instanceLocal.getFunction().getType() == ConnType.SYNC) {
                    synchronized (instanceLocal) {
                        instanceLocal.notify();
                    }
                } else if (instanceLocal.getFunction().getType() == ConnType.ASYNC) {
                    instanceLocal.getInvokeCallback().onInvokeDone(instanceLocal);
                }
            } else {
                LOG.info("invalid function instance: " + instanceResponse.getInstanceId());
            }

        } catch (Exception e) {
            LOG.info("invoke response failed");
            e.printStackTrace();
        }
    }

    private void clear() {
        try {
            //  clear function instance context
            Iterator<Map.Entry<Long, FunctionInstance>> it = instanceMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Long, FunctionInstance> entry = it.next();
                FunctionInstance instance = entry.getValue();
                it.remove();

                instance.setResult(ResultType.ERROR);
                if (instance.getFunction().getType() == ConnType.ASYNC) {
                    instance.getInvokeCallback().onInvokeDone(instance);
                } else {
                    synchronized (instance) {
                        instance.notifyAll();
                    }
                }
            }
        } catch (Exception e) {
            LOG.warn("clear session " + deviceMaster.getDeviceId() + " context failed");
            e.printStackTrace();
        }
    }

}
