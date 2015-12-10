package com.ani.octopus.service.agent.service.websocket;

import com.ani.service.bus.core.application.agent.dto.anistub.AniStub;
import com.ani.service.bus.core.application.agent.dto.anistub.Argument;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.List;

/**
 * Created by zhaoyu on 15-11-26.
 */
public interface AniInvokable extends AccountInvoker {
    /**
     * Asynchronous call, but you should register your observer to get the return messsage
     * @param stub
     */
    void invokeAniObjectAsyn(AniStub stub) throws IOException, EncodeException;

    /**
     * the sync method for aniObject call
     * @param stub
     * @throws IOException
     * @throws EncodeException
     */
    List<Argument> invokeAniObjectSync(AniStub stub) throws IOException, EncodeException;
}
