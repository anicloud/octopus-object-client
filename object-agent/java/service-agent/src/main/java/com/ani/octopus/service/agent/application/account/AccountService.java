package com.ani.octopus.service.agent.application.account;

import com.ani.octopus.service.agent.domain.account.Account;

/** The service for Account manager.
 * Created by zhaoyu on 15-10-31.
 */
public interface AccountService {
    /**
     * register an account.
     * @param account account basic information.
     * @return account information.
     */
    Account register(Account account);

    /**
     * modify account.
     * @param account
     * @return modified account.
     */
    Account modify(Account account);

    /**
     * find an account by id.
     * @param accountId account id.
     * @return account details.
     */
    Account getByAccountId(Long accountId);

    /**
     * find an account by email.
     * @param email account email.
     * @return account details.
     */
    Account getByEmail(String email);

    /**
     * find an account by phone number.
     * @param phoneNumber account phone number.
     * @return account details.
     */
    Account getByPhoneNumber(String phoneNumber);

    /**
     * add an account to a group.
     * @param accountId account id.
     * @param groupId group id.
     * @return modified account.
     */
    Account addAccountGroup(Long accountId, Long groupId);
}
