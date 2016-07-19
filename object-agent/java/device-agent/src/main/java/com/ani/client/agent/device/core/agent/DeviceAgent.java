package com.ani.client.agent.device.core.agent;


import com.ani.bus.device.commons.dto.device.ArgumentDto;
import com.ani.bus.device.commons.dto.device.FunctionDto;
import com.ani.bus.device.commons.dto.device.FunctionInstance;
import com.ani.bus.device.commons.dto.message.*;
import com.ani.client.agent.device.core.device.DeviceMaster;
import com.ani.client.agent.device.core.device.DeviceSlave;
import com.ani.client.agent.device.core.socket.DeviceMessageHandler;
import com.ani.client.agent.device.core.socket.IoHandler;
import com.ani.client.agent.device.core.socket.TcpClient;
import org.apache.log4j.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huangbin on 10/22/15.
 */

/**
 * The agent starts up sequence:<br>
 * <br>
 *     Connect(): connect to the server.<br>
 *        .<br>
 *        .<br>
 *     Register(): register the device.<br>
 *        .<br>
 *        .<br>
 *     Auth(): authenticate the device.<br>
 *        .<br>
 *        .<br>
 *     Update() / InvokeAsync() / InvokeSync(): ready to do normal businesses.
 *
  */
public class DeviceAgent implements Invokable, InvokeCallback {
    private static Logger LOG = Logger.getLogger(DeviceAgent.class);

    private Map<Long, FunctionInstance> instanceMap = new ConcurrentHashMap<>();
    private Map<Long, InvokeCallback> callbackMap = new ConcurrentHashMap<>();

    private DeviceController controller;
    private final DeviceMaster deviceMaster;

    private DeviceMessageHandler messageHandler;
    private IoHandler ioHandler;

    private State state;
    private Error error;

    public enum Error {
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
        STATE_CLOSING,
        STATE_CLOSED
    }

    public DeviceAgent(DeviceController controller, DeviceMaster deviceMaster) {
        this.messageHandler = new DeviceMessageHandlerImpl();
        this.ioHandler = new IoHandler(new TcpClient());
        this.ioHandler.setMessageHandler(this.messageHandler);
        this.controller = controller;
        this.deviceMaster = deviceMaster;
        this.state = State.STATE_INIT;
        this.error = Error.ERROR_NONE;
    }

    /*
    *
    *   APIs of DeviceAgent
    *
    * */

    /**
     * Connect to the server.
     *   Success: DeviceController::onConnect()
     *   otherwise: DeviceController::onError(ERROR_CONNECT)
     */
    public void connect() {
        if (state == State.STATE_INIT || state == State.STATE_CLOSED) {
            state = State.STATE_CONNECTING;
            error = Error.ERROR_NONE;
            String host = "bj-yatsen.anicel.cn";
            Integer port = 1222;
            try {
                ioHandler.getClient().connect(host, port);
            } catch (IOException e) {
                state = State.STATE_CLOSED;
                error = Error.ERROR_CONNECT;
                controller.onError(Error.ERROR_CONNECT);
//                controller.onClose();
                LOG.info(e);
            }
        }
    }

    /**
     * Register the device while connecting to the server at the first time.
     *   Success: DeviceController::onRegister()
     *   otherwise: DeviceController::onError(ERROR_REGISTER)
     */
    public void register() {
        if (deviceMaster.getDeviceId() != -1l && deviceMaster.getToken() != null) {
            LOG.info("device state: already registered");
            state = State.STATE_REGISTERED;
            controller.onRegister();
        } else {
            LOG.info("device state: registering");
            state = State.STATE_REGISTERING;
            DeviceMessageContent content = new RegisterRequestContent(
                    deviceMaster.getPhysicalId(),
                    deviceMaster.getPhysicalAddress(),
                    deviceMaster.getName(),
                    deviceMaster.getDescription(),
                    deviceMaster.getAvatarUrl(),
                    deviceMaster.getTags(),
                    deviceMaster.getVersionId());
            DeviceMessage message = new DeviceMessage(DeviceMessageType.REGISTER_REQUEST, content);
            sendMessage(message);
        }
    }

    /**
     * Auth the device.
     *   Success: DeviceController::onAuth()
     *   otherwise: DeviceController::onError(ERROR_AUTH)
     */
    public void auth() {
        LOG.info("device state: authing");
        state = State.STATE_AUTHING;
        Long timestamp = System.currentTimeMillis();
        byte[] sign = signature(timestamp, deviceMaster.getToken());
        DeviceMessageContent content = new AuthRequestContent(deviceMaster.getDeviceId(), timestamp, sign);
        DeviceMessage message = new DeviceMessage(DeviceMessageType.AUTH_REQUEST, content);
        sendMessage(message);
    }

