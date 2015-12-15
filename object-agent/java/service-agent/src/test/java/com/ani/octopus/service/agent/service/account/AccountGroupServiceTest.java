package com.ani.octopus.service.agent.service.account;

import com.ani.octopus.account.application.agent.callmessage.Message;
import com.ani.octopus.account.application.dto.account.AccountDto;
import com.ani.octopus.account.application.dto.account.AccountGroupDto;
import com.ani.octopus.account.application.dto.account.GroupFormDto;
import com.ani.octopus.account.domain.model.enums.GroupType;
import com.ani.octopus.account.infrastructure.persistence.domain.account.AccountDao;
import com.ani.octopus.service.agent.core.config.AnicelMeta;
import com.ani.octopus.service.agent.core.http.RestTemplateFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zhaoyu on 15-12-15.
 */
public class AccountGroupServiceTest {
    private ObjectMapper objectMapper;
    private AccountGroupService accountGroupService;
    private String accessToken = "aa0c7b13-f3d1-4f64-bd1b-bd472f4aaa6f";

    @Before
    public void before() {
        objectMapper = new ObjectMapper();

        AnicelMeta anicelMeta = new AnicelMeta();
        RestTemplateFactory templateFactory = new RestTemplateFactory();
        accountGroupService = new AccountGroupServiceImpl(
                anicelMeta,
                templateFactory,
                accessToken
        );
    }
    @Test
    public void testSave() throws Exception {
        Long accountId = 4655394565573078056L;
        GroupFormDto groupFormDto = new GroupFormDto(
                "ROLE_FRIEND",
                GroupType.CUSTOM,
                accountId
        );

        AccountGroupDto accountGroupDto = accountGroupService.save(groupFormDto);
        System.out.println(accountGroupDto);
    }

    @Test
    public void testModify() throws Exception {
        Long accountId = 4655394565573078056L;
        GroupFormDto groupFormDto = new GroupFormDto(
                3L,
                "ROLE_FRIEND",
                GroupType.CUSTOM,
                accountId
        );
        AccountGroupDto accountGroupDto = accountGroupService.modify(groupFormDto);
        System.out.println(accountGroupDto);
    }

    @Test
    public void testRemove() throws Exception {
        Long accountId = 4655394565573078056L;
        //Long accountId = 5171261575755046940L;
        Long groupId = 3L;

        Message message = accountGroupService.remove(accountId, groupId);
        System.out.println(message);
    }

    @Test
    public void testGetById() throws Exception {
        Long groupId = 4L;
        AccountGroupDto accountGroupDto = accountGroupService.getById(groupId);
        System.out.println(accountGroupDto);
        Assert.assertNotNull(accountGroupDto);
    }

    @Test
    public void testGetByAccountAndGroupType() throws Exception {
        //Long accountId = 5171261575755046940L;
        Long accountId = 5391071553898021350L;
        Collection<AccountGroupDto> groupDtoList = accountGroupService
                .getByAccountAndGroupType(accountId, GroupType.SYSTEM);
        Assert.assertEquals(1, groupDtoList.size());
    }

    @Test
    public void testGetAccountsInGroup() throws Exception {
        Collection<AccountDto> accountDtoCollection = accountGroupService
                .getAccountsInGroup(2L);
        System.out.println(accountDtoCollection.size());
    }
}