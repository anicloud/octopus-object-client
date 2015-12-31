package com.ani.octopus.service.agent.service.oauth.dto;

/**
 * Response type of OAuth2.
 * <br><br>
 * Created by zhaoyu on 15-10-31.
 */
public enum ResponseType {
    CODE("code"), TOKEN("token");

    private String val;
    ResponseType(String val) {
        this.val = val;
    }

    public String getVal() {
        return this.val;
    }
}
