package com.ani.octopus.service.agent.service.websocket;

import com.ani.octopus.service.agent.core.AnicelMeta;
import com.ani.octopus.service.agent.core.websocket.AniServiceSession;
import com.ani.octopus.service.agent.core.websocket.WebSocketClient;
import com.ani.octopus.service.agent.core.websocket.WebSocketSessionFactory;
import com.ani.octopus.service.agent.service.websocket.account.AccountObject;
import com.ani.octopus.service.agent.service.websocket.dto.AniStub;
import com.ani.octopus.service.agent.service.websocket.dto.Argument;
import com.ani.octopus.service.agent.service.websocket.dto.message.Message;
import com.ani.octopus.service.agent.service.websocket.observer.AniObjectCallMessageObserver;
import com.ani.octopus.service.agent.service.websocket.observer.MessageObserver;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Vector;


/**
 * Created by zhaoyu on 15-11-28.
 */
public class AniInvokerImplTest {

    private AniServiceSession serviceSession;

    @Before
    public void before() {
        // you need to implement the Invokable interface and register on
        // WebSocketClient for anicloud platform to callback
        Invokable invokable = new ClientInvokerImpl();
        WebSocketClient socketClient = new WebSocketClient(invokable);

        // you need to implement your own observer and register on socketClient
        // to receive the message from anicloud platform
        Vector<MessageObserver> messageObservers = new Vector<>();
        messageObservers.add(new AniObjectCallMessageObserver());
        socketClient.setObs(messageObservers);

        // inject your WebSocketClient instance and anicloud socket destination url to factory
        // and use factory to get the session, than you can use the session to communicate
        // with anicloud platform
        AnicelMeta anicelMeta = new AnicelMeta();
        WebSocketSessionFactory sessionFactory =
                new WebSocketSessionFactory(socketClient, anicelMeta, "926168327152741609", "f818a4974030cad047b64b01629a02dc");
        serviceSession = sessionFactory.getAniServiceSession();
    }

    @Ignore
    public void testInvokeAniObjectSync() throws Exception {
        AniInvokable aniInvokable = new AniInvokerImpl(serviceSession);
        AccountObject accountObject = new AccountObject(1707593791689932096L);
        Message message = aniInvokable.login(accountObject);
        System.out.println(message);

        AniStub aniStub = new AniStub(
                2174747981244009086L,
                1707593791689932096L,
                1L,
                2,
                null
        );
        List<Argument> result = aniInvokable.invokeAniObjectSync(aniStub);
    }

    @Test
    public void testInvokeAniObjectAsyn() throws Exception {

    }

    @Ignore
    public void testRegisterAndLogin() throws Exception {
        // use AniInvokerImpl service to call platform
        AccountInvoker accountInvoker = new AniInvokerImpl(serviceSession);
        AccountObject accountObject = new AccountObject(1707593791689932096L);
        accountObject.addStub(2L, 1);
        accountObject.addStub(2L, 2);
        accountObject.addStub(1L, 1);
        accountObject.addStub(1L, 2);
        Message message = accountInvoker.registerAndLogin(accountObject);
        System.out.println(message);

        accountObject = new AccountObject(1707593791689932096L);
        Message message2 = accountInvoker.logout(accountObject);
        System.out.println(message2);
    }

    @Test
    public void testLogin() throws Exception {
        // use AniInvokerImpl service to call platform
        AccountInvoker accountInvoker = new AniInvokerImpl(serviceSession);
        AccountObject accountObject = new AccountObject(1707593791689932096L);
        Message message = accountInvoker.login(accountObject);
        System.out.println(message);

        Thread.sleep(1000000);

        accountObject = new AccountObject(1707593791689932096L);
        Message message2 = accountInvoker.logout(accountObject);
        System.out.println(message2);
    }

    @Ignore
    public void testLogout() throws Exception {

    }

    @Ignore
    public void testRemove() throws Exception {
        // use AniInvokerImpl service to call platform
        AccountInvoker accountInvoker = new AniInvokerImpl(serviceSession);
        AccountObject accountObject = new AccountObject(1707593791689932096L);
        accountObject.addStub(2L, 1);
        Message message = accountInvoker.remove(accountObject);
        System.out.println(message);
    }

    @Ignore
    public void testUpdateAccountObjectStubList() throws Exception {
        // use AniInvokerImpl service to call platform
        AniInvokable aniInvokable = new AniInvokerImpl(serviceSession);
        AccountObject accountObject = new AccountObject(1707593791689932096L);

        Message message = aniInvokable.login(accountObject);
        System.out.println(message);

        AccountObject accountObject2 = new AccountObject(1707593791689932096L);
        accountObject2.addStub(2L, 1);
        accountObject2.addStub(2L, 2);
        accountObject2.addStub(1L, 1);
        accountObject2.addStub(1L, 2);
        message = aniInvokable.updateAccountObjectStubList(accountObject2);
        System.out.println(message);
    }
}