package com.ani.octopus.service.agent.application.websocket;

import com.ani.octopus.service.agent.domain.websocket.AniStub;

import javax.websocket.EncodeException;
import java.io.IOException;

/**
 * Created by zhaoyu on 15-10-30.
 */
public class ClientInvokerImpl implements Invokable {

    @Override
    public void invokeAniObjectSync(AniStub stub) throws IOException, EncodeException {

    }

    @Override
    public void invokeAniObjectAsync(AniStub stub) {

    }
}
