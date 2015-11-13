package com.ani.octopus.service.agent.service.account.dto;

import java.util.Set;

/**
 * Created by zhaoyu on 15-10-16.
 */
public class AccountGroupDto extends AbstractDto {
    private static final long serialVersionUID = -3084031060158925590L;

    public Long groupId;
    public String groupName;
    public GroupType groupType;

    public AccountDto owner;
    public Set<AccountDto> accounts;

    public AccountGroupDto() {
    }

    public AccountGroupDto(Long groupId, String groupName, GroupType groupType,
                           AccountDto owner, Set<AccountDto> accounts) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupType = groupType;
        this.owner = owner;
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "AccountGroupDto{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", groupType=" + groupType +
                ", owner=" + owner +
                ", accounts=" + accounts +
                "} " + super.toString();
    }
}
