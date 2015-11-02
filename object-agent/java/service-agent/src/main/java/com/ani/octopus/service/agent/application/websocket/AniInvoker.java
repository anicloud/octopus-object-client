package com.ani.octopus.service.agent.application.websocket;

import com.ani.octopus.service.agent.domain.websocket.AniFunction;

import javax.websocket.EncodeException;
import java.io.IOException;

/**
 * The extended interface for the third party service to call anicloud platform.
 * Created by zhaoyu on 15-10-30.
 */
public interface AniInvoker extends Invokable {

    /**
     * the sync method for aniFunction call.
     * @param function
     * @throws IOException
     * @throws EncodeException
     */
    void invokeAniSystemSync(AniFunction function) throws IOException, EncodeException;

    /**
     * the async method for aniFunction call.
     * @param function
     */
    void invokeAniSystemAsync(AniFunction function);
}
