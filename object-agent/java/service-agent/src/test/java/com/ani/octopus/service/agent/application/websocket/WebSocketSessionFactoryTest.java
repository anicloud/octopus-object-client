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
        Invokable invokable = new ClientInvokerImpl();
        WebSocketClient socketClient = new WebSocketClient(invokable);

        Vector<MessageObserver> messageObservers = new Vector<>();
        messageObservers.add(new AniObjectCallMessageObserver());
        messageObservers.add(new AniFunctionCallMessageObserver());
        socketClient.setObs(messageObservers);

        WebSocketSessionFactory sessionFactory =
                new WebSocketSessionFactory(aniCloudSocketUri, socketClient);
        Session session = sessionFactory.getWebSocketSession();

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