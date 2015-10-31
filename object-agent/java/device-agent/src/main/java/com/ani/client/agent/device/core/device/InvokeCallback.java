package com.ani.client.agent.device.core.device;


/**
 * Created by huangbin on 10/26/15.
 */
public interface InvokeCallback {
    /**
     * Callback in Invokable.invokeAsync(FunctionInstance instance).
     * @param instance
     * @throws Exception
     */
    public void onInvokeDone(FunctionInstance instance) throws Exception;
}
