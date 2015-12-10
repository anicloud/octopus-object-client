package com.ani.octopus.service.agent.service.websocket;

import com.ani.octopus.service.agent.core.config.AnicelMeta;
import com.ani.octopus.service.agent.core.websocket.WebSocketClient;
import com.ani.octopus.service.agent.core.websocket.WebSocketSessionFactory;
import com.ani.service.bus.core.application.agent.dto.anistub.AniStub;
import com.ani.service.bus.core.application.agent.dto.anistub.Argument;
import com.ani.service.bus.core.application.agent.observer.AniObjectCallMessageObserver;
import com.ani.service.bus.core.application.agent.observer.MessageObserver;
import com.ani.service.bus.core.application.session.AniServiceSession;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by zhaoyu on 15-10-30.
 */
public class WebSocketSessionFactoryTest {

    @Ignore
    public void testSessionFactory() {
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
        WebSocketSessionFactory sessionFactory =
                new WebSocketSessionFactory(socketClient, anicelMeta, "926168327152741609", "f818a4974030cad047b64b01629a02dc");
        AniServiceSession session = sessionFactory.getAniServiceSession();

        // use AniInvokerImpl service to call platform
        AniInvokable aniInvoker = new AniInvokerImpl(session);
        List<Argument> argumentList = new ArrayList<>();
        AniStub aniStub = new AniStub(
                102L,
                1707593791689932096L,
                1L,
                1,
                argumentList
        );

        try {
            List<Argument> result = aniInvoker.invokeAniObjectSync(aniStub);
            System.out.println("here");
            Thread.sleep(4000);
            System.out.println("wsws");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}