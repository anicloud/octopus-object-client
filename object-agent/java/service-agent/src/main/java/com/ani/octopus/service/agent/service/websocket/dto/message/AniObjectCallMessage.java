package com.ani.octopus.service.agent.service.websocket.dto.message;

import com.ani.octopus.service.agent.service.websocket.dto.AniStub;
import com.ani.octopus.service.agent.service.websocket.dto.ResultType;

/**
 * Created by zhaoyu on 15-10-30.
 */
public class AniObjectCallMessage extends Message<AniObjectCallMessage> {

    private Long stubId;
    private ResultType resultType;
    private String result;

    public AniObjectCallMessage() {
    }

    public AniObjectCallMessage(String msg) {
        super(msg);
    }

    public AniObjectCallMessage(MessageType messageType, String msg) {
        super(messageType, msg);
    }

    public AniObjectCallMessage(AniStub aniStub) {
        this.stubId = aniStub.getStubId();
        super.messageType = MessageType.CALL_ANI_OBJECT;
        // TODO
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    @Override
    public String toString() {
        return "CallAniObjectMessage{" +
                "stubId=" + stubId +
                ", resultType=" + resultType +
                ", result='" + result + '\'' +
                "} " + super.toString();
    }
}
