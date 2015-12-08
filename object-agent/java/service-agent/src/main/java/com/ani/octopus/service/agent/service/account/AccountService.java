package com.ani.octopus.service.agent.service.account;

import com.ani.octopus.service.agent.service.account.dto.AccountDto;
import com.ani.octopus.service.agent.service.account.dto.AccountModifyDto;
import com.ani.octopus.service.agent.service.account.dto.AccountRegisterDto;

import javax.xml.bind.ValidationException;

/** The service for Account manager.
 * Created by zhaoyu on 15-10-31.
 */
public interface AccountService {
    /**
     * register an account.
     * @param account account basic information.
     * @return account information.
     */
    AccountDto register(AccountRegisterDto account) throws ValidationException;

    /**
     * modify account.
     * @param account
     * @return modified account.
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
