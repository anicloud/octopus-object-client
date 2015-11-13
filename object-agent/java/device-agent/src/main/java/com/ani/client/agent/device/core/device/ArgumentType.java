package com.ani.client.agent.device.core.device;

/**
 * Created by huangbin on 10/26/15.
 */
public enum ArgumentType {
    BOOLEAN(0, "Boolean", Boolean.class),
    BYTE(1, "Byte", Byte.class),
    SHORT(2, "Short", Short.class),
    INTEGER(3, "Integer", Integer.class),
    LONG(4, "Long", Long.class),
    FLOAT(5, "Float", Float.class),
    DOUBLE(6, "Double", Double.class),
    STRING(7, "String", String.class);

    private final Integer value;
    private final String name;
    private final Class typeClass;

    ArgumentType(Integer value, String name, Class typeClass) {
        this.value = value;
        this.name = name;
        this.typeClass = typeClass;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public Class getTypeClass() {
        return typeClass;
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
            default:
                return STRING;
        }
    }
}
