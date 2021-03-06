package com.ani.octopus.service.agent.core.websocket;

import com.ani.bus.service.commons.dto.accountobject.AccountObject;
import com.ani.bus.service.commons.dto.anistub.AniStub;
import com.ani.bus.service.commons.dto.anistub.AniStubConnType;
import com.ani.bus.service.commons.dto.anistub.Argument;
import com.ani.bus.service.commons.message.MessageDecoder;
import com.ani.bus.service.commons.message.MessageEncoder;
import com.ani.bus.service.commons.message.SocketMessage;
import com.ani.bus.service.commons.message.callmessage.AniAccountCallMessage;
import com.ani.bus.service.commons.message.callmessage.AniObjectCallMessage;
import com.ani.bus.service.commons.message.callmessage.AniServiceCallMessage;
import com.ani.bus.service.commons.observer.MessageObservable;
import com.ani.bus.service.commons.session.AniServiceSession;
import com.ani.octopus.service.agent.service.websocket.ClientInvokable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.io.IOException;
import java.util.List;


/**
 *
 * Created by zhaoyu on 15-10-29.
 */
@ClientEndpoint(
        decoders = {MessageDecoder.class},
        encoders = {MessageEncoder.class}
)
public class WebSocketClient extends MessageObservable {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketClient.class);

    private ClientInvokable clientInvoker;
    private AniServiceSession aniServiceSession;

    public WebSocketClient() {
    }

    public WebSocketClient(ClientInvokable clientInvoker) {
        this.clientInvoker = clientInvoker;
    }

    @OnOpen
    public void onOpen(Session session) {
        LOGGER.info("connected to endpoint, session id : " + session.getId());
    }

    @OnMessage
    public void onMessage(Session session, SocketMessage message) {
        LOGGER.info("onMessage, session id {}, message {}", session.getId(), message);
        if (message instanceof AniObjectCallMessage) {
            AniObjectCallMessage msg = (AniObjectCallMessage) message;
            if (msg.getConnType() == AniStubConnType.ASYNC) {
                notifyObservers(msg);
            }
            if (msg.getConnType() == AniStubConnType.SYNC) {
                AniStub aniStub = aniServiceSession.getAniStub(msg.getAniStub().getKeyId());
                if (aniStub == null) {
                    throw new NullPointerException("AniStub is Null.");
                }
                aniStub.setOutputValues(msg.getAniStub().getOutputValues());
                synchronized (aniStub) {
                    aniStub.notify();
                }
            }

        }
        if (message instanceof AniAccountCallMessage) {
            AniAccountCallMessage msg = (AniAccountCallMessage) message;
            AccountObject accountObject = aniServiceSession.getAccountObject(msg.getAccountObject().getKeyId());
            if (accountObject == null) {
                throw new NullPointerException("AniStub is Null.");
            }
            accountObject.setResultMsg(msg);
            synchronized (accountObject) {
                accountObject.notify();
            }

        }
        if (message instanceof AniServiceCallMessage) {
            LOGGER.info("AniService call from anicloud.");
            AniServiceCallMessage msg = (AniServiceCallMessage) message;
            try {
                AniStub aniStub = msg.getAniStub();
                List<Argument> outputValues = clientInvoker.invokeAniObjectSync(aniStub);
                aniStub.setOutputValues(outputValues);
                session.getBasicRemote().sendObject(msg);
            } catch (IOException |EncodeException e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LOGGER.info("onError, {}.", throwable.getCause());
        clientInvoker.sessionOnError(session.getId(), throwable);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        LOGGER.info("onClose, session id {}.", session.getId());
        clientInvoker.sessionOnClose(session.getId(), closeReason);
    }

    public void setClientInvoker(ClientInvokable clientInvoker) {
        this.clientInvoker = clientInvoker;
    }

    public void setAniServiceSession(AniServiceSession aniServiceSession) {
        this.aniServiceSession = aniServiceSession;
    }
}