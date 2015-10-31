package com.ani.octopus.service.agent.application.account;

import com.ani.octopus.service.agent.domain.account.Account;
import com.ani.octopus.service.agent.domain.account.AccountGroup;
import com.ani.octopus.service.agent.domain.account.GroupType;

import java.util.List;
import java.util.Set;

/**
 * Created by zhaoyu on 15-10-31.
 */
public interface AccountGroupService {
    AccountGroup save(AccountGroup accountGroup);
    AccountGroup modify(AccountGroup accountGroup);
    void remove(Long accountId, Long groupId);

    AccountGroup getById(Long groupId);

    List<AccountGroup> getByAccountAndGroupType(Long accountId, GroupType groupType);
    Set<Account> getAccountsInGroup(Long groupId);
}
