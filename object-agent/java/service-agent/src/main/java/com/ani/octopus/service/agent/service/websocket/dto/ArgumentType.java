package com.ani.octopus.service.agent.service.websocket.dto;


import java.util.List;

/**
 * Created by zhaoyu on 15-10-29.
 */
public enum ArgumentType {
    INTEGER(Integer.class),
    PERCENTAGE(Short.class),
    FLOAT(Float.class),
    STRING(String.class),
    BOOLEAN(Boolean.class),
    BINARY(byte[].class),
    OBJECT(Object.class),
    LIST(List.class);

    private final Class dataClass;

    ArgumentType(Class dataClass) {
        this.dataClass = dataClass;
    }

    public Class getValue(){
        return this.dataClass;
    }
}