    /**
     * Close connection.
     *   Success: DeviceController::onClose()
     *   otherwise: DeviceController::onError(ERROR_CLOSE)
     */
    public void close() {
        if (state == State.STATE_INIT ||
                state == State.STATE_CLOSING ||
                state == State.STATE_CLOSED) {
            return;
        }
        try {
            state = State.STATE_CLOSING;
            ioHandler.getClient().close();
            controller.onClose();
        } catch (IOException e) {
            error = Error.ERROR_CLOSE;
            controller.onError(Error.ERROR_CLOSE);
            LOG.info(e);
        }
    }

    /**
     * Update device master.
     *   Success: do nothing.
     *   otherwise: DeviceController::onError(ERROR_CONNECT)
     */
    public void update() {
        LOG.info("device update start");
        DeviceMessageContent content = new UpdateRequestContent(deviceMaster.toDto());
        DeviceMessage message = new DeviceMessage(DeviceMessageType.UPDATE_REQUEST, content);
        sendMessage(message);
    }

    /**
     * @param deviceId
     * @param slaveId
     * @param async
     * @param function
     * @param inputValues
     * @param outputValues
     * @return
     */
    public FunctionInstance createFunctionInstance(Long deviceId, Integer slaveId, Boolean async, FunctionDto function, List<ArgumentDto> inputValues, List<ArgumentDto> outputValues) {
        Long createTime = System.currentTimeMillis();
        Random rand = new Random(createTime + deviceId + function.functionId + function.groupId);
        Long instanceId = Math.abs(rand.nextLong());
        while (instanceMap.containsKey(instanceId)) {
            instanceId = Math.abs(rand.nextLong());
        }
        return new FunctionInstance(instanceId, createTime, deviceId, slaveId, async, function, inputValues, outputValues);
    }

    /**
     *  Implement of Invokable.
     */
    @Override
    public void invokeSync(FunctionInstance instance, Long timeout) {
        try {
            instance.startTime = System.currentTimeMillis();
            InvokeRequestContent content = new InvokeRequestContent(instance);
            DeviceMessage message = new DeviceMessage(DeviceMessageType.INVOKE_REQUEST, content);
            sendMessage(message);
            instanceMap.put(instance.instanceId, instance);
            synchronized (instance.lock) {
                instance.lock.wait(timeout);
            }
        } catch (Exception e) {
            instance.result = false;
            LOG.info(e);
        } finally {
            instanceMap.remove(instance.instanceId);
        }
    }

    @Override
    public void invokeAsync(FunctionInstance instance, InvokeCallback callback) {
        instance.startTime = System.currentTimeMillis();
        instanceMap.put(instance.instanceId, instance);
        callbackMap.put(instance.instanceId, callback);
        InvokeRequestContent content = new InvokeRequestContent(instance);
        DeviceMessage message = new DeviceMessage(DeviceMessageType.INVOKE_REQUEST, content);
        sendMessage(message);
    }

    /*
    * Implement of InvokeHandler
    */
    public void onInvokeDone(FunctionInstance instance) {
        InvokeResponseContent contentResponse = new InvokeResponseContent(instance.result, instance);
        DeviceMessage messageResponse = new DeviceMessage(DeviceMessageType.INVOKE_RESPONSE, contentResponse);
        sendMessage(messageResponse);
    }

    /**
     * Implement of DeviceMessageHandler
     */
    private class DeviceMessageHandlerImpl implements DeviceMessageHandler {

        public void onMessage(DeviceMessage message) throws IOException {
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
                    if (state == State.STATE_AUTHED) {
                        onUpdateResponse(message);
                    }
                    break;
                case INVOKE_REQUEST:
                    if (state == State.STATE_AUTHED) {
                        onInvokeRequest(message);
                    }
                    break;
                case INVOKE_RESPONSE:
                    if (state == State.STATE_AUTHED) {
                        onInvokeResponse(message);
                    }
                    break;
                default:
                    break;
            }
        }

        public void onConnect() throws IOException {
            state = State.STATE_CONNECTED;
            controller.onConnect();
        }

        public void onClose() throws IOException {
            clear();
            state = State.STATE_CLOSED;
            controller.onClose();
        }

