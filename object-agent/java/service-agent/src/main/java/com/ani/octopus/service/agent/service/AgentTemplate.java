package com.ani.octopus.service.agent.service;

import com.ani.octopus.service.agent.core.config.AnicelMeta;
import com.ani.octopus.service.agent.core.http.RestTemplateFactory;
import com.ani.octopus.service.agent.service.account.AccountGroupService;
import com.ani.octopus.service.agent.service.account.AccountGroupServiceImpl;
import com.ani.octopus.service.agent.service.account.AccountService;
import com.ani.octopus.service.agent.service.account.AccountServiceImpl;
import com.ani.octopus.service.agent.service.deviceobj.DeviceObjService;
import com.ani.octopus.service.agent.service.deviceobj.DeviceObjServiceImpl;
import com.ani.octopus.service.agent.service.oauth.AniOAuthService;
import com.ani.octopus.service.agent.service.oauth.AniOAuthServiceImpl;

/**
 * AgentTemplate provides the singleton instances of the agent service method.
 * Created by zhaoyu on 16-04-12.
 */
public class AgentTemplate {

    private AccountService accountService;
    private AccountGroupService accountGroupService;
    private AniOAuthService aniOAuthService;
    private DeviceObjService deviceObjService;

    private AnicelMeta anicelMeta;
    private RestTemplateFactory restTemplateFactory;

    public AgentTemplate(AnicelMeta anicelMeta, RestTemplateFactory restTemplateFactory) {
        this.anicelMeta = anicelMeta;
        this.restTemplateFactory = restTemplateFactory;
    }

    public synchronized AccountService getAccountService(String accessToken) {
        if (accountService == null) {
            accountService = new AccountServiceImpl(
                    anicelMeta,
                    restTemplateFactory,
                    accessToken
            );
        } else {
            accountService.setAccessToken(accessToken);
        }
        return accountService;
    }

    public synchronized AccountGroupService getAccountGroupService(String accessToken) {
        if (accountGroupService == null) {
            accountGroupService = new AccountGroupServiceImpl(
                    anicelMeta,
                    restTemplateFactory,
                    accessToken
            );
        } else {
            accountGroupService.setAccessToken(accessToken);
        }
        return accountGroupService;
    }

    public synchronized AniOAuthService getAniOAuthService() {
        if (aniOAuthService == null) {
            aniOAuthService = new AniOAuthServiceImpl(
                    anicelMeta,
                    restTemplateFactory
            );
        }
        return aniOAuthService;
    }

    public synchronized DeviceObjService getDeviceObjService(String accessToken) {
        if (deviceObjService == null) {
            deviceObjService = new DeviceObjServiceImpl(
                    anicelMeta,
                    restTemplateFactory,
                    accessToken
            );
        } else {
            deviceObjService.setAccessToken(accessToken);
        }
        return deviceObjService;
    }

    public void setAnicelMeta(AnicelMeta anicelMeta) {
        this.anicelMeta = anicelMeta;
    }

    public void setRestTemplateFactory(RestTemplateFactory restTemplateFactory) {
        this.restTemplateFactory = restTemplateFactory;
    }
}
