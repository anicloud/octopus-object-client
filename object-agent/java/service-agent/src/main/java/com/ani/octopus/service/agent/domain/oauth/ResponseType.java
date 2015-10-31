package com.ani.octopus.service.agent.domain.oauth;

/**
 * Created by zhaoyu on 15-10-31.
 */
public enum ResponseType {
    CODE("code"), TOKEN("token");

    private String val;
    private ResponseType(String val) {
        this.val = val;
    }

    public String getVal() {
        return this.val;
    }
}
