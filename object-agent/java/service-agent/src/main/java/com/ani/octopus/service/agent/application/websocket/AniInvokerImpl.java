package com.ani.octopus.service.agent.application.websocket;


import com.ani.octopus.service.agent.domain.websocket.AniFunction;
import com.ani.octopus.service.agent.domain.websocket.AniStub;
import com.ani.octopus.service.agent.domain.websocket.message.AniFunctionCallMessage;
import com.ani.octopus.service.agent.domain.websocket.message.AniObjectCallMessage;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by zhaoyu on 15-10-30.
 */
public class AniInvokerImpl implements AniInvoker {

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

    @Override
    public synchronized void invokeAniSystemSync(AniFunction function) throws IOException, EncodeException {
        AniFunctionCallMessage message = new AniFunctionCallMessage(function);
        session.getBasicRemote().sendObject(message);
    }

    @Override
    public synchronized void invokeAniSystemAsync(AniFunction function) {
        AniFunctionCallMessage message = new AniFunctionCallMessage(function);
        session.getAsyncRemote().sendObject(message);
    }
}
