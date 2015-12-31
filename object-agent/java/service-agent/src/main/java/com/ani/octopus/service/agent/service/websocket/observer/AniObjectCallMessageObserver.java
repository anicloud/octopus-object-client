package com.ani.octopus.service.agent.service.websocket.observer;

import com.anicel.commons.service.bus.observer.MessageObservable;
import com.anicel.commons.service.bus.observer.MessageObserver;

/**
 * If you want to use <b>Async</b> methods, you need to register your own <b>Observer</b> which implements the MessageObserver interface
 * to accept the result from Anicloud Platform.
 * <br><br>
 * Created by zhaoyu on 15-10-30.
 */
public class AniObjectCallMessageObserver implements MessageObserver {
    @Override
    public void update(MessageObservable o, Object arg) {
        // TODO
    }
}
