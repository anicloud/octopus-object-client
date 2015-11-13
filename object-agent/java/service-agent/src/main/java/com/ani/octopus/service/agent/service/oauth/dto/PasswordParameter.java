package com.ani.octopus.service.agent.service.oauth.dto;

/**
 * Created by zhaoyu on 15-10-31.
 */
public class PasswordParameter extends AniOAuthParameter {
    private static final long serialVersionUID = 430833401426197508L;
    private String clientId;
    private String clientSecret;
    private GrantType grantType;
    private Scope scope;

    private String userName;
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
