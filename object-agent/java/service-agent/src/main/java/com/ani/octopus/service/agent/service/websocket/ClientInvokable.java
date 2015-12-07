package com.ani.octopus.service.agent.service.websocket;

import com.ani.octopus.service.agent.service.websocket.dto.AniStub;
import com.ani.octopus.service.agent.service.websocket.dto.Argument;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.List;

/**
 * The basic interface for the third service to call anicloud platform.
 *  Also, the third party service need to implement the interface and inject into
 *  the websocketclient for anicloud platform to callback.
 * Created by zhaoyu on 15-10-29.
 */
public interface ClientInvokable {
    /**
     * the sync method for aniObject call
     * @param stub
     * @throws IOException
     * @throws EncodeException
     */
    List<Argument> invokeAniObjectSync(AniStub stub) throws IOException, EncodeException;

    /**
     * when session was closed, the method was called. You can implements this method for your ouwn
     * business.
     * @param sessionId
     * @param closeReason
     */
    void sessionOnClose(String sessionId, CloseReason closeReason);


    /**
     * session on error callback method
     * @param sessionId
     * @param throwable
     */
    void sessionOnError(String sessionId, Throwable throwable);
}
