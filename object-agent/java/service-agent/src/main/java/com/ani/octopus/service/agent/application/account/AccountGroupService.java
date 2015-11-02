package com.ani.octopus.service.agent.application.account;

import com.ani.octopus.service.agent.domain.account.Account;
import com.ani.octopus.service.agent.domain.account.AccountGroup;
import com.ani.octopus.service.agent.domain.account.GroupType;

import java.util.List;
import java.util.Set;

/**
 * The account group manager service.
 * Created by zhaoyu on 15-10-31.
 */
public interface AccountGroupService {
    /**
     * add an account group.
     * @param accountGroup the basic information of an account group.
     * @return the added account group.
     */
    AccountGroup save(AccountGroup accountGroup);

    /**
     * modify an account group.
     * @param accountGroup the modified information of an account group.
     * @return modified account group.
     */
    AccountGroup modify(AccountGroup accountGroup);

    /**
     * remove an account group.
     * @param accountId account id.
     * @param groupId group id.
     */
    void remove(Long accountId, Long groupId);

    /**
     * find an account group by group id.
     * @param groupId group id.
     * @return account group details.
     */
    AccountGroup getById(Long groupId);

    /**
     * find the group list of the account.
     * @param accountId account id.
     * @param groupType group type.
     * @return the account group list.
     */
    List<AccountGroup> getByAccountAndGroupType(Long accountId, GroupType groupType);

    /**
     * find all the accounts in the specified group.
     * @param groupId group id.
     * @return set of accounts.
     */
    Set<Account> getAccountsInGroup(Long groupId);
}
