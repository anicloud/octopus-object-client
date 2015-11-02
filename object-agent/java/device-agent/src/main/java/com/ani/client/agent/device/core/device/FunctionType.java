package com.ani.client.agent.device.core.device;

/**
 * Created by huangbin on 10/26/15.
 */
public enum FunctionType {
    SYNC(0),
    ASYNC(1);

    private final Integer value;

    FunctionType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public static FunctionType getType(Integer value) {
        switch (value) {
            case 0:
                return SYNC;
            case 1:
                return ASYNC;
            default:
                return SYNC;
        }
    }
}
