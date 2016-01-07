package com.ani.octopus.service.agent.service.websocket;

import com.ani.bus.service.commons.dto.accountobject.AccountObject;
import com.ani.bus.service.commons.dto.anistub.AniDataType;
import com.ani.bus.service.commons.dto.anistub.AniStub;
import com.ani.bus.service.commons.dto.anistub.Argument;
import com.ani.bus.service.commons.dto.anistub.ArgumentType;
import com.ani.bus.service.commons.message.SocketMessage;
import com.ani.bus.service.commons.observer.MessageObserver;
import com.ani.bus.service.commons.session.AniServiceSession;
import com.ani.octopus.service.agent.core.config.AnicelMeta;
import com.ani.octopus.service.agent.core.websocket.WebSocketClient;
import com.ani.octopus.service.agent.core.websocket.WebSocketSessionFactory;
import com.ani.octopus.service.agent.service.websocket.observer.AniObjectCallMessageObserver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


/**
 * Created by zhaoyu on 15-11-28.
 */
public class AniInvokerImplTest {

    private AniServiceSession serviceSession;
    private Long accountId = 3888396496254000114L;
    private ObjectMapper objectMapper;
    @Before
    public void before() {
        objectMapper = new ObjectMapper();

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

        ArgumentType type = new ArgumentType(AniDataType.INTEGER);
        ArgumentType argumentType = new ArgumentType(AniDataType.ARRAY, new ArgumentType(AniDataType.STRING));
        inputValues.add(new Argument("inArg1", type, 1));
        inputValues.add(new Argument("inArg2", argumentType, new String[]{"zhang", "zhao", "yu"}));
        AniStub aniStub = new AniStub(
                5970297041189928808L,
                accountId,
                1L,
                1,
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
        AccountObject accountObject = new AccountObject(3888396496254000114L);
        accountObject.addStub(1L, 1);
        accountObject.addStub(2L, 2);
        //accountObject.addStub(2L, 2);
        //accountObject.addStub(1L, 1);
        //accountObject.addStub(1L, 2);
        Long begin = System.currentTimeMillis();
        SocketMessage message = accountInvoker.registerAndLogin(accountObject);
        Long costTime = System.currentTimeMillis() - begin;
        System.out.println("register And Login cost time : " + costTime);
        System.out.println(message);

        accountObject = new AccountObject(3888396496254000114L);
        SocketMessage message2 = accountInvoker.logout(accountObject);
        System.out.println(message2);
    }

    @Test
    public void testLogin() throws Exception {
        // use AniInvokerImpl service to call platform
        AccountInvoker accountInvoker = new AniInvokerImpl(serviceSession);
        AccountObject accountObject = new AccountObject(accountId);
        Long beginTime = System.currentTimeMillis();
        SocketMessage message = accountInvoker.login(accountObject);
        Long costTime = System.currentTimeMillis() - beginTime;
        System.out.println("Login call time : " + costTime);
        System.out.println(message);

        //Thread.sleep(1000000);
        beginTime = System.currentTimeMillis();
        accountObject = new AccountObject(accountId);
        SocketMessage message2 = accountInvoker.logout(accountObject);
        costTime = System.currentTimeMillis() - beginTime;
        System.out.println("logout cost time: " + costTime);
        System.out.println(message2);
    }

    @Ignore
    public void testLogout() throws Exception {

    }

    @Ignore
    public void testRemove() throws Exception {
        // use AniInvokerImpl service to call platform
        AccountInvoker accountInvoker = new AniInvokerImpl(serviceSession);
        AccountObject accountObject = new AccountObject(accountId);
        SocketMessage message = accountInvoker.remove(accountObject);
        System.out.println(message);
    }

    @Test
    public void testUpdateAccountObjectStubList() throws Exception {
        // use AniInvokerImpl service to call platform
        AniInvokable aniInvokable = new AniInvokerImpl(serviceSession);
        AccountObject accountObject = new AccountObject(accountId);

        SocketMessage message = aniInvokable.login(accountObject);
        System.out.println(message);

        AccountObject accountObject2 = new AccountObject(accountId);
        accountObject2.addStub(1L, 1);
        accountObject2.addStub(2L, 2);

        long beginTime = System.currentTimeMillis();
        message = aniInvokable.updateAccountObjectStubList(accountObject2);
        long costTime = System.currentTimeMillis() - beginTime;
        System.out.println("update Stub List cost:" + costTime);
        System.out.println(message);
    }

    @Test
    public void testJsonConvert() throws IOException {
        List<Argument> inputValues = new ArrayList<>();

        ArgumentType type = new ArgumentType(AniDataType.INTEGER);

        inputValues.add(new Argument("arg1", type, 1L));
        AniStub aniStub = new AniStub(
                5970297041189928808L,
                accountId,
                1L,
                1,
                inputValues
        );
        String result = objectMapper.writeValueAsString(aniStub);
        aniStub = objectMapper.readValue(result, AniStub.class);
        System.out.println(aniStub.toString());
    }
}