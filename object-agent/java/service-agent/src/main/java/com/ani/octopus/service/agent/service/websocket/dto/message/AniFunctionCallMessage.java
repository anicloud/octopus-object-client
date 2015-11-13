package com.ani.octopus.service.agent.service.websocket.dto.message;


import com.ani.octopus.service.agent.service.websocket.dto.AniFunction;
import com.ani.octopus.service.agent.service.websocket.dto.ResultType;

/**
 * Created by zhaoyu on 15-10-30.
 */
public class AniFunctionCallMessage extends Message<AniFunctionCallMessage> {
    private static final long serialVersionUID = -8753061323601286165L;

    private Long aniFunctionId;
    private ResultType resultType;
    private String result;

    public AniFunctionCallMessage() {
        super.messageType = MessageType.CALL_SYSTEM;
    }

    public AniFunctionCallMessage(String msg, Long aniFunctionId, ResultType resultType, String result) {
        super(msg);
        super.messageType = MessageType.CALL_SYSTEM;
        this.aniFunctionId = aniFunctionId;
        this.resultType = resultType;
        this.result = result;
    }

    public AniFunctionCallMessage(AniFunction aniFunction) {
        this.aniFunctionId = aniFunctionId;
        // TODO
    }

    public Long getAniFunctionId() {
        return aniFunctionId;
    }

    public void setAniFunctionId(Long aniFunctionId) {
        this.aniFunctionId = aniFunctionId;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "AniFunctionCallMessage{" +
                "aniFunctionId=" + aniFunctionId +
                ", resultType=" + resultType +
                ", result='" + result + '\'' +
                "} " + super.toString();
    }
}
