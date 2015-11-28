package com.ani.octopus.service.agent.service.websocket.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Created by zhaoyu on 15-10-29.
 */
public class Argument implements Serializable {
    private static final long serialVersionUID = -135305841275262624L;

    private String name;
    private ArgumentType argumentType;
    private List<Object> value;

    public Argument() {
    }

    public Argument(String name, ArgumentType argumentType, List<Object> value) {
        this.name = name;
        this.argumentType = argumentType;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArgumentType getArgumentType() {
        return argumentType;
    }

    public void setArgumentType(ArgumentType argumentType) {
        this.argumentType = argumentType;
    }

    public List<Object> getValue() {
        return value;
    }

    public void setValue(List<Object> value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Argument argument = (Argument) o;
        return Objects.equals(name, argument.name) &&
                Objects.equals(argumentType, argument.argumentType) &&
                Objects.equals(value, argument.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, argumentType, value);
    }

    @Override
    public String toString() {
        return "Argument{" +
                "name='" + name + '\'' +
                ", argumentType=" + argumentType +
                ", value=" + value +
                '}';
    }
}
