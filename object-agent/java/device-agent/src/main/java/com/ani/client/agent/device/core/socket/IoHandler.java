package com.ani.client.agent.device.core.socket;


import com.ani.bus.device.commons.dto.message.DeviceMessage;
import com.ani.bus.device.commons.dto.message.MessageUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Semaphore;

/**
 * Created by huangbin on 10/21/15.
 */
public class IoHandler implements SocketEventHandler {
    private static final Logger LOG = Logger.getLogger(IoHandler.class);

    private static final int BUFFER_SIZE = 0x10000; // 64kB
    private ByteBuffer inBuffer;
    private ByteBuffer outBuffer;

    private DeviceMessageHandler messageHandler;

    private TcpClient client;

    private final Semaphore writeSemaphore = new Semaphore(1);

    public IoHandler(TcpClient client) {
        this.inBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        this.outBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        this.client = client;
        this.client.setEventHandler(this);
    }

    @Override
    public void onConnectEvent(SocketChannel channel) throws IOException {
      //  LOG.info("connect event");
        messageHandler.onConnect();
    }

    @Override
    public void onCloseEvent(SocketChannel channel) throws IOException {
      //  LOG.info("close event");
        messageHandler.onClose();
    }

    @Override
    public void onReadEvent(SocketChannel channel) throws IOException {
        // LOG.info("read event");
        if (channel.read(inBuffer) == -1) {
            LOG.info("socket closed, read -1 bytes");
            client.close();
        } else {
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
    }

    @Override
    public void onWriteEvent(SocketChannel channel) throws IOException {
        // LOG.info("write event");
        outBuffer.flip();
        while (outBuffer.remaining() > 0) {
            // LOG.info(channel.write(outBuffer));
            channel.write(outBuffer);
        }
        outBuffer.clear();
        writeSemaphore.release();
    }


    private void write(byte [] out) throws IOException {
        try {
            writeSemaphore.acquire();
        } catch (InterruptedException e) {
            throw new IOException(e.getCause());
        }
        outBuffer.putInt(out.length); // add the LENGTH head
        outBuffer.put(out);
        client.onWriteRequest();
    }

    public void sendMessage(DeviceMessage message) throws IOException {
        write(MessageUtils.encodeMessage(message));
    }

    public void setMessageHandler(DeviceMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public TcpClient getClient() {
        return client;
    }
}
