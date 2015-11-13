package com.ani.octopus.service.agent.service.account.dto;

import java.io.Serializable;

/**
 * Created by zhaoyu on 15-11-11.
 */
public class AccountRegisterDto implements Serializable {
    private static final long serialVersionUID = -5229370617948473689L;

    public String screenName;
    public String email;
    public String password;
    public AccountType accountType;

    public String phoneNumber;
    public String address;
    public String company;
    public String photoPath;

    public AccountRegisterDto() {
    }

    public AccountRegisterDto(String screenName, String email, String password,
                              AccountType accountType, String phoneNumber,
                              String address, String company, String photoPath) {
        this.screenName = screenName;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.company = company;
        this.photoPath = photoPath;
    }

    public static AccountDto toAccountDto(AccountRegisterDto registerDto) {
        if (registerDto == null) {
            return null;
        }
        AccountInfoDto accountInfoDto = new AccountInfoDto(
                registerDto.phoneNumber,
                registerDto.address,
                registerDto.company,
                registerDto.photoPath
        );
        return new AccountDto(
                null,
                registerDto.screenName,
                registerDto.email,
                registerDto.password,
                registerDto.accountType,
                accountInfoDto
        );
    }
}
