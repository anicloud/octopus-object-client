package com.ani.octopus.service.agent.domain.websocket;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhaoyu on 15-10-29.
 */
public class Argument implements Serializable {
    private static final long serialVersionUID = -135305841275262624L;

    private String name;
    private DataType dataType;
    private List<Object> value;

    public Argument() {
    }

    public Argument(String name, DataType dataType, List<Object> value) {
        this.name = name;
        this.dataType = dataType;
        this.value = value;
    }
}
