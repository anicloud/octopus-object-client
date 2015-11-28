package com.ani.octopus.service.agent.service.websocket.dto.message;

import com.ani.octopus.service.agent.service.websocket.dto.AniStub;
import com.ani.octopus.service.agent.service.websocket.dto.AniStubConnType;
import com.ani.octopus.service.agent.service.websocket.dto.ResultType;

/**
 * Created by zhaoyu on 15-10-30.
 */
public class AniObjectCallMessage extends Message {

    private AniStub aniStub;
    private AniStubConnType connType;

    public AniObjectCallMessage() {
        super();
    }

    public AniObjectCallMessage(AniStub aniStub, AniStubConnType connType) {
        super(MessageType.CALL_ANI_OBJECT);
        this.aniStub = aniStub;
    }

    public AniStub getAniStub() {
        return aniStub;
    }

    public void setAniStub(AniStub aniStub) {
        this.aniStub = aniStub;
    }

    public AniStubConnType getConnType() {
        return connType;
    }

    public void setConnType(AniStubConnType connType) {
        this.connType = connType;
    }

    @Override
    public String toString() {
        return "AniObjectCallMessage{" +
                "aniStub=" + aniStub +
                ", connType=" + connType +
                "} " + super.toString();
    }
}
