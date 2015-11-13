package com.ani.octopus.service.agent.service.account;

import com.ani.octopus.service.agent.core.AnicelMeta;
import com.ani.octopus.service.agent.core.http.AbstractBaseService;
import com.ani.octopus.service.agent.service.account.dto.AccountDto;
import com.ani.octopus.service.agent.service.account.dto.AccountModifyDto;
import com.ani.octopus.service.agent.service.account.dto.AccountRegisterDto;
import com.ani.octopus.service.agent.core.http.RestTemplateFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

/**
 * Created by zhaoyu on 15-10-31.
 */
public class AccountServiceImpl extends AbstractBaseService implements AccountService {

    public AccountServiceImpl(AnicelMeta anicelMeta, RestTemplateFactory restTemplateFactory, String accessToken) {
        super(anicelMeta, restTemplateFactory, accessToken);
    }

    @Override
    public AccountDto register(AccountRegisterDto account) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(anicelMeta.getOctopusServiceUrl() + anicelMeta.getAccountRegisterUrl());

        HttpEntity<AccountRegisterDto> requestEntity = new HttpEntity<>(account, httpHeaders);
        AccountDto accountDto = restTemplateFactory.getRestTemplate(new Class[]{AccountRegisterDto.class, AccountDto.class}).postForObject(
                uriComponentsBuilder.toUriString(),
                requestEntity,
                AccountDto.class
        );
        return accountDto;
    }

    @Override
    public AccountDto modify(AccountModifyDto account) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(anicelMeta.getOctopusServiceUrl() + anicelMeta.getAccountModifyUrl())
                .queryParam(RestTemplateFactory.ACCESS_TOKEN, accessToken);

        HttpEntity<AccountModifyDto> requestEntity = new HttpEntity<>(account, httpHeaders);
        AccountDto accountDto = restTemplateFactory
                .getRestTemplate(new Class[]{AccountRegisterDto.class, AccountDto.class})
                .postForObject(uriComponentsBuilder.toUriString(), requestEntity, AccountDto.class);
        return accountDto;
    }

    @Override
    public AccountDto getByAccountId(Long accountId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(anicelMeta.getOctopusServiceUrl())
                .append(anicelMeta.getAccountByAccountIdUrl())
                .append("/")
                .append(accountId);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stringBuilder.toString())
                .queryParam("access_token", accessToken);
        AccountDto accountDto = restTemplateFactory
                .getRestTemplate(new Class[]{AccountRegisterDto.class, AccountDto.class})
                .getForObject(uriComponentsBuilder.build().toUriString(), AccountDto.class);
        return accountDto;
    }

    @Override
    public AccountDto getByEmail(String email) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(anicelMeta.getOctopusServiceUrl())
                .append(anicelMeta.getAccountByAccountIdUrl())
                .append("/")
                .append(email);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stringBuilder.toString())
                .queryParam("access_token", accessToken);
        AccountDto accountDto = restTemplateFactory
                .getRestTemplate(new Class[]{AccountRegisterDto.class, AccountDto.class})
                .getForObject(uriComponentsBuilder.build().toUriString(), AccountDto.class);
        return accountDto;
    }

    @Override
    public AccountDto getByPhoneNumber(String phoneNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(anicelMeta.getOctopusServiceUrl())
                .append(anicelMeta.getAccountByAccountIdUrl())
                .append("/")
                .append(phoneNumber);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stringBuilder.toString())
                .queryParam("access_token", accessToken);
        AccountDto accountDto = restTemplateFactory.getRestTemplate(new Class[]{AccountRegisterDto.class, AccountDto.class})
                .getForObject(uriComponentsBuilder.build().toUriString(), AccountDto.class);
        return accountDto;
    }

    @Override
    public AccountDto addAccountInGroup(Long accountId, Long groupId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(anicelMeta.getOctopusServiceUrl())
                .append(anicelMeta.getAccountByAccountIdUrl())
                .append("/")
                .append(accountId)
                .append("/")
                .append(groupId);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stringBuilder.toString())
                .queryParam("access_token", accessToken);
        AccountDto accountDto = restTemplateFactory.getRestTemplate(new Class[]{AccountRegisterDto.class, AccountDto.class})
                .getForObject(uriComponentsBuilder.build().toUriString(), AccountDto.class);
        return accountDto;
    }
}
