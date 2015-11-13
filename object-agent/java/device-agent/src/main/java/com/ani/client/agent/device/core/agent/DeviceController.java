package com.ani.client.agent.device.core.agent;


import com.ani.client.agent.device.core.device.DeviceMaster;
import com.ani.client.agent.device.core.device.Invokable;
import com.ani.client.agent.device.core.device.InvokeCallback;

/**
 * Created by huangbin on 10/25/15.
 */
public abstract class DeviceController implements Invokable, InvokeCallback {
    public enum AgentError {
        AGENT_ERROR_CONNECT,
        AGENT_ERROR_UPDATE
    }
    /**
     *  Handle the agent error event.
     * @param agentError
     */
    public abstract void onError(AgentError agentError);

    /**
     *  Handle the agent ready event and begin business logic procedure.
     */
    public abstract void onReady();

    /**
     *  Handle the agent close event, triggered either by network errors or by the DeviceAgent.Close().
     */
    public abstract void onClose();

    /**
     *  Handle the agent update event, triggered before the DeviceAgent::onReady() and after the DeviceAgent.update(),
     * @param master
     */
    public abstract void onUpdate(DeviceMaster master);
}
