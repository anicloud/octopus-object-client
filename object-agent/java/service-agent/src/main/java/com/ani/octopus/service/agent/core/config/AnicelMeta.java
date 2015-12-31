package com.ani.octopus.service.agent.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * This class provides URIs for agent to get resources from Anicloud Platform. Including OAuth2 resources,
 * Account related resources and webSocket resources. <br>
 * Created by zhaoyu on 15-11-11.
 */
public class AnicelMeta {
    private final static Logger LOGGER = LoggerFactory.getLogger(AnicelMeta.class);

    private final static String ANICEL_CONFIG_FILE = "anicel_meta.properties";
    private static Properties properties;

    static {
        Resource resource = new ClassPathResource(ANICEL_CONFIG_FILE);
        try {
            properties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // account manager
    private String octopusServiceUrl;

    private String accountRegisterUrl;
    private String accountModifyUrl;
    private String accountAddInGroupUrl;
    private String accountByAccountIdUrl;
    private String accountByEmailUrl;
    private String accountByPhoneUrl;

    private String groupAddUrl;
    private String groupModifyUrl;
    private String groupDeleteUrl;
    private String groupById;
    private String groupByAccountIdAndType;
    private String groupAccountsIn;

    // service bus
    private String aniServiceBusUrl;
    private String serviceBusWebSocketUrl;
    private String serviceBusRegisterUrl;
    private String serviceBusGetByUrl;
    // oauth
    private String accountOAuthAuthorizeUrl;
    private String accountOAuthTokenUrl;

    public AnicelMeta() {
        this.octopusServiceUrl = properties.getProperty("octopus.account.service");
        this.accountRegisterUrl = properties.getProperty("octopus.account.service.account.register");
        this.accountModifyUrl = properties.getProperty("octopus.account.service.account.modify");
        this.accountAddInGroupUrl = properties.getProperty("octopus.account.service.account.addInGroup");
        this.accountByAccountIdUrl = properties.getProperty("octopus.account.service.account.accountId");
        this.accountByEmailUrl = properties.getProperty("octopus.account.service.account.email");
        this.accountByPhoneUrl = properties.getProperty("octopus.account.service.account.phone");


        this.groupAddUrl = properties.getProperty("octopus.account.service.group.add");
        this.groupModifyUrl = properties.getProperty("octopus.account.service.group.modify");
        this.groupDeleteUrl = properties.getProperty("octopus.account.service.group.delete");
        this.groupById = properties.getProperty("octopus.account.service.group.groupId");
        this.groupByAccountIdAndType = properties.getProperty("octopus.account.service.group.accountId.groupType");
        this.groupAccountsIn = properties.getProperty("octopus.account.service.group.accountsIn");


        this.accountOAuthAuthorizeUrl = properties.getProperty("octopus.account.service.oauth.authorize");
        this.accountOAuthTokenUrl = properties.getProperty("octopus.account.service.oauth.token");

        this.serviceBusRegisterUrl = properties.getProperty("ani.bus.service.bus.aniservice.register");
        this.serviceBusGetByUrl = properties.getProperty("ani.bus.service.bus.aniservice.get");
        this.aniServiceBusUrl = properties.getProperty("ani.bus.service.bus");
        this.serviceBusWebSocketUrl = properties.getProperty("ani.bus.service.bus.websocket");
    }

    public String getOctopusServiceUrl() {
        return octopusServiceUrl;
    }

    public String getAccountRegisterUrl() {
        return accountRegisterUrl;
    }

    public String getAccountModifyUrl() {
        return accountModifyUrl;
    }

    public String getAccountAddInGroupUrl() {
        return accountAddInGroupUrl;
    }

    public String getAccountByAccountIdUrl() {
        return accountByAccountIdUrl;
    }

    public String getAccountByEmailUrl() {
        return accountByEmailUrl;
    }

    public String getAccountByPhoneUrl() {
        return accountByPhoneUrl;
    }

    public String getGroupAddUrl() {
        return groupAddUrl;
    }

    public String getGroupModifyUrl() {
        return groupModifyUrl;
    }

    public String getGroupDeleteUrl() {
        return groupDeleteUrl;
    }

    public String getGroupById() {
        return groupById;
    }

    public String getAniServiceBusUrl() {
        return aniServiceBusUrl;
    }

    public String getServiceBusWebSocketUrl() {
        return serviceBusWebSocketUrl;
    }

    public String getAccountOAuthAuthorizeUrl() {
        return accountOAuthAuthorizeUrl;
    }

    public String getAccountOAuthTokenUrl() {
        return accountOAuthTokenUrl;
    }

    public String getGroupByAccountIdAndType() {
        return groupByAccountIdAndType;
    }

    public String getGroupAccountsIn() {
        return groupAccountsIn;
    }

    public String getServiceBusRegisterUrl() {
        return serviceBusRegisterUrl;
    }

    public String getServiceBusGetByUrl() {
        return serviceBusGetByUrl;
    }

    @Override
    public String toString() {
        return "AnicelMeta{" +
                "octopusServiceUrl='" + octopusServiceUrl + '\'' +
                ", accountRegisterUrl='" + accountRegisterUrl + '\'' +
                ", accountModifyUrl='" + accountModifyUrl + '\'' +
                ", accountAddInGroupUrl='" + accountAddInGroupUrl + '\'' +
                ", accountByAccountIdUrl='" + accountByAccountIdUrl + '\'' +
                ", accountByEmailUrl='" + accountByEmailUrl + '\'' +
                ", accountByPhoneUrl='" + accountByPhoneUrl + '\'' +
                ", groupAddUrl='" + groupAddUrl + '\'' +
                ", groupModifyUrl='" + groupModifyUrl + '\'' +
                ", groupDeleteUrl='" + groupDeleteUrl + '\'' +
                ", groupById='" + groupById + '\'' +
                ", groupByAccountIdAndType='" + groupByAccountIdAndType + '\'' +
                ", groupAccountsIn='" + groupAccountsIn + '\'' +
                ", aniServiceBusUrl='" + aniServiceBusUrl + '\'' +
                ", serviceBusWebSocketUrl='" + serviceBusWebSocketUrl + '\'' +
                ", serviceBusRegisterUrl='" + serviceBusRegisterUrl + '\'' +
                ", serviceBusGetByUrl='" + serviceBusGetByUrl + '\'' +
                ", accountOAuthAuthorizeUrl='" + accountOAuthAuthorizeUrl + '\'' +
                ", accountOAuthTokenUrl='" + accountOAuthTokenUrl + '\'' +
                '}';
    }
}
