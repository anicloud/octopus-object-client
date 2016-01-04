package com.ani.octopus.service.agent.service.websocket;


import com.ani.bus.service.commons.dto.anistub.AniStub;
import com.ani.bus.service.commons.dto.anistub.Argument;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.List;

/**
 * The basic interface for the third service to call Anicloud Platform.
 *  Also, the third party service need to implement the interface and inject into
 *  the websocketclient for Anicloud Platform to callback.
 *  <br><br>
 * Created by zhaoyu on 15-10-29.
 */
public interface ClientInvokable {
    /**
     * the sync method for aniObject call
     * @param stub call stub instance
     * @return result
     * @throws IOException IOException
     * @throws EncodeException EncodeException
     */
    List<Argument> invokeAniObjectSync(AniStub stub) throws IOException, EncodeException;

    /**
     * when session was closed, the method was called. You can implements this method for your own
     * business.
     * @param sessionId session id
     * @param closeReason close reason
     */
    void sessionOnClose(String sessionId, CloseReason closeReason);


    /**
     * session on error callback method
     * @param sessionId session id
     * @param throwable close reason
     */
    void sessionOnError(String sessionId, Throwable throwable);
}
