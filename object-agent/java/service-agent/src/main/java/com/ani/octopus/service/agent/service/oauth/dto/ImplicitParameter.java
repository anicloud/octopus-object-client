package com.ani.octopus.service.agent.service.oauth.dto;

/**
 * Created by zhaoyu on 15-10-31.
 */
public class ImplicitParameter extends AniOAuthParameter {
    private static final long serialVersionUID = 2870285652549125206L;

    private String clientId;
    private String clientSecret;
    private GrantType grantType;
    private Scope scope;

    public ImplicitParameter(String clientId, String clientSecret, GrantType grantType, Scope scope) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.scope = scope;
    }
}
