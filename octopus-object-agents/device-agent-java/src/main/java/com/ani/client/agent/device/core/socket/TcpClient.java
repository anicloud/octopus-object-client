package com.ani.client.agent.device.core.socket;

import org.apache.log4j.Logger;

import java.net.InetAddress;
import java.net.InetSocketAddress;
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

    public void connect(String host, int port) throws Exception {
        if (!isStarted) {
            selector = Selector.open();
        }
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(InetAddress.getByName(host), port));
        selectionKey = socketChannel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void close() throws Exception {
        socketChannel.close();
    }

    public void startLoop() throws Exception {
        isStarted = true;
        while (isStarted) {
            selector.select();
            Iterator it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey selectionKey = (SelectionKey) it.next();
                SocketChannel channel = (SocketChannel) selectionKey.channel();
                it.remove();

                if (selectionKey.isConnectable()) {
                    while (channel.isConnectionPending()) {
                        channel.finishConnect();
                    }
                    selectionKey.interestOps(SelectionKey.OP_READ);
                    eventHandler.onConnectEvent(channel);
                }

                if (selectionKey.isReadable()) {
                    eventHandler.onReadEvent(channel);
                }

                if (selectionKey.isWritable()) {
                    // Unregister the OP_WRITE because it is always ready.
                    selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_WRITE);
                    eventHandler.onWriteEvent(channel);
                }
            }
        }
    }

    public void onWriteRequest() {
        selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
    }

    public void setEventHandler(SocketEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }
}
