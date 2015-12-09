package com.ani.octopus.service.agent.core.message;

import com.ani.octopus.service.agent.core.message.Message;

/**
 * Created by zhaoyu on 15-12-9.
 */
public class HttpMessage<T> extends Message {
    private T returnObj;

    public HttpMessage() {
    }

    public HttpMessage(T returnObj) {
        this.returnObj = returnObj;
    }

    public HttpMessage(ResultCode resultCode, String msg, T returnObj) {
        super(resultCode, msg);
        this.returnObj = returnObj;
    }

    public T getReturnObj() {
        return returnObj;
    }

    public void setReturnObj(T returnObj) {
        this.returnObj = returnObj;
    }

    @Override
    public String toString() {
        return "HttpMessage{" +
                "returnObj=" + returnObj +
                "} " + super.toString();
    }
}
