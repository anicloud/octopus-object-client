package com.ani.client.agent.device.core.message;


import java.io.IOException;

/**
 * Created by huangbin on 10/22/15.
 */
public interface MessageHandler {
    public void onMessage(Message message) throws IOException;
    public void onConnect() throws IOException;
    public void onClose() throws IOException;
}
