package com.ani.client.agent.device.core.device;


/**
 * Created by huangbin on 10/26/15.
 */
public interface Invokable {
    public void invokeSync(FunctionInstance instance, Long timeout) throws Exception;
    public void invokeAsync(FunctionInstance instance) throws Exception;
}
