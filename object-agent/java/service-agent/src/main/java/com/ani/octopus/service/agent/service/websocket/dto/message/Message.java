package com.ani.octopus.service.agent.service.websocket.dto.message;

import com.ani.octopus.service.agent.service.websocket.dto.AniStubConnType;
import com.ani.octopus.service.agent.service.websocket.dto.ResultType;

import java.io.Serializable;

/**
 * Created by zhaoyu on 15-10-30.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 2242277563485901805L;

    public MessageType messageType;
    public ResultType resultType;
    public String msg;

    public Message() {
    }

    public Message(MessageType messageType) {
        this.messageType = messageType;
    }

    public Message(MessageType messageType, ResultType resultType, String msg) {
        this.messageType = messageType;
        this.resultType = resultType;
        this.msg = msg;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + messageType +
                ", resultType=" + resultType +
                ", msg='" + msg + '\'' +
                '}';
    }
}
