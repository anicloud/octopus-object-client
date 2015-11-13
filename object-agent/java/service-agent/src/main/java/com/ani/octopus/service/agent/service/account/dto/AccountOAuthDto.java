package com.ani.octopus.service.agent.service.account.dto;


/**
 * Created by zhaoyu on 15-10-16.
 */
public class AccountOAuthDto extends AbstractDto {
    private static final long serialVersionUID = -472232350042607777L;

    public Long accountId;
    public String screenName;
    public String email;
    public boolean enabled;

    public String accessToken;
    public String tokenType;
    public String refreshToken;
    public Long expiresIn;
    public String scope;
    public Long authDate;

    public String phoneNumber;
    public String address;
    public String company;
    public Long registerDate;

    public AccountOAuthDto() {
        super();
    }

    public AccountOAuthDto(Long accountId, String screenName, String email,
                           boolean enabled, String accessToken, String tokenType,
                           String refreshToken, Long expiresIn, String scope,
                           Long authDate, String phoneNumber, String address,
                           String company, Long registerDate) {
        this.accountId = accountId;
        this.screenName = screenName;
        this.email = email;
        this.enabled = enabled;
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.scope = scope;
        this.authDate = authDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.company = company;
        this.registerDate = registerDate;
    }

}
