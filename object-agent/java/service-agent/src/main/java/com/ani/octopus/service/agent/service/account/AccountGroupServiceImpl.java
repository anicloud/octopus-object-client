package com.ani.octopus.service.agent.service.account;

import com.ani.bus.service.commons.core.message.HttpMessage;
import com.ani.octopus.commons.accout.dto.AccountDto;
import com.ani.octopus.commons.accout.dto.AccountGroupDto;
import com.ani.octopus.commons.accout.dto.GroupFormDto;
import com.ani.octopus.commons.accout.dto.GroupType;
import com.ani.octopus.commons.accout.message.AccountGroupHttpMessage;
import com.ani.octopus.commons.accout.message.AccountGroupsHttpMessage;
import com.ani.octopus.commons.accout.message.AccountsHttpMessage;
import com.ani.octopus.commons.core.message.OctopusHttpMessage;
import com.ani.octopus.commons.core.message.OctopusMessage;
import com.ani.octopus.service.agent.core.config.AnicelMeta;
import com.ani.octopus.service.agent.core.http.AbstractBaseService;
import com.ani.octopus.service.agent.core.http.RestTemplateFactory;
import com.ani.octopus.service.agent.core.validate.DomainObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.Collections;

/**
 * The implementation of the AccountGroupService and extends from AbstractBaseService. <br><br>
 * <strong>Use Example:</strong><br>
 * <pre>
 *     AnicelMeta anicelMeta = new AnicelMeta();
 *     // create the RestTemplateFactory
 *     RestTemplateFactory templateFactory = new RestTemplateFactory();
 *     // create AccountGroupService instance
 *     AccountGroupService accountGroupService = new AccountGroupServiceImpl(
 *          anicelMeta,
 *          templateFactory,
 *          accessToken
 *     );
 *     // call the methods
 *     GroupFormDto formDto = new GroupFormDto();
 *     accountGroupService.save(formDto);
 *     ......
 * </pre>
 * Created by zhaoyu on 15-10-31.
 */
