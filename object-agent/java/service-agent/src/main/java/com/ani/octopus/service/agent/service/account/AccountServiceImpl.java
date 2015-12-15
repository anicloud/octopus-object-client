package com.ani.octopus.service.agent.service.account;

import com.ani.octopus.account.application.agent.callmessage.AccountHttpMessage;
import com.ani.octopus.account.application.agent.callmessage.Message;
import com.ani.octopus.account.application.dto.account.AccountDto;
import com.ani.octopus.account.application.dto.account.AccountModifyDto;
import com.ani.octopus.account.application.dto.account.AccountRegisterDto;
import com.ani.octopus.account.domain.model.enums.AccountType;
import com.ani.octopus.service.agent.core.config.AnicelMeta;
import com.ani.octopus.service.agent.core.http.AbstractBaseService;
import com.ani.octopus.service.agent.core.validate.DomainObjectValidator;
import com.ani.octopus.service.agent.core.http.RestTemplateFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.bind.ValidationException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
        if (account.accountType == AccountType.ROOT) {
            throw new ValidationException("Account Type cannot be Root");
        }
        if (!DomainObjectValidator.isDomainObjectValid(account)) {
            throw new ValidationException("Invalid AccountRegisterDto Instance");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(anicelMeta.getOctopusServiceUrl() + anicelMeta.getAccountRegisterUrl());

        HttpEntity<AccountRegisterDto> requestEntity = new HttpEntity<>(account, httpHeaders);
        AccountHttpMessage result = restTemplateFactory.getRestTemplate(new Class[]{AccountRegisterDto.class, AccountDto.class}).postForObject(
                uriComponentsBuilder.toUriString(),
                requestEntity,
                AccountHttpMessage.class
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
        if (account.accountType == AccountType.ROOT) {
            throw new ValidationException("Account Type cannot be Root");
        }
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
        AccountHttpMessage result = restTemplateFactory
                .getRestTemplate(new Class[]{AccountRegisterDto.class, AccountDto.class})
                .postForObject(uriComponentsBuilder.toUriString(), requestEntity, AccountHttpMessage.class);

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
        AccountHttpMessage result = restTemplateFactory
                .getRestTemplate(new Class[]{AccountRegisterDto.class, AccountDto.class})
                .getForObject(uriComponentsBuilder.build().toUriString(), AccountHttpMessage.class);

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
                .append(anicelMeta.getAccountByEmailUrl());

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stringBuilder.toString())
                .queryParam("email", email)
                .queryParam("access_token", accessToken);
        AccountHttpMessage result = restTemplateFactory
                .getRestTemplate(new Class[]{AccountRegisterDto.class, AccountDto.class})
                .getForObject(uriComponentsBuilder.build().toUriString(), AccountHttpMessage.class);

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
                .append(anicelMeta.getAccountByPhoneUrl())
                .append("/")
                .append(phoneNumber);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stringBuilder.toString())
                .queryParam("access_token", accessToken);
        AccountHttpMessage result  = restTemplateFactory.getRestTemplate(new Class[]{AccountRegisterDto.class, AccountDto.class})
                .getForObject(uriComponentsBuilder.build().toUriString(), AccountHttpMessage.class);

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
                .append(anicelMeta.getAccountAddInGroupUrl())
                .append("/")
                .append(accountId)
                .append("/")
                .append(groupId);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stringBuilder.toString())
                .queryParam("access_token", accessToken);
        AccountHttpMessage result = restTemplateFactory.getRestTemplate(new Class[]{AccountRegisterDto.class, AccountDto.class})
                .getForObject(uriComponentsBuilder.build().toUriString(), AccountHttpMessage.class);

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
