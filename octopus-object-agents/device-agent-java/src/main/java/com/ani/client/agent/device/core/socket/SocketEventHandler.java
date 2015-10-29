package com.ani.client.agent.device.core.socket;

import java.nio.channels.SocketChannel;

/**
 * Created by huangbin on 10/21/15.
 */
public interface SocketEventHandler {
    void onConnectEvent(SocketChannel channel) throws Exception;
    void onCloseEvent(SocketChannel channel) throws Exception;
    void onReadEvent(SocketChannel channel) throws Exception;
    void onWriteEvent(SocketChannel channel) throws Exception;
}
