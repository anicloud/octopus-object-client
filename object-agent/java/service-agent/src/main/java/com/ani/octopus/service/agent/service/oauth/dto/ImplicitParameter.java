package com.ani.octopus.service.agent.service.oauth.dto;

import javax.validation.constraints.NotNull;

/**
 * This class provides the parameters for <b>OAuth2 Implicit Model</b>.
 * See <a href="https://github.com/jeansfish/RFC6749.zh-cn/blob/master/Section04/4.2.md" target="_blank">Implicit</a> details.
 * <br><br>
 * <strong>Notice:</strong><br>
 * Anicloud Platform doesn't support Implicit Model, maybe it will support in the feature.
 * <br><br>
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
