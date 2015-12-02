package com.ani.octopus.service.agent.service.websocket;

import com.ani.octopus.service.agent.service.websocket.Invokable;
import com.ani.octopus.service.agent.service.websocket.dto.AniStub;

import javax.websocket.EncodeException;
import java.io.IOException;

/**
 * Created by zhaoyu on 15-11-26.
 */
public interface AniInvokable extends Invokable, AccountInvoker {
    /**
     * Asynchronous call, but you should register your observer to get the return messsage
     * @param stub
     */
    void invokeAniObjectAsyn(AniStub stub) throws IOException, EncodeException;
}
