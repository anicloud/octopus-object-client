package com.ani.octopus.service.agent.service.websocket.account;

/**
 * Created by zhaoyu on 15-11-14.
 */
public enum AniObjectState {
    ACTIVE,
    DISABLE,
    REMOVED;

    public static AniObjectState[] values = AniObjectState.values();
}
