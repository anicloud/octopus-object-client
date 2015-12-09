package com.ani.octopus.service.agent.service.account;

import com.ani.octopus.service.agent.core.config.AnicelMeta;
import com.ani.octopus.service.agent.core.http.AbstractBaseService;
import com.ani.octopus.service.agent.core.message.Message;
import com.ani.octopus.service.agent.core.validate.DomainObjectValidator;
import com.ani.octopus.service.agent.service.account.dto.AccountDto;
import com.ani.octopus.service.agent.service.account.dto.AccountModifyDto;
import com.ani.octopus.service.agent.service.account.dto.AccountRegisterDto;
import com.ani.octopus.service.agent.core.http.RestTemplateFactory;
import com.ani.octopus.service.agent.core.message.HttpMessage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.bind.ValidationException;
import java.util.Collections;

/**
 * Created by zhaoyu on 15-10-31.
 */
public class AccountServiceImpl extends AbstractBaseService implements AccountService {

    public AccountServiceImpl(AnicelMeta anicelMeta, RestTemplateFactory restTemplateFactory, String accessToken) {
        super(anicelMeta, restTemplateFactory, accessToken);
    }

    @Override
    public AccountDto register(AccountRegisterDto account) throws ValidationException {
        if (!DomainObjectValidator.isDomainObjectValid(account)) {
            throw new ValidationException("Invalid AccountRegisterDto Instance.");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(anicelMeta.getOctopusServiceUrl() + anicelMeta.getAccountRegisterUrl());

        HttpEntity<AccountRegisterDto> requestEntity = new HttpEntity<>(account, httpHeaders);
        HttpMessage<AccountDto> result = restTemplateFactory.getRestTemplate(new Class[]{AccountRegisterDto.class, AccountDto.class}).postForObject(
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
    public AccountDto modify(AccountModifyDto account) throws ValidationException {
        if (!DomainObjectValidator.isDomainObjectValid(account)) {
            throw new ValidationException("Invalid AccountModifyDto Instance.");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(anicelMeta.getOctopusServiceUrl() + anicelMeta.getAccountModifyUrl())
                .queryParam(RestTemplateFactory.ACCESS_TOKEN, accessToken);

        HttpEntity<AccountModifyDto> requestEntity = new HttpEntity<>(account, httpHeaders);
        HttpMessage<AccountDto> result = restTemplateFactory
                .getRestTemplate(new Class[]{AccountRegisterDto.class, AccountDto.class})
                .postForObject(uriComponentsBuilder.toUriString(), requestEntity, HttpMessage.class);

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
    public AccountDto getByAccountId(Long accountId) {
        if (accountId == null) {
            throw new NullPointerException("AccountId is Null.");
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(anicelMeta.getOctopusServiceUrl())
                .append(anicelMeta.getAccountByAccountIdUrl())
                .append("/")
                .append(accountId);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stringBuilder.toString())
                .queryParam("access_token", accessToken);
        HttpMessage<AccountDto> result = restTemplateFactory
                .getRestTemplate(new Class[]{AccountRegisterDto.class, AccountDto.class})
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
    public AccountDto getByEmail(String email) {
        if (email == null) {
            throw new NullPointerException("Email is Null.");
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(anicelMeta.getOctopusServiceUrl())
                .append(anicelMeta.getAccountByAccountIdUrl())
                .append("/")
                .append(email);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stringBuilder.toString())
                .queryParam("access_token", accessToken);
        HttpMessage<AccountDto> result = restTemplateFactory
                .getRestTemplate(new Class[]{AccountRegisterDto.class, AccountDto.class})
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
    public AccountDto getByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            throw new NullPointerException("PhoneNumber is Null.");
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(anicelMeta.getOctopusServiceUrl())
                .append(anicelMeta.getAccountByAccountIdUrl())
                .append("/")
                .append(phoneNumber);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stringBuilder.toString())
                .queryParam("access_token", accessToken);
        HttpMessage<AccountDto> result  = restTemplateFactory.getRestTemplate(new Class[]{AccountRegisterDto.class, AccountDto.class})
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
    public AccountDto addAccountInGroup(Long accountId, Long groupId) {
        if (accountId == null || groupId == null) {
            throw new NullPointerException("AccountId or GroupId is Null.");
        }

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
        HttpMessage<AccountDto> result = restTemplateFactory.getRestTemplate(new Class[]{AccountRegisterDto.class, AccountDto.class})
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
