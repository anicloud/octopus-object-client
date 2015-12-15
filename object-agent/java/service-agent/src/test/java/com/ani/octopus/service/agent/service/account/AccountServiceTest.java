package com.ani.octopus.service.agent.service.account;

import com.ani.octopus.account.application.dto.account.AccountDto;
import com.ani.octopus.account.application.dto.account.AccountModifyDto;
import com.ani.octopus.account.application.dto.account.AccountRegisterDto;
import com.ani.octopus.account.application.dto.account.adapter.AccountInfoDtoAdapter;
import com.ani.octopus.account.domain.model.enums.AccountType;
import com.ani.octopus.service.agent.core.config.AnicelMeta;
import com.ani.octopus.service.agent.core.http.RestTemplateFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.xml.bind.ValidationException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import static org.junit.Assert.*;

/**
 * Created by zhaoyu on 15-12-14.
 */
public class AccountServiceTest {
    private ObjectMapper objectMapper;
    private AccountService accountService;
    private String accessToken = "aa0c7b13-f3d1-4f64-bd1b-bd472f4aaa6f";

    @Before
    public void before() {
        objectMapper = new ObjectMapper();

        AnicelMeta anicelMeta = new AnicelMeta();
        RestTemplateFactory templateFactory = new RestTemplateFactory();
        accountService = new AccountServiceImpl(
                anicelMeta,
                templateFactory,
                accessToken
        );
    }

    @Test
    public void testRegisterAccount() throws ValidationException, JsonProcessingException {
        AccountRegisterDto registerDto = createRegisterAccount();
        AccountDto accountDto = accountService.register(registerDto);
        System.out.println(objectMapper.writeValueAsString(accountDto));
    }

    @Test
    public void testModifyAccount() throws ValidationException {
        AccountModifyDto modifyDto = new AccountModifyDto(
                4655394565573078056L,
                "Bill-Huang",
                AccountType.PERSONAL,
                "18511929814",
                "Fengtai, Beijing",
                "Anicloud Limited",
                "https://raw.githubusercontent.com/anicloud/anicloud.github.io/master/images/logo/ani_logo.png"
        );
        AccountDto accountDto = accountService.modify(modifyDto);
        System.out.println(accountDto);
    }

    @Test
    public void testGetByAccountId() throws JsonProcessingException {
        Long accountId = 4655394565573078056L;
        AccountDto accountDto = accountService.getByAccountId(accountId);
        System.out.println(objectMapper.writeValueAsString(accountDto));
    }

    @Test
    public void testGetByEmail() throws JsonProcessingException, UnsupportedEncodingException {
        String email = "bill@anicloud.com";
        AccountDto accountDto = accountService.getByEmail(email);
        System.out.println(objectMapper.writeValueAsString(accountDto));
    }

    @Test
    public void testGetByPhoneNumber() throws JsonProcessingException {
        String phoneNumber = "18511929814";
        AccountDto accountDto = accountService.getByPhoneNumber(phoneNumber);
        System.out.println(objectMapper.writeValueAsString(accountDto));
    }

    @Test
    public void testAddAccountInGroup() {
        Long accountId = 4655394565573078056L;
        Long groupId = 2L;
        AccountDto accountDto = accountService.addAccountInGroup(accountId, groupId);
        System.out.println(accountDto);
    }

    private AccountRegisterDto createRegisterAccount() {
        return new AccountRegisterDto(
                "Bill",
                "bill@anicloud.com",
                "123456",
                AccountType.PERSONAL,
                "18511929814",
                "Fengtai, Beijing",
                "Anicloud Limited",
                "https://raw.githubusercontent.com/anicloud/anicloud.github.io/master/images/logo/ani_logo.png"
        );
    }

    @Test
    public void testEmailEncoder() throws UnsupportedEncodingException {
        String email = "bill@163.com";
        String result = URLEncoder.encode(email, "US-ASCII");
        System.out.println(result);
        System.out.println(URLDecoder.decode(result, "US-ASCII"));
    }
}