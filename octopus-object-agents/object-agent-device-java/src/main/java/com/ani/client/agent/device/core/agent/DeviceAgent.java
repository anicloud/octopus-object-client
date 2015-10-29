package com.ani.client.agent.device.core.agent;

import com.ani.bus.device.core.domain.device.*;
import com.ani.bus.device.core.domain.message.*;

import com.ani.client.agent.device.core.socket.IoHandler;
import com.ani.client.agent.device.core.socket.TcpClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by huangbin on 10/22/15.
 */
public class DeviceAgent implements MessageHandler, Invokable, InvokeHandler {
    private Map<Long, FunctionInstance> instanceMap = new HashMap<Long, FunctionInstance>();

    private DeviceController controller;
    private DeviceMaster deviceMaster;

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

    public enum State {
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
        STATE_CLOSED,
        STATE_IDLE,
        STATE_ERROR
    }

    public DeviceAgent(DeviceController controller) {
        this.ioHandler = new IoHandler(new TcpClient());
        this.ioHandler.setMessageHandler(this);
        this.controller = controller;
        this.deviceMaster = controller.getDeviceInfo();
        this.state = State.STATE_INIT;
        this.error = Error.ERROR_NONE;
    }

    /*
    *
    *   APIs of DeviceAgent
    *
    * */

    public void connect() {
        try {
            String host = "localhost";
            Integer port = 1222;
            ioHandler.getClient().connect(host, port);
            ioHandler.getClient().startLoop();
            state = State.STATE_CONNECTING;
            onStateChanged();
        } catch (Exception e) {
            error = Error.ERROR_CONNECT;
            state = State.STATE_ERROR;
            onStateChanged();
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            ioHandler.getClient().close();
            state = State.STATE_CLOSING;
            onStateChanged();
        } catch (Exception e) {
            error = Error.ERROR_CLOSE;
            state = State.STATE_ERROR;
            onStateChanged();
            e.printStackTrace();
        }
    }

    /*
    *
    *  Implements of MessageHandler
    *
    * */
    public void onMessage(Message message) {
        switch (message.type) {
            case REGISTER_RESPONSE:
                onRegisterResponse(message);
                break;
            case AUTH_RESPONSE:
                onAuthResponse(message);
                break;
            case UPDATE_RESPONSE:
                onUpdateResponse(message);
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
        state = State.STATE_CLOSED;
        onStateChanged();
    }

    /*
    *
    *   Implements of Invokable
    *
    * */

    public void invokeMethodSync(FunctionInstance instance, Long timeout) throws Exception {
        Long startTime = System.currentTimeMillis();
        instance.setStartTime(startTime);
        ContentInvokeRequest content = new ContentInvokeRequest(instance);
        Message message = new Message(MessageType.INVOKE_REQUEST, content);
        sendMessage(message);
        synchronized (this) {
            instanceMap.put(instance.getInstanceId(), instance);
            instance.wait(timeout);
        }
    }

    public void invokeMethodAsync(FunctionInstance instance, InvokeHandler callback) throws Exception {
        Long startTime = System.currentTimeMillis();
        instance.setStartTime(startTime);
        ContentInvokeRequest content = new ContentInvokeRequest(instance);
        Message message = new Message(MessageType.INVOKE_REQUEST, content);
        sendMessage(message);
        synchronized (this) {
            instanceMap.put(instance.getInstanceId(), instance);
        }
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

    public FunctionInstance createFunctionInstance(Function function, List<Argument> inputArgValues, List<Argument> outputArgValues) {
        Random random = new Random();
        Long createTime = System.currentTimeMillis();
        Long id = createTime + function.getId() + random.nextLong();
        FunctionInstance instance = new FunctionInstance(id, deviceMaster.getDeviceId(), createTime, function, inputArgValues, outputArgValues);
        return instance;
    }

    /*
    *
    *   Local helper functions
    *
    * */
    private void onStateChanged() {
        switch (state) {
            case STATE_INIT:
                connect();
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
                state = State.STATE_IDLE;
                controller.onReady();
                break;
            case STATE_ERROR:
                controller.onError();
                break;
            case STATE_CLOSED:
                controller.onClose();
                break;
            default:
                break;
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
        MessageContent content= new ContentRegisterRequest(deviceMaster.getInfo());
        Message message = new Message(MessageType.REGISTER_REQUEST, content);
        sendMessage(message);
        state = State.STATE_REGISTERING;
    }

    private void onRegisterResponse(Message message) {
        ContentRegisterResponse content = (ContentRegisterResponse) message.content;
        if (content.result == ResultType.ERROR) {
            error = Error.ERROR_REGISTER;
            state = State.STATE_ERROR;
        } else if (content.result == ResultType.SUCCESS) {
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
        Long timestamp = System.currentTimeMillis();
        String sign = signature(deviceMaster.getDeviceId(), timestamp, deviceMaster.getToken());
        MessageContent content = new ContentAuthRequest(deviceMaster.getDeviceId(), timestamp, sign);
        Message message = new Message(MessageType.AUTH_REQUEST, content);
        sendMessage(message);
        state = State.STATE_AUTHING;
        onStateChanged();
    }

    private void onAuthResponse(Message message) {
        ContentAuthResponse content = (ContentAuthResponse) message.content;
        if (content.result == ResultType.ERROR) {
            error = Error.ERROR_AUTH;
            state = State.STATE_ERROR;
        } else if (content.result == ResultType.SUCCESS) {
            state = State.STATE_AUTHED;
        }
        onStateChanged();
    }

    private void update() {
        MessageContent content = new ContentUpdateRequest(deviceMaster);
        Message message = new Message(MessageType.UPDATE_REQUEST, content);
        sendMessage(message);
        state = State.STATE_UPDATING;
    }

    private void onUpdateResponse(Message message) {
        ContentUpdateResponse content = (ContentUpdateResponse) message.content;
        if (content.result == ResultType.ERROR) {
            error = Error.ERROR_UPDATE;
            state = State.STATE_ERROR;
        } else if (content.result == ResultType.SUCCESS) {
            DeviceMaster deviceMasterUpdated = content.deviceMaster;
//            TODO: check and update the consistence between local and remote device master info.
            state = State.STATE_UPDATED;
        }
        onStateChanged();
    }

    private void onInvokeRequest(Message message)  {
        ContentInvokeRequest content = (ContentInvokeRequest) message.content;
        FunctionInstance instance = content.instance;
        try {
            if (instance.getFunction().getType() == FunctionType.SYNC) {
                controller.invokeMethodSync(instance, (long) 5000);
                ContentInvokeResponse contentResponse = new ContentInvokeResponse(instance.getResult(), instance);
                Message messageResponse = new Message(MessageType.INVOKE_RESPONSE, contentResponse);
                sendMessage(messageResponse);
            } else {
                controller.invokeMethodAsync(instance, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onInvokeResponse(Message message) {
        ContentInvokeResponse content = (ContentInvokeResponse) message.content;
        FunctionInstance instanceUpdated = content.instance;
        try {
            if (instanceMap.containsKey(instanceUpdated.getInstanceId())) {
                synchronized (this) {
                    FunctionInstance instanceLocal = instanceMap.remove(instanceUpdated.getInstanceId());
                    instanceLocal.setResult(instanceUpdated.getResult());
                    instanceLocal.setOutputValues(instanceUpdated.getOutputValues());
                    if (instanceUpdated.getFunction().getType() == FunctionType.SYNC) {
                        instanceLocal.notify();
                    } else if (instanceUpdated.getFunction().getType() == FunctionType.ASYNC) {
                        instanceLocal.getInvokeHandler().onInvokeDone(instanceLocal);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
