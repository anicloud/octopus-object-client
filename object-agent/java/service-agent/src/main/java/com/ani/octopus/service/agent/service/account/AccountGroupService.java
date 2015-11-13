package com.ani.octopus.service.agent.service.account;

import com.ani.octopus.service.agent.service.account.dto.GroupType;
import com.ani.octopus.service.agent.service.account.dto.AccountDto;
import com.ani.octopus.service.agent.service.account.dto.AccountGroupDto;
import com.ani.octopus.service.agent.service.account.dto.GroupFormDto;
import com.ani.octopus.service.agent.service.account.dto.Message;

import java.util.Collection;

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
    AccountGroupDto save(GroupFormDto accountGroup);

    /**
     * modify an account group.
     * @param accountGroup the modified information of an account group.
     * @return modified account group.
     */
    AccountGroupDto modify(GroupFormDto accountGroup);

    /**
     * remove an account group.
     * @param accountId account id.
     * @param groupId group id.
     */
    Message remove(Long accountId, Long groupId);

    /**
     * find an account group by group id.
     * @param groupId group id.
     * @return account group details.
     */
    AccountGroupDto getById(Long groupId);

    /**
     * find the group list of the account.
     * @param accountId account id.
     * @param groupType group type.
     * @return the account group list.
     */
    Collection<AccountGroupDto> getByAccountAndGroupType(Long accountId, GroupType groupType);

    /**
     * find all the accounts in the specified group.
     * @param groupId group id.
     * @return Collection of accounts.
     */
    Collection<AccountDto> getAccountsInGroup(Long groupId);
}
