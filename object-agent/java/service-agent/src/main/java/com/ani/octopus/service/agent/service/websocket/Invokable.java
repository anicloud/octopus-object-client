package com.ani.octopus.service.agent.service.websocket;

import com.ani.octopus.service.agent.service.websocket.dto.AniStub;
import com.ani.octopus.service.agent.service.websocket.dto.AniStubConnType;
import com.ani.octopus.service.agent.service.websocket.dto.Argument;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.List;

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
    List<Argument> invokeAniObjectSync(AniStub stub) throws IOException, EncodeException;
}
