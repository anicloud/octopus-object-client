package com.ani.octopus.service.agent.application.account;

import com.ani.octopus.service.agent.domain.account.Account;

/**
 * Created by zhaoyu on 15-10-31.
 */
public interface AccountService {
    Account register(Account account);
    Account modify(Account account);

    Account getByAccountId(Long accountId);
    Account getByEmail(String email);
    Account getByPhoneNumber(String phoneNumber);

    Account addAccountGroup(Long accountId, Long groupId);
}
