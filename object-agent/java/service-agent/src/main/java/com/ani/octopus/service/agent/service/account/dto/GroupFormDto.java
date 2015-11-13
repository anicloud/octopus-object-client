package com.ani.octopus.service.agent.service.account.dto;

import java.io.Serializable;

/**
 * Created by zhaoyu on 15-11-11.
 */
public class GroupFormDto implements Serializable {
    private static final long serialVersionUID = -2997915395832489663L;

    public String groupName;
    public GroupType groupType;
    public Long accountId;

    public GroupFormDto() {
    }

    public GroupFormDto(String groupName, GroupType groupType, Long accountId) {
        this.groupName = groupName;
        this.groupType = groupType;
        this.accountId = accountId;
    }

    public static AccountGroupDto toAccountGroupDto(GroupFormDto formDto) {
        if (formDto == null) {
            return null;
        }
        return new AccountGroupDto(
                null,
                formDto.groupName,
                formDto.groupType,
                new AccountDto(formDto.accountId),
                null
        );
    }

}
