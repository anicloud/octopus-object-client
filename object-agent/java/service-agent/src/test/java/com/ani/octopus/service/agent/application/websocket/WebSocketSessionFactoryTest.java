package com.ani.octopus.service.agent.application.websocket;

import com.ani.octopus.service.agent.application.websocket.observer.AniFunctionCallMessageObserver;
import com.ani.octopus.service.agent.application.websocket.observer.AniObjectCallMessageObserver;
import com.ani.octopus.service.agent.application.websocket.observer.MessageObserver;
import com.ani.octopus.service.agent.domain.websocket.AniStub;
import org.junit.Test;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Vector;

import static org.junit.Assert.*;

/**
 * Created by zhaoyu on 15-10-30.
 */
public class WebSocketSessionFactoryTest {

    private static final String aniCloudSocketUri = "ws://localhost:8080/service-bus/websocket/123";

    @Test
    public void testSessionFactory() {
        // you need to implements the Invokable interface and register into
        // WebSocketClient for anicloud platform to call back
        Invokable invokable = new ClientInvokerImpl();
        WebSocketClient socketClient = new WebSocketClient(invokable);

        // you need to implements yourself observer and register on socketClient
        // to receive the message from anicloud platform
        Vector<MessageObserver> messageObservers = new Vector<>();
        messageObservers.add(new AniObjectCallMessageObserver());
        messageObservers.add(new AniFunctionCallMessageObserver());
        socketClient.setObs(messageObservers);

        // inject your WebSocketClient instance and anicloud socket destination url to factory
        // and than use factory to get the session, than you can use the session to communication
        // with anicloud platform
        WebSocketSessionFactory sessionFactory =
                new WebSocketSessionFactory(aniCloudSocketUri, socketClient);
        Session session = sessionFactory.getWebSocketSession();

        // call use AniInvokerImpl
        Invokable clientInvoke = new AniInvokerImpl(session);
        AniStub aniStub = new AniStub();
        aniStub.setObjectId(123L);
        aniStub.setStubId(456L);

        try {
            clientInvoke.invokeAniObjectSync(aniStub);
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}