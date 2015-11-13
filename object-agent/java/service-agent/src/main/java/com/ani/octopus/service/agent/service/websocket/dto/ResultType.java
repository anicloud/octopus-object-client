package com.ani.octopus.service.agent.service.websocket.dto;

/**
 * Created by zhaoyu on 15-10-29.
 */
public enum ResultType {
    SUCCESS(0),
    ERROR(1);

    private final int value;

    ResultType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ResultType getType(int value) {
        if (value == 0) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }
}
