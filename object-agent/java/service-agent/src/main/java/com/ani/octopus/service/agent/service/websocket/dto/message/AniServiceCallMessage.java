package com.ani.octopus.service.agent.service.websocket.dto.message;


import com.ani.octopus.service.agent.service.websocket.dto.AniStub;
import com.ani.octopus.service.agent.service.websocket.dto.ResultType;

/**
 * Created by zhaoyu on 15-10-30.
 */
public class AniServiceCallMessage extends Message<AniServiceCallMessage> {
    private Long stubId;
    private ResultType resultType;
    private String result;

    public AniServiceCallMessage(AniStub stub) {
        super.messageType = MessageType.CALL_ANI_SERVICE;
    }

    public Long getStubId() {
        return stubId;
    }

    public void setStubId(Long stubId) {
        this.stubId = stubId;
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
        return "AniServiceCallMessage{" +
                "stubId=" + stubId +
                ", resultType=" + resultType +
                ", result='" + result + '\'' +
                "} " + super.toString();
    }
}
