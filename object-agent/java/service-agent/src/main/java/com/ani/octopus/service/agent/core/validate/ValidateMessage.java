package com.ani.octopus.service.agent.core.validate;

import java.io.Serializable;

/**
 * Created by zhaoyu on 15-12-7.
 */
public class ValidateMessage implements Serializable {
    private static final long serialVersionUID = 4568970624428883564L;

    public String field;
    public String msg;
    public String currentValue;

    public ValidateMessage() {
    }

    public ValidateMessage(String field, String msg, String currentValue) {
        this.field = field;
        this.msg = msg;
        this.currentValue = currentValue;
    }

    @Override
    public String toString() {
        return "ValidateMessage{" +
                "field='" + field + '\'' +
                ", msg='" + msg + '\'' +
                ", currentValue='" + currentValue + '\'' +
                '}';
    }
}
