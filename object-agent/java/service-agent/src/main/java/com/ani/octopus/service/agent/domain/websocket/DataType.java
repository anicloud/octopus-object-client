package com.ani.octopus.service.agent.domain.websocket;


import java.util.List;

/**
 * Created by zhaoyu on 15-10-29.
 */
public enum DataType {
    INTEGER(Integer.class),
    PERCENTAGE(Short.class),
    FLOAT(Float.class),
    STRING(String.class),
    BOOLEAN(Boolean.class),
    BINARY(byte[].class),
    OBJECT(Object.class),
    LIST(List.class);

    private final Class dataClass;

    DataType(Class dataClass) {
        this.dataClass = dataClass;
    }

    public Class getValue(){
        return this.dataClass;
    }
}
