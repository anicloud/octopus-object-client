package com.ani.client.agent.device.core.device;


/**
 * Created by huangbin on 10/26/15.
 */
public interface Invokable {
    /**
     * Invoke remote Functions in a synchronous way.
     * @param instance
     * @param timeout
     * @throws Exception
     */
    public void invokeSync(FunctionInstance instance, Long timeout) throws Exception;

    /**
     * Invoke remote Functions in an asynchronous way.
     * @param instance
     * @throws Exception
     */
    public void invokeAsync(FunctionInstance instance) throws Exception;
}
