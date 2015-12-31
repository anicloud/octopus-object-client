package com.ani.octopus.service.agent.core.http;

import com.ani.octopus.service.agent.core.config.AnicelMeta;

/**
 * This class provides base information for http based request. <br>
 * <strong>AnicelMeta</strong> basic resources URIs. <br>
 * <strong>RestTemplateFactory</strong> provide the Spring based RestTemplate. <br>
 * <strong>accessToken</strong> Anicloud Platform issues for the Third Party Service
 * to access the current Account's resources. <br>
 * Created by zhaoyu on 15-11-11.
 */
public abstract class AbstractBaseService {

    /**
     * URIs resources meta information
     */
    public AnicelMeta anicelMeta;
    public RestTemplateFactory restTemplateFactory;
    /**
     * OAuth2 based access token
     */
    public String accessToken;

    public AbstractBaseService() {
    }

    public AbstractBaseService(AnicelMeta anicelMeta, RestTemplateFactory restTemplateFactory, String accessToken) {
        this.anicelMeta = anicelMeta;
        this.restTemplateFactory = restTemplateFactory;
        this.accessToken = accessToken;
    }

    public void setAnicelMeta(AnicelMeta anicelMeta) {
        this.anicelMeta = anicelMeta;
    }

    public void setRestTemplateFactory(RestTemplateFactory restTemplateFactory) {
        this.restTemplateFactory = restTemplateFactory;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
