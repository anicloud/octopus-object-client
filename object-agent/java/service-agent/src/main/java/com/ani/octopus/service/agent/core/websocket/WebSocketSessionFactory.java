package com.ani.octopus.service.agent.core.websocket;

import com.ani.bus.service.commons.session.AniServiceSession;
import com.ani.octopus.service.agent.core.config.AnicelMeta;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

/**
 * The web socket session factory.
 * <br><br>
 * Created by zhaoyu on 15-10-29.
 */
public class WebSocketSessionFactory {
    private static final String ANI_SERVICE_ID = "aniServiceId";
    private static final String CLIENT_SECRET = "clientSecret";

    private AniServiceSession aniServiceSession;

    private WebSocketClient webSocketClient;
    private AnicelMeta anicelMeta;

    private String aniServiceId;
    private String clientSecret;

    public WebSocketSessionFactory() {
    }

    public WebSocketSessionFactory(WebSocketClient webSocketClient,
                                   AnicelMeta anicelMeta,
                                   String aniServiceId,
                                   String clientSecret) {
        this.webSocketClient = webSocketClient;
        this.anicelMeta = anicelMeta;
        this.aniServiceId = aniServiceId;
        this.clientSecret = clientSecret;
    }

    /**
     * get the singleton web socket session instance.
     * @return AniServiceSession instance
     */
    public synchronized  AniServiceSession getAniServiceSession() {
        if (this.webSocketClient == null || aniServiceId == null || clientSecret == null) {
            throw new NullPointerException("some parameter is null.");
        }
        if (aniServiceSession == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.anicelMeta.getServiceBusWebSocketUrl())
                    .append("/")
                    .append(this.aniServiceId)
                    .append("/")
                    .append(this.clientSecret);

            System.out.println(stringBuilder.toString());
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            try {
                Session session = container.connectToServer(this.webSocketClient, URI.create(stringBuilder.toString()));
                aniServiceSession = new AniServiceSession(session);
                this.webSocketClient.setAniServiceSession(aniServiceSession);
            } catch (DeploymentException | IOException e) {
                e.printStackTrace();
            }
        }
        return aniServiceSession;
    }

    public void setWebSocketClient(WebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    public void setAnicelMeta(AnicelMeta anicelMeta) {
        this.anicelMeta = anicelMeta;
    }

    public void setAniServiceId(String aniServiceId) {
        this.aniServiceId = aniServiceId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
