package com.ani.client.agent.device.core.device;

/**
 * Created by huangbin on 10/27/15.
 */
public enum ResultType {
    SUCCESS(0, "Success"),
    ERROR(1, "Error");

    private final Integer value;
    private final String name;

    ResultType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public static ResultType getType(Integer value) {
        if (value == 0) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }
}
