package com.ani.octopus.service.agent.service.account;


import com.anicel.commons.account.dto.AccountDto;
import com.anicel.commons.account.dto.AccountModifyDto;
import com.anicel.commons.account.dto.AccountRegisterDto;

import javax.xml.bind.ValidationException;
import java.io.UnsupportedEncodingException;

/**
 * This is the interface for account management which is used by the third party service to operate account related information.
 * <br><br>
 * Created by zhaoyu on 15-10-31.
 */
public interface AccountService {

    /**
     * register an account. but you need to know when you want to register an account, you don't need the access token.
     * @param account account basic information.
     * @return account information.
     * @throws ValidationException object validate exception
     */
    AccountDto register(AccountRegisterDto account) throws ValidationException;

    /**
     * modify account.
     * @param account modified account dto
     * @return modified account.
     * @throws ValidationException object validate exception
     */
    AccountDto modify(AccountModifyDto account) throws ValidationException;

    /**
     * find an account by id.
     * @param accountId account id.
     * @return account details.
     */
    AccountDto getByAccountId(Long accountId);

    /**
     * find an account by email.
     * @param email account email.
     * @return account details.
     */
     AccountDto getByEmail(String email);

    /**
     * find an account by phone number.
     * @param phoneNumber account phone number.
     * @return account details.
     */
    AccountDto getByPhoneNumber(String phoneNumber);

    /**
     * add an account to a group.
     * @param accountId account id.
     * @param groupId group id.
     * @return modified account.
     */
    AccountDto addAccountInGroup(Long accountId, Long groupId);
}
