package com.ani.octopus.service.agent.service.websocket;

import com.ani.octopus.service.agent.service.websocket.dto.AniStub;

import javax.websocket.EncodeException;
import java.io.IOException;

/**
 * The basic interface for the third service to call anicloud platform.
 *  Also, the third party service need to implement the interface and inject into
 *  the websocketclient for anicloud platform to callback.
 * Created by zhaoyu on 15-10-29.
 */
public interface Invokable {
    /**
     * the sync method for aniObject call
     * @param stub
     * @throws IOException
     * @throws EncodeException
     */
    void invokeAniObjectSync(AniStub stub) throws IOException, EncodeException;

    /**
     * the async method for aniObject call.
     * @param stub
     */
    void invokeAniObjectAsync(AniStub stub);
}
