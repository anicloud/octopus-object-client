package com.ani.client.agent.device.core.agent;


import com.ani.client.agent.device.core.device.DeviceMaster;
import com.ani.client.agent.device.core.device.Invokable;
import com.ani.client.agent.device.core.device.InvokeCallback;

/**
 * Created by huangbin on 10/25/15.
 */
public abstract class DeviceController implements Invokable, InvokeCallback {
    /**
     *  Called during the DeviceAgent.connect() method
     *  It shall provide a DeviceMaster that describes device hardware information.
     * @return
     */
    public abstract DeviceMaster getDeviceMaster();

    /**
     *  Handle the agent error event.
     */
    public abstract void onError();

    /**
     *  Handle the agent ready event, which means the device authentication and update are completed.
     */
    public abstract void onReady();

    /**
     *  Handle the agent close event, triggered either by network errors or by the Agent.Close() call.
     */
    public abstract void onClose();

    /**
     *  Handle the agent update event, triggered first time before OnReady() and then after the Agent.update() call,
     * @param master
     */
    public abstract void onUpdate(DeviceMaster master);
}
