package com.ani.octopus.service.agent.application.account;

import com.ani.octopus.service.agent.domain.account.Account;
import com.ani.octopus.service.agent.domain.account.AccountGroup;
import com.ani.octopus.service.agent.domain.account.GroupType;

import java.util.List;
import java.util.Set;

/**
 * Created by zhaoyu on 15-10-31.
 */
public class AccountGroupServiceImpl implements AccountGroupService {

    @Override
    public AccountGroup save(AccountGroup accountGroup) {
        // TODO
        return null;
    }

    @Override
    public AccountGroup modify(AccountGroup accountGroup) {
        // TODO
        return null;
    }

    @Override
    public void remove(Long accountId, Long groupId) {
        // TODO

    }

    @Override
    public AccountGroup getById(Long groupId) {
        // TODO
        return null;
    }

    @Override
    public List<AccountGroup> getByAccountAndGroupType(Long accountId, GroupType groupType) {
        // TODO
        return null;
    }

    @Override
    public Set<Account> getAccountsInGroup(Long groupId) {
        // TODO
        return null;
    }
}
