package com.ani.octopus.service.agent.service.oauth.dto;

/**
 * Grant type of OAuth2.
 * <br><br>
 * Created by zhaoyu on 15-10-31.
 */
public enum GrantType {
    AUTHORIZATION_CODE("authorization_code"),
    PASSWORD("password"),
    IMPLICIT("implicit"),
    REFRESH_TOKEN("refresh_token");

    private String val;
    private GrantType(String val) {
        this.val = val;
    }

    public String getVal() {
        return this.val;
    }
}
