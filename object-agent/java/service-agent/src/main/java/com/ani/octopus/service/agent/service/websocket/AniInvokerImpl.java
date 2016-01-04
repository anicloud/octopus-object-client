package com.ani.octopus.service.agent.service.websocket;


import com.ani.bus.service.commons.dto.accountobject.AccountObject;
import com.ani.bus.service.commons.dto.accountobject.AccountObjectCallType;
import com.ani.bus.service.commons.dto.anistub.AniStub;
import com.ani.bus.service.commons.dto.anistub.AniStubConnType;
import com.ani.bus.service.commons.dto.anistub.Argument;
import com.ani.bus.service.commons.message.SocketMessage;
import com.ani.bus.service.commons.message.callmessage.AniAccountCallMessage;
import com.ani.bus.service.commons.message.callmessage.AniObjectCallMessage;
import com.ani.bus.service.commons.session.AniServiceSession;
import com.ani.octopus.service.agent.core.validate.DomainObjectValidator;

import javax.validation.ValidationException;
import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.List;

/**
 * The implementation of AniInvoker for the third party service to call. We provides <b>Async</b> and <b>Sync</b> methods.
 * If you want to use <b>Async</b> methods, you need to register your own <b>Observer</b> which implements the MessageObserver interface
 * to accept the result from Anicloud Platform.
 * <br><br>
 * <strong>Use Example:</strong><br>
 * <pre>
 *      // you need to implement the ClientInvokable interface and register on
 *      // WebSocketClient for anicloud platform to callback
 *      ClientInvokable invokable = new ClientInvokerImpl();
 *      WebSocketClient socketClient = new WebSocketClient(invokable);
 *      // you need to implement your own observer and register on socketClient
 *      // to receive the message for the asynchronous call result from anicloud platform
 *      Vector&lt;MessageObserver&gt; messageObservers = new Vector&lt;MessageObserver&gt;();
 *      messageObservers.add(new AniObjectCallMessageObserver());
 *      socketClient.setObs(messageObservers);
 *
 *      // inject your WebSocketClient instance and anicloud socket destination url to factory
 *      // and use factory to get the session, than you can use the session to communicate
 *      // with anicloud platform
 *      AnicelMeta anicelMeta = new AnicelMeta();
 *      WebSocketSessionFactory sessionFactory = new WebSocketSessionFactory(
 *          socketClient,
 *          anicelMeta,
 *          aniServiceId,
 *          clientSecret
 *      );
 *      // get AniServiceSession instance
 *      AniServiceSession serviceSession = sessionFactory.getAniServiceSession();
 *      // AniInvokable instance
 *      AniInvokable aniInvokable = new AniInvokerImpl(serviceSession);
 *      // call the methods
 *      ......
 * </pre>
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
        if (!DomainObjectValidator.isDomainObjectValid(stub)) {
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
        if (!DomainObjectValidator.isDomainObjectValid(stub)) {
            throw new ValidationException("Invalid AniStub Instance.");
        }

        if (this.session == null) {
            throw new NullPointerException("webSocket session is null.");
        }
        AniObjectCallMessage message = new AniObjectCallMessage(stub, AniStubConnType.ASYNC);
        session.getSession().getBasicRemote().sendObject(message);
    }

    @Override
    public SocketMessage registerAndLogin(AccountObject accountObject) throws IOException, EncodeException {
        if (!DomainObjectValidator.isDomainObjectValid(accountObject)) {
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
        return accountObject.getResultMsg();
    }

    @Override
    public SocketMessage login(AccountObject accountObject) throws IOException, EncodeException {
        if (!DomainObjectValidator.isDomainObjectValid(accountObject)) {
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
        return accountObject.getResultMsg();
    }

    @Override
    public SocketMessage logout(AccountObject accountObject) throws IOException, EncodeException {
        if (!DomainObjectValidator.isDomainObjectValid(accountObject)) {
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
        return accountObject.getResultMsg();
    }

    @Override
    public SocketMessage remove(AccountObject accountObject) throws IOException, EncodeException {
        if (!DomainObjectValidator.isDomainObjectValid(accountObject)) {
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
        return accountObject.getResultMsg();
    }

    @Override
    public SocketMessage updateAccountObjectStubList(AccountObject accountObject) throws IOException, EncodeException {
        if (!DomainObjectValidator.isDomainObjectValid(accountObject)) {
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
        return accountObject.getResultMsg();
    }
}
