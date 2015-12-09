package com.ani.octopus.service.agent.service.oauth.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by zhaoyu on 15-10-31.
 */
public class ImplicitParameter extends AniOAuthParameter {
    private static final long serialVersionUID = 2870285652549125206L;

    @NotNull
    private String clientId;
    @NotNull
    private String clientSecret;
    @NotNull
    private GrantType grantType;
    @NotNull
    private Scope scope;

    public ImplicitParameter(String clientId, String clientSecret, GrantType grantType, Scope scope) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.scope = scope;
    }
}
