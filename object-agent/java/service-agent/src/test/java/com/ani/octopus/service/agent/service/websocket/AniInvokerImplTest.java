package com.ani.octopus.service.agent.service.websocket;

import com.ani.octopus.service.agent.core.config.AnicelMeta;
import com.ani.octopus.service.agent.core.websocket.WebSocketClient;
import com.ani.octopus.service.agent.core.websocket.WebSocketSessionFactory;
import com.ani.octopus.service.agent.service.websocket.observer.AniObjectCallMessageObserver;
import com.ani.service.bus.core.application.agent.dto.accountobject.AccountObject;
import com.ani.service.bus.core.application.agent.dto.anistub.AniStub;
import com.ani.service.bus.core.application.agent.dto.anistub.Argument;
import com.ani.service.bus.core.application.agent.dto.anistub.ArgumentType;
import com.ani.service.bus.core.application.agent.message.SocketMessage;
import com.ani.service.bus.core.application.agent.observer.MessageObserver;
import com.ani.service.bus.core.application.session.AniServiceSession;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


/**
 * Created by zhaoyu on 15-11-28.
 */
public class AniInvokerImplTest {

    private AniServiceSession serviceSession;

    @Before
    public void before() {
        String aniServiceId = "1058595963104900977";
        String clientSecret = "34d54214721d6077ae021ab5d8215258";

        // you need to implement the Invokable interface and register on
        // WebSocketClient for anicloud platform to callback
        ClientInvokable invokable = new ClientInvokerImpl();
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
        WebSocketSessionFactory sessionFactory = new WebSocketSessionFactory(
                socketClient,
                anicelMeta,
                aniServiceId,
                clientSecret
        );
        serviceSession = sessionFactory.getAniServiceSession();
    }

    @Test
    public void testInvokeAniObjectSync() throws Exception {
        AniInvokable aniInvokable = new AniInvokerImpl(serviceSession);

        List<Argument> inputValues = new ArrayList<>();

        List<Object> val = new ArrayList<>();
        val.add(1);

        inputValues.add(new Argument("arg1", ArgumentType.INTEGER, val));
        AniStub aniStub = new AniStub(
                6233851390026698963L,
                1707593791689932096L,
                1L,
                2,
                inputValues
        );
        List<Argument> result = aniInvokable.invokeAniObjectSync(aniStub);
        System.out.println(result);
        System.out.println("finished");
    }

    @Test
    public void testInvokeAniObjectAsyn() throws Exception {

    }

    @Test
    public void testRegisterAndLogin() throws Exception {
        // use AniInvokerImpl service to call platform
        AccountInvoker accountInvoker = new AniInvokerImpl(serviceSession);
        AccountObject accountObject = new AccountObject(1707593791689932096L);
        accountObject.addStub(2L, 1);
        //accountObject.addStub(2L, 2);
        //accountObject.addStub(1L, 1);
        //accountObject.addStub(1L, 2);
        SocketMessage message = accountInvoker.registerAndLogin(accountObject);
        System.out.println(message);

        accountObject = new AccountObject(1707593791689932096L);
        SocketMessage message2 = accountInvoker.logout(accountObject);
        System.out.println(message2);
    }

    @Test
    public void testLogin() throws Exception {
        // use AniInvokerImpl service to call platform
        AccountInvoker accountInvoker = new AniInvokerImpl(serviceSession);
        AccountObject accountObject = new AccountObject(1707593791689932096L);
        SocketMessage message = accountInvoker.login(accountObject);
        System.out.println(message);

        Thread.sleep(1000000);

        accountObject = new AccountObject(1707593791689932096L);
        SocketMessage message2 = accountInvoker.logout(accountObject);
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
        SocketMessage message = accountInvoker.remove(accountObject);
        System.out.println(message);
    }

    @Test
    public void testUpdateAccountObjectStubList() throws Exception {
        // use AniInvokerImpl service to call platform
        AniInvokable aniInvokable = new AniInvokerImpl(serviceSession);
        AccountObject accountObject = new AccountObject(1707593791689932096L);

        SocketMessage message = aniInvokable.login(accountObject);
        System.out.println(message);

        AccountObject accountObject2 = new AccountObject(1707593791689932096L);
        accountObject2.addStub(2L, 1);
        /*accountObject2.addStub(2L, 2);
        accountObject2.addStub(1L, 1);
        accountObject2.addStub(1L, 2);*/
        message = aniInvokable.updateAccountObjectStubList(accountObject2);
        System.out.println(message);
    }
}