package com.ani.octopus.service.agent.core.http;

import com.ani.octopus.service.agent.core.AnicelMeta;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zhaoyu on 15-11-11.
 */
public abstract class AbstractBaseService {

    public AnicelMeta anicelMeta;
    public RestTemplateFactory restTemplateFactory;
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
