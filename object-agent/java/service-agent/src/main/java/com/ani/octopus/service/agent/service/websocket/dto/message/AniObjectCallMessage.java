package com.ani.octopus.service.agent.service.websocket.dto.message;

import com.ani.octopus.service.agent.service.websocket.dto.AniStub;
import com.ani.octopus.service.agent.service.websocket.dto.ResultType;

/**
 * Created by zhaoyu on 15-10-30.
 */
public class AniObjectCallMessage extends Message<AniObjectCallMessage> {

    private AniStub aniStub;

    public AniObjectCallMessage(AniStub aniStub) {
        super(MessageType.CALL_ANI_OBJECT);
        this.aniStub = aniStub;
    }

    public AniStub getAniStub() {
        return aniStub;
    }

    public void setAniStub(AniStub aniStub) {
        this.aniStub = aniStub;
    }

    @Override
    public String toString() {
        return "AniObjectCallMessage{" +
                "aniStub=" + aniStub +
                "} " + super.toString();
    }
}