public class AccountGroupServiceImpl extends AbstractBaseService implements AccountGroupService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountGroupServiceImpl.class);

    public AccountGroupServiceImpl() {
        super();
    }

    public AccountGroupServiceImpl(AnicelMeta anicelMeta, RestTemplateFactory restTemplateFactory, String accessToken) {
        super(anicelMeta, restTemplateFactory, accessToken);
    }

    @Override
    public AccountGroupDto save(GroupFormDto groupFormDto) {
        if (groupFormDto.groupType == GroupType.SYSTEM) {
            throw new ValidationException("Group Type cannot be System");
        }
        if (!DomainObjectValidator.isDomainObjectValid(groupFormDto)) {
            throw new ValidationException("Invalid GroupFormDto Instance.");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(anicelMeta.getOctopusServiceUrl() + anicelMeta.getGroupAddUrl())
                .queryParam(RestTemplateFactory.ACCESS_TOKEN, accessToken);

        HttpEntity<GroupFormDto> requestEntity = new HttpEntity<>(groupFormDto, httpHeaders);

        AccountGroupHttpMessage result= restTemplateFactory.getRestTemplate(new Class[] {GroupFormDto.class, AccountGroupDto.class}).postForObject(
                uriComponentsBuilder.toUriString(),
                requestEntity,
                AccountGroupHttpMessage.class
        );

        if (result.getResultCode() == OctopusMessage.ResultCode.SUCCESS) {
            return result.getReturnObj();
        } else {
            StringBuilder builder = new StringBuilder("message: ")
                    .append(result.getMsg())
                    .append(", error code:")
                    .append(result.getResultCode());
            throw new RuntimeException(builder.toString());
        }
    }

    @Override
    public AccountGroupDto modify(GroupFormDto groupFormDto) {
        if (groupFormDto.groupType == GroupType.SYSTEM) {
            throw new ValidationException("Group Type cannot be System");
        }

        if (!DomainObjectValidator.isDomainObjectValid(groupFormDto)) {
            throw new ValidationException("Invalid GroupFormDto Instance.");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(anicelMeta.getOctopusServiceUrl() + anicelMeta.getGroupModifyUrl())
                .queryParam(RestTemplateFactory.ACCESS_TOKEN, accessToken);

        HttpEntity<GroupFormDto> requestEntity = new HttpEntity<>(groupFormDto, httpHeaders);
        AccountGroupHttpMessage result = restTemplateFactory.getRestTemplate(new Class[] {GroupFormDto.class, AccountGroupDto.class}).postForObject(
                uriComponentsBuilder.toUriString(),
                requestEntity,
                AccountGroupHttpMessage.class
        );
        if (result.getResultCode() == OctopusMessage.ResultCode.SUCCESS) {
            return result.getReturnObj();
        } else {
            StringBuilder builder = new StringBuilder("message: ")
                    .append(result.getMsg())
                    .append(", error code:")
                    .append(result.getResultCode());
            throw new RuntimeException(builder.toString());
        }
    }

    @Override
    public OctopusMessage remove(Long accountId, Long groupId) {
        if (accountId == null || groupId == null) {
            throw new NullPointerException("AccountId or GroupId is Null.");
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(anicelMeta.getOctopusServiceUrl())
                .append(anicelMeta.getGroupDeleteUrl())
                .append("/")
                .append(accountId)
                .append("/")
                .append(groupId);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stringBuilder.toString())
                .queryParam(RestTemplateFactory.ACCESS_TOKEN, accessToken);
        OctopusHttpMessage message = restTemplateFactory.getRestTemplate(new Class[] {GroupFormDto.class, AccountGroupDto.class}).getForObject(
                uriComponentsBuilder.build().toUriString(),
                OctopusHttpMessage.class
        );
        return message;
    }

    @Override
    public AccountGroupDto getById(Long groupId) {
        if (groupId == null) {
            throw new NullPointerException("GroupId is Null.");
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(anicelMeta.getOctopusServiceUrl())
                .append(anicelMeta.getGroupById())
                .append("/")
                .append(groupId);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stringBuilder.toString())
                .queryParam(RestTemplateFactory.ACCESS_TOKEN, accessToken);
        LOGGER.info(uriComponentsBuilder.toUriString());
        AccountGroupHttpMessage result = restTemplateFactory.getRestTemplate(new Class[] {GroupFormDto.class, AccountGroupDto.class})
                .getForObject(uriComponentsBuilder.build().toUriString(), AccountGroupHttpMessage.class);

        if (result.getResultCode() == OctopusMessage.ResultCode.SUCCESS) {
            return result.getReturnObj();
        } else {
            StringBuilder builder = new StringBuilder("message: ")
                    .append(result.getMsg())
                    .append(", error code:")
                    .append(result.getResultCode());
            throw new RuntimeException(builder.toString());
        }
    }

    @Override
    public Collection<AccountGroupDto> getByAccountAndGroupType(Long accountId, GroupType groupType) {
        if (accountId == null || groupType == null) {
            throw new NullPointerException("GroupId is Null.");
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(anicelMeta.getOctopusServiceUrl())
                .append(anicelMeta.getGroupByAccountIdAndType())
                .append("/")
                .append(accountId)
                .append("/type")
                .append("/")
                .append(groupType);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stringBuilder.toString())
                .queryParam(RestTemplateFactory.ACCESS_TOKEN, accessToken);
        AccountGroupsHttpMessage result = restTemplateFactory.getRestTemplate(new Class[] {GroupFormDto.class, AccountGroupDto.class})
                .getForObject(uriComponentsBuilder.build().toUriString(), AccountGroupsHttpMessage.class);

        if (result.getResultCode() == OctopusMessage.ResultCode.SUCCESS) {
            return result.getReturnObj();
        } else {
            StringBuilder builder = new StringBuilder("message: ")
                    .append(result.getMsg())
                    .append(", error code:")
                    .append(result.getResultCode());
            throw new RuntimeException(builder.toString());
        }
    }

    @Override
    public Collection<AccountDto> getAccountsInGroup(Long groupId) {
        if (groupId == null || groupId == null) {
            throw new NullPointerException("GroupId is Null.");
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(anicelMeta.getOctopusServiceUrl())
                .append(anicelMeta.getGroupAccountsIn())
                .append("/")
                .append(groupId);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stringBuilder.toString())
                .queryParam(RestTemplateFactory.ACCESS_TOKEN, accessToken);
        AccountsHttpMessage result = restTemplateFactory.getRestTemplate(new Class[] {GroupFormDto.class, AccountGroupDto.class})
                .getForObject(uriComponentsBuilder.build().toUriString(), AccountsHttpMessage.class);

        if (result.getResultCode() == OctopusMessage.ResultCode.SUCCESS) {
            return result.getReturnObj();
        } else {
            StringBuilder builder = new StringBuilder("message: ")
                    .append(result.getMsg())
                    .append(", error code:")
                    .append(result.getResultCode());
            throw new RuntimeException(builder.toString());
        }
    }
}
