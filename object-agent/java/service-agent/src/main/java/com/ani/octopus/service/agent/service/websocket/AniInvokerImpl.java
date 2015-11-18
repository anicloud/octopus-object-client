package com.ani.octopus.service.agent.service.websocket;


import com.ani.octopus.service.agent.service.websocket.account.AccountObject;
import com.ani.octopus.service.agent.service.websocket.account.AccountObjectCallType;
import com.ani.octopus.service.agent.service.websocket.account.AniObjectState;
import com.ani.octopus.service.agent.service.websocket.dto.AniStub;
import com.ani.octopus.service.agent.service.websocket.dto.message.AniAccountCallMessage;
import com.ani.octopus.service.agent.service.websocket.dto.message.AniObjectCallMessage;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

/**
 * The implementation of AniInvoker for the third party service to call.
 *
 * Created by zhaoyu on 15-10-30.
 */
public class AniInvokerImpl implements Invokable, AccountInvoker {

    private Session session;

    public AniInvokerImpl(Session session) {
        this.session = session;
    }

    @Override
    public synchronized void invokeAniObjectSync(AniStub stub) throws IOException, EncodeException {
        if (this.session == null) {
            throw new NullPointerException("webSocket session is null.");
        }
        AniObjectCallMessage message = new AniObjectCallMessage(stub);
        session.getBasicRemote().sendObject(message);
    }

    @Override
    public synchronized void invokeAniObjectAsync(AniStub stub) {
        if (this.session == null) {
            throw new NullPointerException("webSocket session is null.");
        }
        AniObjectCallMessage message = new AniObjectCallMessage(stub);
        session.getAsyncRemote().sendObject(message);
    }

    @Override
    public void registerAndLogin(AccountObject accountObject) throws IOException, EncodeException {
        if (this.session == null) {
            throw new NullPointerException("webSocket session is null.");
        }
        AniAccountCallMessage message = new AniAccountCallMessage(
                accountObject,
                AccountObjectCallType.REGISTER_AND_LOGIN
        );
        session.getBasicRemote().sendObject(message);
    }

    @Override
    public void login(AccountObject accountObject) throws IOException, EncodeException {
        if (this.session == null) {
            throw new NullPointerException("webSocket session is null.");
        }
        AniAccountCallMessage message = new AniAccountCallMessage(
                accountObject,
                AccountObjectCallType.LOGIN
        );
        session.getBasicRemote().sendObject(message);
    }

    @Override
    public void logout(AccountObject accountObject) throws IOException, EncodeException {
        if (this.session == null) {
            throw new NullPointerException("webSocket session is null.");
        }
        AniAccountCallMessage message = new AniAccountCallMessage(
                accountObject,
                AccountObjectCallType.LOGOUT
        );
        session.getBasicRemote().sendObject(message);
    }

    @Override
    public void remove(AccountObject accountObject) throws IOException, EncodeException {
        if (this.session == null) {
            throw new NullPointerException("webSocket session is null.");
        }
        AniAccountCallMessage message = new AniAccountCallMessage(
                accountObject,
                AccountObjectCallType.REMOVE
        );
        session.getBasicRemote().sendObject(message);
    }

    @Override
    public void UpdateAccountObjectStubList(AccountObject accountObject) throws IOException, EncodeException {
        if (this.session == null) {
            throw new NullPointerException("webSocket session is null.");
        }
        AniAccountCallMessage message = new AniAccountCallMessage(
                accountObject,
                AccountObjectCallType.UPDATE_STUB_LIST
        );
        session.getBasicRemote().sendObject(message);
    }
}
