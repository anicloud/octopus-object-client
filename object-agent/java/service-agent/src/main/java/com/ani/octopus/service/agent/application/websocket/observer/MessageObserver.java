package com.ani.octopus.service.agent.application.websocket.observer;

/**
 * the observer who can accept the specified the message.
 * Created by zhaoyu on 15-10-30.
 */
public interface MessageObserver {
    void update(MessageObservable o, Object arg);
}
