package com.ani.octopus.service.agent.service.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

/**
 * Created by zhaoyu on 15-10-16.
 */
public class AccountDto extends AbstractDto {
    private static final long serialVersionUID = 4052752760162801947L;

    public Long accountId;
    public String screenName;
    public String email;
    @JsonIgnore
    public String password;
    public boolean enabled;
    public Long registerDate;
    public AccountType accountType;

    public boolean accountNonExpired;
    public boolean accountNonLocked;
    public boolean credentialsNonExpired;

    public AccountInfoDto accountInfo;
    public Set<AccountGroupDto> groupSet;

    public AccountDto() {
    }

    public AccountDto(Long accountId) {
        this.accountId = accountId;
    }

    public AccountDto(Long accountId, String screenName, String email, String password, AccountType accountType, AccountInfoDto accountInfo) {
        this.screenName = screenName;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
        this.accountInfo = accountInfo;
    }

    public AccountDto(Long accountId, String screenName, String email, String password,
                      boolean enabled, Long registerDate, AccountType accountType,
                      boolean accountNonExpired, boolean accountNonLocked,
                      boolean credentialsNonExpired, AccountInfoDto accountInfo,
                      Set<AccountGroupDto> groupSet) {
        this.accountId = accountId;
        this.screenName = screenName;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registerDate = registerDate;
        this.accountType = accountType;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountInfo = accountInfo;
        this.groupSet = groupSet;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "accountId='" + accountId + '\'' +
                ", screenName='" + screenName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", registerDate=" + registerDate +
                ", accountType=" + accountType +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", accountInfo=" + accountInfo +
                ", groupSet=" + groupSet +
                "} " + super.toString();
    }
}
