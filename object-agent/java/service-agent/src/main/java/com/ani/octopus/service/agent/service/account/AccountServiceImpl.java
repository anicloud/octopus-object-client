package com.ani.octopus.service.agent.service.account;

import com.ani.octopus.commons.accout.dto.AccountDto;
import com.ani.octopus.commons.accout.dto.AccountModifyDto;
import com.ani.octopus.commons.accout.dto.AccountRegisterDto;
import com.ani.octopus.commons.accout.dto.AccountType;
import com.ani.octopus.commons.accout.message.AccountHttpMessage;
import com.ani.octopus.commons.core.message.OctopusMessage;
import com.ani.octopus.service.agent.core.config.AnicelMeta;
import com.ani.octopus.service.agent.core.http.AbstractBaseService;
import com.ani.octopus.service.agent.core.validate.DomainObjectValidator;
import com.ani.octopus.service.agent.core.http.RestTemplateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.bind.ValidationException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;

/**
 * The implementation of the AccountService and extends from AbstractBaseService. <br><br>
 * <strong>Use Example:</strong><br>
 * <pre>
 *     AnicelMeta anicelMeta = new AnicelMeta();
 *     // create the RestTemplateFactory
 *     RestTemplateFactory templateFactory = new RestTemplateFactory();
 *     // create AccountServiceImpl instance
 *     AccountService accountService = new AccountServiceImpl(
 *          anicelMeta,
 *          templateFactory,
 *          accessToken
 *     );
 *     // call the methods
 *     AccountRegisterDto accountDto = new AccountRegisterDto();
 *     accountGroupService.save(accountDto);
 *     ......
 * </pre>
 * <Strong>Notice:</Strong><br>
 *  When you want to register an account, you don't need the accessToken value, you just can set it <b>NULL</b>.
 * <br><br>
 * Created by zhaoyu on 15-10-31.
 */
public class AccountServiceImpl extends AbstractBaseService implements AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl() {
    }

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

        LOGGER.info(uriComponentsBuilder.toUriString());
        HttpEntity<AccountRegisterDto> requestEntity = new HttpEntity<>(account, httpHeaders);
        AccountHttpMessage result = restTemplateFactory.getRestTemplate(new Class[]{AccountRegisterDto.class, AccountDto.class}).postForObject(
                uriComponentsBuilder.toUriString(),
                requestEntity,
                AccountHttpMessage.class
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
