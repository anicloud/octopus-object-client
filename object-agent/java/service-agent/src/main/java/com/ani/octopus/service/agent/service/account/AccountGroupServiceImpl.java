package com.ani.octopus.service.agent.service.account;

import com.ani.octopus.service.agent.core.config.AnicelMeta;
import com.ani.octopus.service.agent.core.http.AbstractBaseService;
import com.ani.octopus.service.agent.core.http.RestTemplateFactory;
import com.ani.octopus.service.agent.core.message.HttpMessage;
import com.ani.octopus.service.agent.core.message.Message;
import com.ani.octopus.service.agent.core.validate.DomainObjectValidator;
import com.ani.octopus.service.agent.service.account.dto.GroupType;
import com.ani.octopus.service.agent.service.account.dto.AccountDto;
import com.ani.octopus.service.agent.service.account.dto.AccountGroupDto;
import com.ani.octopus.service.agent.service.account.dto.GroupFormDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * Created by zhaoyu on 15-10-31.
 */
public class AccountGroupServiceImpl extends AbstractBaseService implements AccountGroupService {

    public AccountGroupServiceImpl() {
        super();
    }

    public AccountGroupServiceImpl(AnicelMeta anicelMeta, RestTemplateFactory restTemplateFactory, String accessToken) {
        super(anicelMeta, restTemplateFactory, accessToken);
    }

    @Override
    public AccountGroupDto save(GroupFormDto accountGroup) {
        if (!DomainObjectValidator.isDomainObjectValid(accountGroup)) {
            throw new ValidationException("Invalid GroupFormDto Instance.");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(anicelMeta.getOctopusServiceUrl() + anicelMeta.getGroupAddUrl())
                .queryParam(RestTemplateFactory.ACCESS_TOKEN, accessToken);

        HttpEntity<GroupFormDto> requestEntity = new HttpEntity<>(accountGroup, httpHeaders);

        HttpMessage<AccountGroupDto> result= restTemplateFactory.getRestTemplate(new Class[] {GroupFormDto.class, AccountGroupDto.class}).postForObject(
                uriComponentsBuilder.toUriString(),
                requestEntity,
                HttpMessage.class
        );

        if (result.getResultCode() == Message.ResultCode.SUCCESS) {
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
    public AccountGroupDto modify(GroupFormDto accountGroup) {
        if (!DomainObjectValidator.isDomainObjectValid(accountGroup)) {
            throw new ValidationException("Invalid GroupFormDto Instance.");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(anicelMeta.getOctopusServiceUrl() + anicelMeta.getGroupModifyUrl())
                .queryParam(RestTemplateFactory.ACCESS_TOKEN, accessToken);

        HttpEntity<GroupFormDto> requestEntity = new HttpEntity<>(accountGroup, httpHeaders);
        HttpMessage<AccountGroupDto> result = restTemplateFactory.getRestTemplate(new Class[] {GroupFormDto.class, AccountGroupDto.class}).postForObject(
                uriComponentsBuilder.toUriString(),
                requestEntity,
                HttpMessage.class
        );
        if (result.getResultCode() == Message.ResultCode.SUCCESS) {
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
    public Message remove(Long accountId, Long groupId) {
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
        Message message = restTemplateFactory.getRestTemplate(new Class[] {GroupFormDto.class, AccountGroupDto.class}).getForObject(
                uriComponentsBuilder.build().toUriString(),
                Message.class
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
        HttpMessage<AccountGroupDto> result = restTemplateFactory.getRestTemplate(new Class[] {GroupFormDto.class, AccountGroupDto.class})
                .getForObject(uriComponentsBuilder.build().toUriString(), HttpMessage.class);

        if (result.getResultCode() == Message.ResultCode.SUCCESS) {
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
        HttpMessage<List> result = restTemplateFactory.getRestTemplate(new Class[] {GroupFormDto.class, AccountGroupDto.class})
                .getForObject(uriComponentsBuilder.build().toUriString(), HttpMessage.class);

        if (result.getResultCode() == Message.ResultCode.SUCCESS) {
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
        HttpMessage<List> result = restTemplateFactory.getRestTemplate(new Class[] {GroupFormDto.class, AccountGroupDto.class})
                .getForObject(uriComponentsBuilder.build().toUriString(), HttpMessage.class);

        if (result.getResultCode() == Message.ResultCode.SUCCESS) {
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
