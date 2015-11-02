package com.ani.client.agent.device.core.device;

/**
 * Created by huangbin on 10/27/15.
 */
public enum ResultType {
    SUCCESS(0),
    ERROR(1);

    private final Integer value;

    ResultType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public static ResultType getType(Integer value) {
        if (value == 0) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }
}
