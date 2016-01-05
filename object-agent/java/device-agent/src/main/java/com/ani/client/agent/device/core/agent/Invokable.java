package com.ani.client.agent.device.core.agent;


import com.ani.bus.device.commons.dto.device.FunctionInstance;

/**
 * Created by huangbin on 10/26/15.
 */
public interface Invokable {
    /**
     * Invoke the remote function, in a synchronous fashion.
     * @param instance
     * @param timeout
     * @throws Exception
     */
    void invokeSync(FunctionInstance instance, Long timeout) throws Exception;

    /**
     * Invoke the remote function, in an asynchronous fashion.
     * @param instance
     * @throws Exception
     */
    void invokeAsync(FunctionInstance instance, InvokeCallback callback) throws Exception;
}
