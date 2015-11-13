package com.ani.octopus.service.agent.service.oauth.dto;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Created by zhaoyu on 15-10-31.
 */
public class AuthorizationCodeParameter extends AniOAuthParameter {
    private static final long serialVersionUID = 3448535996883214446L;

    private String clientId;
    private String clientSecret;
    private GrantType grantType;
    private String redirectUri;
    private ResponseType responseType;
    private Scope scope;

    public AuthorizationCodeParameter() {
    }

    public AuthorizationCodeParameter(String clientId, String clientSecret,
                                      GrantType grantType, String redirectUri,
                                      ResponseType responseType, Scope scope) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.redirectUri = redirectUri;
        this.responseType = responseType;
        this.scope = scope;
    }

    public String getClientId() {
        return clientId;
    }

    public AuthorizationCodeParameter setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public AuthorizationCodeParameter setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public GrantType getGrantType() {
        return grantType;
    }

    public AuthorizationCodeParameter setGrantType(GrantType grantType) {
        this.grantType = grantType;
        return this;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public AuthorizationCodeParameter setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public AuthorizationCodeParameter setResponseType(ResponseType responseType) {
        this.responseType = responseType;
        return this;
    }

    public Scope getScope() {
        return scope;
    }

    public AuthorizationCodeParameter setScope(Scope scope) {
        this.scope = scope;
        return this;
    }

    public MultiValueMap<String, String> convertParameterToMapForAccessToken() {
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<String, String>();
        valueMap.add("client_id", this.clientId);
        valueMap.add("client_secret", this.clientSecret);
        valueMap.add("grant_type", this.grantType.getVal());
        valueMap.add("redirect_uri", this.redirectUri);
        return valueMap;
    }

    public MultiValueMap<String, String> convertParameterToMapForRefreshToken() {
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<String, String>();
        valueMap.add("client_id", this.clientId);
        valueMap.add("client_secret", this.clientSecret);
        valueMap.add("grant_type", this.grantType.getVal());
        return valueMap;
    }

    @Override
    public String toString() {
        return "AuthorizationCodeParameter{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", grantType=" + grantType +
                ", redirectUri='" + redirectUri + '\'' +
                ", responseType=" + responseType +
                ", scope=" + scope +
                '}';
    }
}
