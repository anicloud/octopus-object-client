package com.ani.octopus.service.agent.service.oauth.dto;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.validation.constraints.NotNull;

/**
 * This class provides the parameters for <b>OAuth2 Password Model</b>.
 * See <a href="https://github.com/jeansfish/RFC6749.zh-cn/blob/master/Section04/4.3.md" target="_blank" >Password Model</a> Details.
 * <br><br>
 * Created by zhaoyu on 15-10-31.
 */
public class PasswordParameter extends AniOAuthParameter {
    private static final long serialVersionUID = 430833401426197508L;

    @NotNull
    private String clientId;
    @NotNull
    private String clientSecret;
    @NotNull
    private GrantType grantType;
    @NotNull
    private Scope scope;

    @NotNull
    private String userName;
    @NotNull
    private String password;

    public PasswordParameter() {
    }

    public PasswordParameter(String clientId, String clientSecret,
                             GrantType grantType, String password, Scope scope, String userName) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.password = password;
        this.scope = scope;
        this.userName = userName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public GrantType getGrantType() {
        return grantType;
    }

    public void setGrantType(GrantType grantType) {
        this.grantType = grantType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public MultiValueMap<String, String> convertParameter() {
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("client_id", this.clientId);
        valueMap.add("client_secret", this.clientSecret);
        valueMap.add("grant_type", this.grantType.getVal());
        valueMap.add("scope", this.scope.getVal());
        valueMap.add("username", this.userName);
        valueMap.add("password", this.password);
        return valueMap;
    }

    @Override
    public String toString() {
        return "PasswordParameter{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", grantType=" + grantType +
                ", scope=" + scope +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
