package com.ani.octopus.service.agent.service.websocket;


import com.ani.octopus.service.agent.core.validate.DomainValidationUtils;
import com.ani.octopus.service.agent.core.validate.ValidateMessage;
import com.ani.octopus.service.agent.core.websocket.AniServiceSession;
import com.ani.octopus.service.agent.service.websocket.account.AccountObject;
import com.ani.octopus.service.agent.service.websocket.account.AccountObjectCallType;
import com.ani.octopus.service.agent.service.websocket.account.AniObjectState;
import com.ani.octopus.service.agent.service.websocket.dto.AniStub;
import com.ani.octopus.service.agent.service.websocket.dto.AniStubConnType;
import com.ani.octopus.service.agent.service.websocket.dto.Argument;
import com.ani.octopus.service.agent.service.websocket.dto.message.AniAccountCallMessage;
import com.ani.octopus.service.agent.service.websocket.dto.message.AniObjectCallMessage;
import com.ani.octopus.service.agent.service.websocket.dto.message.Message;

import javax.validation.ValidationException;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * The implementation of AniInvoker for the third party service to call.
 *
 * Created by zhaoyu on 15-10-30.
 */
public class AniInvokerImpl implements AniInvokable {

    private AniServiceSession session;

    public AniInvokerImpl() {
    }

    public AniInvokerImpl(AniServiceSession session) {
        this.session = session;
    }

    @Override
    public synchronized List<Argument> invokeAniObjectSync(AniStub stub) throws IOException, EncodeException {
        if (DomainValidationUtils.isAniStubValid(stub)) {
            throw new ValidationException("Invalid AniStub Instance.");
        }
        List<Argument> resultValues;

        if (this.session == null) {
            throw new NullPointerException("webSocket session is null.");
        }
        AniObjectCallMessage message = new AniObjectCallMessage(stub, AniStubConnType.SYNC);
        session.put(stub);
        session.getSession().getBasicRemote().sendObject(message);

        synchronized (stub) {
            try {
                stub.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        stub = session.getAniStub(stub.getKeyId());
        if (stub == null) {
            throw new NullPointerException("Stub is null.");
        }
        resultValues = stub.getOutputValues();
        session.delete(stub);
        return resultValues;
    }

    @Override
    public void invokeAniObjectAsyn(AniStub stub) throws IOException, EncodeException {
        if (DomainValidationUtils.isAniStubValid(stub)) {
            throw new ValidationException("Invalid AniStub Instance.");
        }

        if (this.session == null) {
            throw new NullPointerException("webSocket session is null.");
        }
        AniObjectCallMessage message = new AniObjectCallMessage(stub, AniStubConnType.ASYNC);
        session.getSession().getBasicRemote().sendObject(message);
    }

    @Override
    public Message registerAndLogin(AccountObject accountObject) throws IOException, EncodeException {
        if (DomainValidationUtils.isAccountObjectValid(accountObject)) {
            throw new ValidationException("Invalid Account Object Instance.");
        }

        if (this.session == null) {
            throw new NullPointerException("webSocket session is null.");
        }
        AniAccountCallMessage message = new AniAccountCallMessage(
                accountObject,
                AccountObjectCallType.REGISTER_AND_LOGIN
        );
        session.put(accountObject);
        session.getSession().getBasicRemote().sendObject(message);
        synchronized (accountObject) {
            try {
                accountObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        accountObject = session.getAccountObject(accountObject.getKeyId());
        if (accountObject == null) {
            throw new NullPointerException("AccountObject is null.");
        }
        session.delete(accountObject);
        return accountObject.getResult();
    }

    @Override
    public Message login(AccountObject accountObject) throws IOException, EncodeException {
        if (DomainValidationUtils.isAccountObjectValid(accountObject)) {
            throw new ValidationException("Invalid Account Object Instance.");
        }

        if (this.session == null) {
            throw new NullPointerException("webSocket session is null.");
        }
        AniAccountCallMessage message = new AniAccountCallMessage(
                accountObject,
                AccountObjectCallType.LOGIN
        );
        session.put(accountObject);
        session.getSession().getBasicRemote().sendObject(message);
        synchronized (accountObject) {
            try {
                accountObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        accountObject = session.getAccountObject(accountObject.getKeyId());
        if (accountObject == null) {
            throw new NullPointerException("AccountObject is null.");
        }
        session.delete(accountObject);
        return accountObject.getResult();
    }

    @Override
    public Message logout(AccountObject accountObject) throws IOException, EncodeException {
        if (DomainValidationUtils.isAccountObjectValid(accountObject)) {
            throw new ValidationException("Invalid Account Object Instance.");
        }

        if (this.session == null) {
            throw new NullPointerException("webSocket session is null.");
        }
        AniAccountCallMessage message = new AniAccountCallMessage(
                accountObject,
                AccountObjectCallType.LOGOUT
        );

        session.put(accountObject);
        session.getSession().getBasicRemote().sendObject(message);
        synchronized (accountObject) {
            try {
                accountObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        accountObject = session.getAccountObject(accountObject.getKeyId());
        if (accountObject == null) {
            throw new NullPointerException("AccountObject is null.");
        }
        session.delete(accountObject);
        return accountObject.getResult();
    }

    @Override
    public Message remove(AccountObject accountObject) throws IOException, EncodeException {
        if (DomainValidationUtils.isAccountObjectValid(accountObject)) {
            throw new ValidationException("Invalid Account Object Instance.");
        }

        if (this.session == null) {
            throw new NullPointerException("webSocket session is null.");
        }
        AniAccountCallMessage message = new AniAccountCallMessage(
                accountObject,
                AccountObjectCallType.REMOVE
        );
        session.put(accountObject);
        session.getSession().getBasicRemote().sendObject(message);
        synchronized (accountObject) {
            try {
                accountObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        accountObject = session.getAccountObject(accountObject.getKeyId());
        if (accountObject == null) {
            throw new NullPointerException("AccountObject is null.");
        }
        session.delete(accountObject);
        return accountObject.getResult();
    }

    @Override
    public Message updateAccountObjectStubList(AccountObject accountObject) throws IOException, EncodeException {
        if (DomainValidationUtils.isAccountObjectValid(accountObject)) {
            throw new ValidationException("Invalid Account Object Instance.");
        }

        if (this.session == null) {
            throw new NullPointerException("webSocket session is null.");
        }
        AniAccountCallMessage message = new AniAccountCallMessage(
                accountObject,
                AccountObjectCallType.UPDATE_STUB_LIST
        );
        session.put(accountObject);
        session.getSession().getBasicRemote().sendObject(message);
        synchronized (accountObject) {
            try {
                accountObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        accountObject = session.getAccountObject(accountObject.getKeyId());
        if (accountObject == null) {
            throw new NullPointerException("AccountObject is null.");
        }
        session.delete(accountObject);
        return accountObject.getResult();
    }
}
