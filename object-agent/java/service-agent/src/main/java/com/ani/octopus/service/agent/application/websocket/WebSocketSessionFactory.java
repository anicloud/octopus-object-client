package com.ani.octopus.service.agent.application.websocket;

import com.ani.octopus.service.agent.application.websocket.observer.MessageObserver;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

/**
 * Created by zhaoyu on 15-10-29.
 */
public class WebSocketSessionFactory {

    private String aniCloudSocketUri = "ws://localhost:8080/service-bus/websocket/123";
    private static Session session;
    private WebSocketClient webSocketClient;

    public WebSocketSessionFactory() {
    }

    public WebSocketSessionFactory(String aniCloudSocketUri, WebSocketClient webSocketClient) {
        this.aniCloudSocketUri = aniCloudSocketUri;
        this.webSocketClient = webSocketClient;
    }

    public synchronized  Session getWebSocketSession() {
        if (this.webSocketClient == null) {
            throw new NullPointerException("webSocketClient is null.");
        }
        if (session == null) {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            try {
                session = container.connectToServer(this.webSocketClient, URI.create(aniCloudSocketUri));
            } catch (DeploymentException | IOException e) {
                e.printStackTrace();
            }
        }
        return session;
    }

    public void setAniCloudSocketUri(String aniCloudSocketUri) {
        this.aniCloudSocketUri = aniCloudSocketUri;
    }

    public void setWebSocketClient(WebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
    }
}
