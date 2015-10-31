package com.ani.client.agent.device.core.device;

/**
 * Created by huangbin on 10/26/15.
 */
public enum ArgumentType {
    BOOLEAN(0),
    BYTE(1),
    SHORT(2),
    INTEGER(3),
    LONG(4),
    FLOAT(5),
    DOUBLE(6),
    STRING(7),
    OBJECT(8);

    private final Integer value;

    ArgumentType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static ArgumentType getType(Integer value) {
        switch (value) {
            case 0:
                return BOOLEAN;
            case 1:
                return BYTE;
            case 2:
                return SHORT;
            case 3:
                return INTEGER;
            case 4:
                return LONG;
            case 5:
                return FLOAT;
            case 6:
                return DOUBLE;
            case 7:
                return STRING;
            case 8:
            default:
                return OBJECT;
        }
    }
}