        @Override
        public void onTimeout() throws IOException {

        }
    }

    /*
    *
    *   Local helper functions
    *
    */
    private void sendMessage(DeviceMessage message) {
        try {
            LOG.info("send message to server.");
            ioHandler.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void onRegisterResponse(DeviceMessage message) {
        RegisterResponseContent content = (RegisterResponseContent) message.content;
        if (!content.result) {
            LOG.info("device register error");
            error = Error.ERROR_REGISTER;
            controller.onError(Error.ERROR_REGISTER);
        } else {
            LOG.info("device state: registered");
            deviceMaster.setDeviceId(content.deviceId);
            deviceMaster.setToken(content.token);
            controller.onRegister();
            state = State.STATE_REGISTERED;
        }
    }

    private byte[] signature(Long timestamp, byte[] token) {
        byte[] sign = null;
        byte[] key = new byte[8];
        for (int i = 0; i < key.length; i++) {
            key[i] = (byte) ((timestamp >> (7 - i) * 8) & 0xff);
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacMD5");
        try {
            Mac mac = Mac.getInstance(secretKeySpec.getAlgorithm());
            mac.init(secretKeySpec);
            byte[] rawHmac = mac.doFinal(token);
            sign = rawHmac;
        } catch (Exception e) {
            LOG.info(e);
        } finally {
            return sign;
        }
    }


    private void onAuthResponse(DeviceMessage message) {
        AuthResponseContent content = (AuthResponseContent) message.content;
        if (content.result) {
            LOG.info("device state: authed");
            state = State.STATE_AUTHED;
            mergeDeviceMaster(new DeviceMaster(content.dto));
            controller.onAuth();
        } else {
            LOG.info("device auth error");
            error = Error.ERROR_AUTH;
            controller.onError(Error.ERROR_AUTH);
        }
    }

    private void mergeDeviceMaster(DeviceMaster deviceMasterRemote) {
        if (deviceMasterRemote.getLastModifiedTime() > deviceMaster.getLastModifiedTime()) {
            deviceMaster.setName(deviceMasterRemote.getName());
            deviceMaster.setDescription(deviceMasterRemote.getDescription());
            deviceMaster.setOwner(deviceMasterRemote.getOwner());
            deviceMaster.setAccountGroups(deviceMasterRemote.getAccountGroups());
            deviceMaster.setAvatarUrl(deviceMasterRemote.getAvatarUrl());
            deviceMaster.setTags(deviceMasterRemote.getTags());
            if (deviceMasterRemote.getSlaves() != null && deviceMaster.getSlaves() != null) {
                for (DeviceSlave deviceSlaveRemote : deviceMasterRemote.getSlaves()) {
                    for (DeviceSlave deviceSlave : deviceMaster.getSlaves()) {
                        if (deviceSlave.getDeviceId() == deviceSlaveRemote.getDeviceId()) {
                            deviceSlave.setName(deviceSlaveRemote.getName());
                            deviceSlave.setDescription(deviceSlaveRemote.getName());
                            deviceSlave.setAvatarUrl(deviceSlaveRemote.getAvatarUrl());
                            deviceSlave.setTags(deviceSlaveRemote.getTags());
                            break;
                        }
                    }
                }
            }
            deviceMaster.setLastModifiedTime(deviceMasterRemote.getLastModifiedTime());
        }
    }

    private void onUpdateResponse(DeviceMessage message) {
        UpdateResponseContent content = (UpdateResponseContent) message.content;
        if (content.result) {
            LOG.info("device update ok");
            deviceMaster.setLastModifiedTime(content.dto.lastModifiedTime);
            controller.onUpdate();
        } else {
            LOG.warn("device update error");
            error = Error.ERROR_UPDATE;
        }
    }

    private void onInvokeRequest(DeviceMessage message) {
        LOG.info("invoke request");
        InvokeRequestContent content = (InvokeRequestContent) message.content;
        FunctionInstance instance = content.instance;
        try {
            if (instance.async) {
                controller.invokeAsync(instance, this);
            } else {
                controller.invokeSync(instance, (long) 5000);
                InvokeResponseContent contentResponse = new InvokeResponseContent(instance.result, instance);
                DeviceMessage messageResponse = new DeviceMessage(DeviceMessageType.INVOKE_RESPONSE, contentResponse);
                sendMessage(messageResponse);
            }
        } catch (Exception e) {
            LOG.info("invoke request failed", e);
            InvokeResponseContent contentResponse = new InvokeResponseContent(false, instance);
            DeviceMessage messageResponse = new DeviceMessage(DeviceMessageType.INVOKE_RESPONSE, contentResponse);
            sendMessage(messageResponse);
        }
    }

    private void onInvokeResponse(DeviceMessage message) {
        LOG.info("invoke response");
        InvokeResponseContent content = (InvokeResponseContent) message.content;
        FunctionInstance instanceResponse = content.instance;
        try {
            if (instanceMap.containsKey(instanceResponse.instanceId)) {
                FunctionInstance instanceLocal = instanceMap.remove(instanceResponse.instanceId);
                instanceLocal.result = content.result;
                instanceLocal.outputValues = instanceResponse.outputValues;
                if (instanceLocal.async) {
                    InvokeCallback callback = callbackMap.remove(instanceLocal.instanceId);
                    if (callback != null) {
                        callback.onInvokeDone(instanceLocal);
                    }
                } else {
                    synchronized (instanceLocal.lock) {
                        instanceLocal.lock.notify();
                    }
                }
            } else {
                LOG.info("invalid function instance: " + instanceResponse.instanceId);
            }
        } catch (Exception e) {
            LOG.info("invoke response failed", e);
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

                instance.result = false;
                if (instance.async) {
                    InvokeCallback callback = callbackMap.remove(instance.instanceId);
                    if (callback != null) {
                        callback.onInvokeDone(instance);
                    }
                } else {
                    synchronized (instance.lock) {
                        instance.lock.notifyAll();
                    }
                }
            }
        } catch (Exception e) {
            LOG.warn("clear device session context of " + deviceMaster.getDeviceId() + " failed", e);
        }
    }

}
