package com.ani.client.agent.device.core.socket;

import com.ani.client.agent.device.core.message.Message;
import com.ani.client.agent.device.core.message.MessageHandler;
import com.ani.client.agent.device.core.message.MessageUtils;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Semaphore;

/**
 * Created by huangbin on 10/21/15.
 */
public class IoHandler implements SocketEventHandler {

    private static final Logger LOG = Logger.getLogger(IoHandler.class);
    public static final int BUFFER_SIZE = 40960;
    private ByteBuffer inBuffer;
    private ByteBuffer outBuffer;

    private MessageHandler messageHandler;

    private TcpClient client;

    private final Semaphore writeSemaphore = new Semaphore(1);

    public IoHandler(TcpClient client) {
        this.inBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        this.outBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        this.client = client;
        this.client.setEventHandler(this);
    }

    public void onConnectEvent(SocketChannel channel) throws Exception {
        LOG.info("connect event");
        messageHandler.onConnect();
    }

    public void onCloseEvent(SocketChannel channel) throws Exception {
        LOG.info("close event");
        messageHandler.onClose();
    }

    public void onReadEvent(SocketChannel channel) throws Exception {
        LOG.info("read event");
        channel.read(inBuffer);

        ByteBuffer viewBuffer = inBuffer.asReadOnlyBuffer();
        viewBuffer.flip();

        int length = viewBuffer.getInt();
        int actualLength = viewBuffer.limit();

        if (actualLength >= length) {
            byte[] inBytes = new byte[length];
            inBuffer.flip();
            inBuffer.getInt(); // remove the LENGTH head
            inBuffer.get(inBytes, 0, length);
            inBuffer.compact();
            messageHandler.onMessage(MessageUtils.decodeMessage(inBytes));
        }
    }

    public void onWriteEvent(SocketChannel channel) throws Exception {
        LOG.info("write event");
        outBuffer.flip();
        channel.write(outBuffer);
        outBuffer.clear();
        writeSemaphore.release();
    }

    private void write(byte [] out) throws Exception {
        writeSemaphore.acquire();
        outBuffer.putInt(out.length); // add the LENGTH head
        outBuffer.put(out);
        client.onWriteRequest();
    }

    public void sendMessage(Message message) throws Exception {
        write(MessageUtils.encodeMessage(message));
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public TcpClient getClient() {
        return client;
    }
}
