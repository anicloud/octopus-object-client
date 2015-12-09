package com.ani.octopus.service.agent.core.message;

import java.io.Serializable;

/**
 * Created by zhaoyu on 15-12-9.
 */
public abstract class Message implements Serializable {
    private static final long serialVersionUID = 8363025765626817366L;

    private Message.ResultCode resultCode;
    private String msg;

    public Message() {
    }

    public Message(ResultCode resultCode, String msg) {
        if (resultCode == null) {
            throw new IllegalArgumentException("resultCode cannot be null");
        }
        this.resultCode = resultCode;
        this.msg = msg;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static enum ResultCode {
        SUCCESS(1000),
        DUPLICATE_KEY_ERROR(1001),

        AUTH_ERROR(2001),
        RULE_ERROR(2002),
        DATA_ERROR(2003),

        OBJECT_CALL_ERROR(3001),
        SERVICE_CALL_ERROR(3002);

        private int code;

        ResultCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }

        public static Message.ResultCode getResultCode(final int code) {
            if (code >= 1000 && code <= 4000) {
                switch (code) {
                    case 1000:
                        return SUCCESS;
                    case 1001:
                        return DUPLICATE_KEY_ERROR;
                    default:
                        return null;

                }
            } else {
                throw new IllegalArgumentException("Invalid code: " + code);
            }
        }
    }

    @Override
    public String toString() {
        return "Message{" +
                "resultCode=" + resultCode +
                ", msg='" + msg + '\'' +
                '}';
    }
}
