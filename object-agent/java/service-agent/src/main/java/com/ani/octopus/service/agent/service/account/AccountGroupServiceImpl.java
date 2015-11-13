package com.ani.octopus.service.agent.service.account;

import com.ani.octopus.service.agent.core.AnicelMeta;
import com.ani.octopus.service.agent.core.http.AbstractBaseService;
import com.ani.octopus.service.agent.core.http.RestTemplateFactory;
import com.ani.octopus.service.agent.service.account.dto.GroupType;
import com.ani.octopus.service.agent.service.account.dto.AccountDto;
import com.ani.octopus.service.agent.service.account.dto.AccountGroupDto;
import com.ani.octopus.service.agent.service.account.dto.GroupFormDto;
import com.ani.octopus.service.agent.service.account.dto.Message;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

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
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(anicelMeta.getOctopusServiceUrl() + anicelMeta.getGroupAddUrl())
                .queryParam(RestTemplateFactory.ACCESS_TOKEN, accessToken);

        HttpEntity<GroupFormDto> requestEntity = new HttpEntity<>(accountGroup, httpHeaders);
        AccountGroupDto accountGroupDto= restTemplateFactory.getRestTemplate(new Class[] {GroupFormDto.class, AccountGroupDto.class}).postForObject(
                uriComponentsBuilder.toUriString(),
                requestEntity,
                AccountGroupDto.class
        );
        return accountGroupDto;
    }

    @Override
    public AccountGroupDto modify(GroupFormDto accountGroup) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(anicelMeta.getOctopusServiceUrl() + anicelMeta.getGroupModifyUrl())
                .queryParam(RestTemplateFactory.ACCESS_TOKEN, accessToken);

        HttpEntity<GroupFormDto> requestEntity = new HttpEntity<>(accountGroup, httpHeaders);
        AccountGroupDto accountGroupDto = restTemplateFactory.getRestTemplate(new Class[] {GroupFormDto.class, AccountGroupDto.class}).postForObject(
                uriComponentsBuilder.toUriString(),
                requestEntity,
                AccountGroupDto.class
        );
        return accountGroupDto;
    }

    @Override
    public Message remove(Long accountId, Long groupId) {
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(anicelMeta.getOctopusServiceUrl())
                .append(anicelMeta.getGroupById())
                .append("/")
                .append(groupId);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stringBuilder.toString())
                .queryParam(RestTemplateFactory.ACCESS_TOKEN, accessToken);
        AccountGroupDto accountGroupDto = restTemplateFactory.getRestTemplate(new Class[] {GroupFormDto.class, AccountGroupDto.class})
                .getForObject(uriComponentsBuilder.build().toUriString(), AccountGroupDto.class);
        return accountGroupDto;
    }

    @Override
    public Collection<AccountGroupDto> getByAccountAndGroupType(Long accountId, GroupType groupType) {
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
        List<AccountGroupDto> accountGroupDtoList = restTemplateFactory.getRestTemplate(new Class[] {GroupFormDto.class, AccountGroupDto.class})
                .getForObject(uriComponentsBuilder.build().toUriString(), List.class);
        return accountGroupDtoList;
    }

    @Override
    public Collection<AccountDto> getAccountsInGroup(Long groupId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(anicelMeta.getOctopusServiceUrl())
                .append(anicelMeta.getGroupAccountsIn())
                .append("/")
                .append(groupId);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stringBuilder.toString())
                .queryParam(RestTemplateFactory.ACCESS_TOKEN, accessToken);
        List<AccountDto> accountDtoList = restTemplateFactory.getRestTemplate(new Class[] {GroupFormDto.class, AccountGroupDto.class})
                .getForObject(uriComponentsBuilder.build().toUriString(), List.class);
        return accountDtoList;
    }
}
