package com.ani.client.agent.device.core.agent;


/**
 * Created by huangbin on 10/25/15.
 */
public abstract class DeviceController implements Invokable, InvokeCallback {
    /**
     * Agent connect event.
     */
    public abstract void onConnect();

    /**
     * Agent register event.
     */
    public abstract void onRegister();

    /**
     * Agent auth event.
     */
    public abstract void onAuth();

    /**
     * Agent close event, emit by either the client or the server.
     */
    public abstract void onClose();

    /**
     * Agent update event.
     */
    public abstract void onUpdate();

    /**
     * Agent error event
     * @param error
     */
    public abstract void onError(DeviceAgent.Error error);

}
