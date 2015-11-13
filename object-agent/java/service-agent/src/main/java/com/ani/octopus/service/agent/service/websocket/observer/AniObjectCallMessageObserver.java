package com.ani.octopus.service.agent.service.websocket.observer;

import com.ani.octopus.service.agent.service.websocket.dto.message.AniObjectCallMessage;

/**
 * Created by zhaoyu on 15-10-30.
 */
public class AniObjectCallMessageObserver implements MessageObserver {
    @Override
    public void update(MessageObservable o, Object arg) {
        AniObjectCallMessage message = (AniObjectCallMessage) arg;
        System.out.println("AniObjectCallMessageObserver : " + message);
    }
}
