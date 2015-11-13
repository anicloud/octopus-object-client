package com.ani.client.agent.device.core.agent;


import com.ani.client.agent.device.core.account.Account;
import com.ani.client.agent.device.core.device.*;
import com.ani.client.agent.device.core.message.*;
import com.ani.client.agent.device.core.socket.IoHandler;
import com.ani.client.agent.device.core.socket.TcpClient;
import org.apache.log4j.Logger;

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
    private DeviceMaster deviceMaster;
    private byte[] token;

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
            String host = "localhost";
            Integer port = 1222;
            try {
                ioHandler.getClient().connect(host, port);
            } catch (Exception e) {
                error = Error.ERROR_CONNECT;
                e.printStackTrace();
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
        } catch (Exception e) {
            e.printStackTrace();
            error = Error.ERROR_CLOSE;
            onStateChanged();
        }
    }

    /**
     * Update device master.
     *
     * @param master
     */
    public void update(DeviceMaster master) {
        this.deviceMaster = master;
        update();
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
            synchronized (this) {
                instanceMap.put(instance.getInstanceId(), instance);
                instance.wait(timeout);
            }
        } catch (Exception e) {
            instance.setResult(ResultType.ERROR);
            e.printStackTrace();
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
                case ERROR_CONNECT:
                case ERROR_REGISTER:
                case ERROR_AUTH:
                    controller.onError(DeviceController.AgentError.AGENT_ERROR_CONNECT);
                    break;
                case ERROR_UPDATE:
                    controller.onError(DeviceController.AgentError.AGENT_ERROR_UPDATE);
                    break;
                case ERROR_INVOKE:
                    break;
                case ERROR_CLOSE:
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
            MessageContent content = new ContentRegisterRequest(
                    deviceMaster.getName(),
                    deviceMaster.getPhysicalId(),
                    deviceMaster.getPhysicalAddress(),
                    deviceMaster.getDescription());
            Message message = new Message(MessageType.REGISTER_REQUEST, content);
            sendMessage(message);
            state = State.STATE_REGISTERING;
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
            state = State.STATE_REGISTERED;
        }
        onStateChanged();
    }

    private String signature(Long deviceId, Long timestamp, byte[] token) {
//        TODO: device signature
        String sign = "test-signature";
        return sign;
    }

    private void auth() {
        LOG.info("device auth start");
        Long timestamp = System.currentTimeMillis();
        String sign = signature(deviceMaster.getDeviceId(), timestamp, token);
        MessageContent content = new ContentAuthRequest(deviceMaster.getDeviceId(), timestamp, sign);
        Message message = new Message(MessageType.AUTH_REQUEST, content);
        sendMessage(message);
        state = State.STATE_AUTHING;
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

    private void update() {
        LOG.info("device update start");
        MessageContent content = new ContentUpdateRequest(deviceMaster);
        Message message = new Message(MessageType.UPDATE_REQUEST, content);
        sendMessage(message);
        state = State.STATE_UPDATING;
        onStateChanged();
    }

    private void onUpdateResponse(Message message) {
        ContentUpdateResponse content = (ContentUpdateResponse) message.content;
        if (content.result == ResultType.ERROR) {
            LOG.warn("device update error");
            error = Error.ERROR_UPDATE;
        } else if (content.result == ResultType.SUCCESS) {
            LOG.info("device update ok");
            DeviceMaster deviceMasterUpdated = content.deviceMaster;
//            TODO: check and update the consistence between local and remote device master info.
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
        FunctionInstance instanceUpdated = content.instance;
        try {
            if (instanceMap.containsKey(instanceUpdated.getInstanceId())) {
                synchronized (this) {
                    FunctionInstance instanceLocal = instanceMap.remove(instanceUpdated.getInstanceId());
                    instanceLocal.setResult(content.result);
                    instanceLocal.setOutputValues(instanceUpdated.getOutputValues());
                    if (instanceUpdated.getFunction().getType() == ConnType.SYNC) {
                        instanceLocal.notify();
                    } else if (instanceUpdated.getFunction().getType() == ConnType.ASYNC) {
                        instanceLocal.getInvokeCallback().onInvokeDone(instanceLocal);
                    }
                }
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
