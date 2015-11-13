package com.ani.octopus.service.agent.service.websocket;


import com.ani.octopus.service.agent.service.websocket.dto.AniStub;
import com.ani.octopus.service.agent.service.websocket.dto.message.AniFunctionCallMessage;
import com.ani.octopus.service.agent.service.websocket.dto.message.AniObjectCallMessage;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

/**
 * The implementation of AniInvoker for the third party service to call.
 *
 * Created by zhaoyu on 15-10-30.
 */
public class AniInvokerImpl implements Invokable {

    private Session session;

    public AniInvokerImpl(Session session) {
        this.session = session;
    }

    @Override
    public synchronized void invokeAniObjectSync(AniStub stub) throws IOException, EncodeException {
        AniObjectCallMessage message = new AniObjectCallMessage(stub);
        session.getBasicRemote().sendObject(message);
    }

    @Override
    public synchronized void invokeAniObjectAsync(AniStub stub) {
        AniObjectCallMessage message = new AniObjectCallMessage(stub);
        session.getAsyncRemote().sendObject(message);
    }
}
