package com.ani.client.agent.device.core.message;

/**
 * Created by huangbin on 10/27/15.
 */
public enum MessageType {
    REGISTER_REQUEST(0),
    REGISTER_RESPONSE(1),
    AUTH_REQUEST(2),
    AUTH_RESPONSE(3),
    UPDATE_REQUEST(4),
    UPDATE_RESPONSE(5),
    INVOKE_REQUEST(6),
    INVOKE_RESPONSE(7),
    UNKNOWN(8);

    private Integer value;

    MessageType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static MessageType getType(Integer value) {
        switch(value) {
            case 0:
                return REGISTER_REQUEST;
            case 1:
                return REGISTER_RESPONSE;
            case 2:
                return AUTH_REQUEST;
            case 3:
                return AUTH_RESPONSE;
            case 4:
                return UPDATE_REQUEST;
            case 5:
                return UPDATE_RESPONSE;
            case 6:
                return INVOKE_REQUEST;
            case 7:
                return INVOKE_RESPONSE;
            default:
                return UNKNOWN;
        }
    }
}
