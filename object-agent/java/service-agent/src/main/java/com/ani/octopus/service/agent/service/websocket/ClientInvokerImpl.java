package com.ani.octopus.service.agent.service.websocket;

import com.ani.service.bus.core.application.agent.dto.anistub.AniStub;
import com.ani.service.bus.core.application.agent.dto.anistub.Argument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.List;

/**
 * the example implementation of ClientInvokable, the third party service need to implement
 * what you want to do.<br><br>
 * <strong>Use Example:</strong><br>
 * <pre>
 *      // you need to implement the ClientInvokable interface and register on
 *      // WebSocketClient for anicloud platform to callback
 *      ClientInvokable invokable = new ClientInvokerImpl();
 *      WebSocketClient socketClient = new WebSocketClient(invokable);
 *      // you need to implement your own observer and register on socketClient
 *      // to receive the message for the asynchronous call result from anicloud platform
 *      Vector&lt;MessageObserver&gt; messageObservers = new Vector&lt;&gt;();
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
 *      serviceSession = sessionFactory.getAniServiceSession();
 *
 *      ......
 * </pre>
 * <br>
 * Created by zhaoyu on 15-10-30.
 */
public class ClientInvokerImpl implements ClientInvokable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientInvokerImpl.class);

    @Override
    public List<Argument> invokeAniObjectSync(AniStub stub) throws IOException, EncodeException {
        LOGGER.info("AniService call from anicloud, AniStub id is {}.", stub.getKeyId());
        // TODO
        return null;
    }

    @Override
    public void sessionOnClose(String sessionId, CloseReason closeReason) {
        LOGGER.info("sessionOnClose, session id {}.", sessionId);
        // TODO
    }

    @Override
    public void sessionOnError(String sessionId, Throwable throwable) {
        LOGGER.info("sessionOnError, session id {}.", sessionId);
        throwable.printStackTrace();
        // TODO
    }
}
