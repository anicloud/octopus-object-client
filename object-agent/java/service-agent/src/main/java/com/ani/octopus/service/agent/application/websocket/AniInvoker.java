package com.ani.octopus.service.agent.application.websocket;

import com.ani.octopus.service.agent.domain.websocket.AniFunction;

import javax.websocket.EncodeException;
import java.io.IOException;

/**
 * Created by zhaoyu on 15-10-30.
 */
public interface AniInvoker extends Invokable {
    void invokeAniSystemSync(AniFunction function) throws IOException, EncodeException;
    void invokeAniSystemAsync(AniFunction function);
}
