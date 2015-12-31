package com.ani.octopus.service.agent.core.validate;

import java.io.Serializable;

/**
 * This class encapsulates the validated result message of <Strong>domain object</Strong>.
 * Hibernate Validator(it is the implementation of Bean Validation 1.1) is used for the validation.
 * <br><br>
 * Created by zhaoyu on 15-12-7.
 */
public class ValidateMessage implements Serializable {
    private static final long serialVersionUID = 4568970624428883564L;

    /**
     * field name
     */
    public String field;
    /**
     * error message
     */
    public String msg;
    /**
     * current value
     */
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
