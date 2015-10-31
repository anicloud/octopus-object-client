package com.ani.client.agent.device.core.message;


/**
 * Created by huangbin on 10/22/15.
 */
public interface MessageHandler {
    public void onMessage(Message message) throws Exception;
    public void onConnect() throws Exception;
    public void onClose() throws Exception;
}
