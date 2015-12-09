package com.ani.octopus.service.agent.core.message;

/**
 * Created by zhaoyu on 15-10-30.
 */
public class SocketMessage extends Message {
    private static final long serialVersionUID = 2242277563485901805L;

    private MessageType messageType;

    public SocketMessage() {
    }

    public SocketMessage(MessageType messageType) {
        this.messageType = messageType;
    }

    public SocketMessage(ResultCode resultCode, String msg, MessageType messageType) {
        super(resultCode, msg);
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "SocketMessage{" +
                "messageType=" + messageType +
                "} " + super.toString();
    }
}
