package com.ani.octopus.service.agent.application.websocket;

import com.ani.octopus.service.agent.domain.websocket.AniStub;

import javax.websocket.EncodeException;
import java.io.IOException;

/**
 * Created by zhaoyu on 15-10-29.
 */
public interface Invokable {
    void invokeAniObjectSync(AniStub stub) throws IOException, EncodeException;
    void invokeAniObjectAsync(AniStub stub);
}
