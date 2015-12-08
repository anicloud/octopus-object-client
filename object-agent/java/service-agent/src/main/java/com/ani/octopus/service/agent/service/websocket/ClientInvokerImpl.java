package com.ani.octopus.service.agent.service.websocket;

import com.ani.octopus.service.agent.service.websocket.dto.AniStub;
import com.ani.octopus.service.agent.service.websocket.dto.Argument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.List;

/**
 * the example implementation of Invokable, your third party service need to implement
 * what to do.
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
