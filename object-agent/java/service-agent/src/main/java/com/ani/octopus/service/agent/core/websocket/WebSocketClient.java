package com.ani.octopus.service.agent.core.websocket;

import com.ani.octopus.service.agent.service.websocket.Invokable;
import com.ani.octopus.service.agent.service.websocket.dto.message.*;
import com.ani.octopus.service.agent.service.websocket.observer.MessageObservable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.io.IOException;


/**
 * Created by zhaoyu on 15-10-29.
 */
@ClientEndpoint(
        decoders = {MessageDecoder.class},
        encoders = {MessageEncoder.class}
)
public class WebSocketClient extends MessageObservable {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketClient.class);

    private Invokable clientInvoker;

    public WebSocketClient() {
    }

    public WebSocketClient(Invokable clientInvoker) {
        this.clientInvoker = clientInvoker;
    }

    @OnOpen
    public void onOpen(Session session) {
        LOGGER.info("connected to endpoint, session id : " + session.getId());
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        LOGGER.info("onMessage, session id {}, message {}", session.getId(), message);
        if (message instanceof AniObjectCallMessage) {
            AniObjectCallMessage msg = (AniObjectCallMessage) message;
            notifyObservers(msg, MessageType.CALL_ANI_OBJECT);
        }
        if (message instanceof AniAccountCallMessage) {
            notifyObservers(message, MessageType.CALL_ANI_ACCOUNT);
        }
        if (message instanceof AniServiceCallMessage) {
            // TODO
            // call client method
            try {
                clientInvoker.invokeAniObjectSync(null);
            } catch (IOException |EncodeException e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Throwable throwable) {
        LOGGER.info("onError, {}.", throwable.getCause());
        throwable.printStackTrace();
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        LOGGER.info("onClose, session id {}.", session.getId());
    }

    public void setClientInvoker(Invokable clientInvoker) {
        this.clientInvoker = clientInvoker;
    }
}