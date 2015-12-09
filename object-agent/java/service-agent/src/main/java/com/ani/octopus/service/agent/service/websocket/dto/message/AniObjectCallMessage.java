package com.ani.octopus.service.agent.service.websocket.dto.message;

import com.ani.octopus.service.agent.core.message.MessageType;
import com.ani.octopus.service.agent.core.message.SocketMessage;
import com.ani.octopus.service.agent.service.websocket.dto.AniStub;
import com.ani.octopus.service.agent.service.websocket.dto.AniStubConnType;

/**
 * Created by zhaoyu on 15-10-30.
 */
public class AniObjectCallMessage extends SocketMessage {

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
