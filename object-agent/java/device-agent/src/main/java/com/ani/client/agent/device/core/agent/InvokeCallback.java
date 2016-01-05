package com.ani.client.agent.device.core.agent;

import com.ani.bus.device.commons.dto.device.FunctionInstance;

/**
 * Created by huangbin on 10/26/15.
 */
public interface InvokeCallback {
    /**
     * Callback in Invokable.invokeAsync(FunctionInstance instance).
     * @param instance
     * @throws Exception
     */
    void onInvokeDone(FunctionInstance instance) throws Exception;
}
