package com.ani.octopus.service.agent.domain.oauth;

/**
 * Created by zhaoyu on 15-10-31.
 */
public enum  Scope {
    READ("read"),
    WRITE("write"),
    READ_WRITE("read write");

    private String val;
    private Scope(String scope) {
        this.val = scope;
    }

    public String getVal() {
        return val;
    }
}
