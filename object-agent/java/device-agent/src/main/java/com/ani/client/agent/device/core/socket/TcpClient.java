package com.ani.client.agent.device.core.socket;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by huangbin on 10/21/15.
 */
public class TcpClient {
    private static final Logger LOG = Logger.getLogger(TcpClient.class);

    private SocketEventHandler eventHandler;
    private Selector selector;
    private SelectionKey selectionKey;
    private SocketChannel socketChannel;
    private boolean isStarted;

    public TcpClient() {
        this.isStarted = false;
    }

    public void connect(String host, int port) throws IOException {
        clear();
        selector = Selector.open();
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(InetAddress.getByName(host), port));
        selectionKey = socketChannel.register(selector, SelectionKey.OP_CONNECT);
        isStarted = true;
        startLoop();
    }

    public void close() throws IOException {
        clear();
        eventHandler.onCloseEvent(socketChannel);
    }

    private void clear() throws IOException {
        if (selectionKey != null) {
            selectionKey.cancel();
        }
        if (selector != null && selector.isOpen()) {
            selector.close();
        }
        if (socketChannel != null && socketChannel.isOpen()) {
            socketChannel.close();
        }
    }

    private void startLoop() throws IOException {
        while (selector.isOpen()) {
            selector.select();
            Iterator it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();
                SocketChannel channel = (SocketChannel) key.channel();
                it.remove();

                if (key.isValid() && key.isConnectable()) {
                    while (channel.isConnectionPending() || !channel.isConnected()) {
                        channel.finishConnect();
                    }
                    key.interestOps(SelectionKey.OP_READ);
                    eventHandler.onConnectEvent(channel);
                }

                if (key.isValid() && key.isReadable()) {
                    eventHandler.onReadEvent(channel);
                }

//                if (key.isValid() && key.isWritable()) {
//                    // Unregister the OP_WRITE because it is always ready.
//                    key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
//                    eventHandler.onWriteEvent(channel);
//                }
            }
        }
    }

    public void onWriteRequest() throws IOException {
//        selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
        eventHandler.onWriteEvent(socketChannel);
    }

    public void setEventHandler(SocketEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }
}
