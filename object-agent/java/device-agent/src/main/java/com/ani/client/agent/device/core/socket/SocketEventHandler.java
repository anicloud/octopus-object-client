package com.ani.client.agent.device.core.socket;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Created by huangbin on 10/21/15.
 */
public interface SocketEventHandler {
    void onConnectEvent(SocketChannel channel) throws IOException;
    void onCloseEvent(SocketChannel channel) throws IOException;
    void onReadEvent(SocketChannel channel) throws IOException;
    void onWriteEvent(SocketChannel channel) throws IOException;
}
