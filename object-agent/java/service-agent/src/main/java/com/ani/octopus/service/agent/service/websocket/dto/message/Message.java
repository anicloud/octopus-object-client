package com.ani.octopus.service.agent.service.websocket.dto.message;

import java.io.Serializable;

/**
 * Created by zhaoyu on 15-10-30.
 */
public abstract class Message<T> implements Serializable {
    private static final long serialVersionUID = 2242277563485901805L;

    public MessageType messageType;
    public String msg;

    public Message() {
    }

    public Message(String msg) {
        this.msg = msg;
    }

    public Message(MessageType messageType, String msg) {
        this.messageType = messageType;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "commandType=" + messageType +
                ", msg='" + msg + '\'' +
                '}';
    }
}
