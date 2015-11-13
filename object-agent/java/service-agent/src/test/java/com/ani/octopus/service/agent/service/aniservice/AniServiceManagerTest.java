package com.ani.octopus.service.agent.service.aniservice;

import com.ani.octopus.service.agent.core.AnicelMeta;
import com.ani.octopus.service.agent.core.http.RestTemplateFactory;
import com.ani.octopus.service.agent.service.aniservice.dto.AniServiceDto;
import com.ani.octopus.service.agent.service.aniservice.dto.AniServiceInfoDto;
import com.ani.octopus.service.agent.service.aniservice.dto.AniServiceRegisterDto;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by zhaoyu on 15-11-13.
 */
public class AniServiceManagerTest {

    @Test
    public void testAniServiceRegister() {
        AnicelMeta anicelMeta = new AnicelMeta();
        RestTemplateFactory templateFactory = new RestTemplateFactory();
        AniServiceManager aniServiceManager = new AniServiceManagerImpl(
                anicelMeta,
                templateFactory
        );

        AniServiceRegisterDto registerDto = createRegisterDto();
        AniServiceDto serviceDto = aniServiceManager.register(registerDto);
        System.out.println(serviceDto);
    }

    private AniServiceRegisterDto createRegisterDto() {
        Set<String> tagSet = new HashSet<>();
        tagSet.add("life");
        AniServiceInfoDto serviceInfoDto = new AniServiceInfoDto(
                "http://localhost:8080/sunny",
                "https://raw.githubusercontent.com/anicloud/anicloud.github.io/master/images/logo/ani_logo.png",
                null,
                tagSet,
                0.0,
                new Date(),
                ""
        );

        AniServiceRegisterDto registerDto = new AniServiceRegisterDto(
                "xinwo-app",
                "1.0",
                "http://localhost:8080/xinwo/redirect",
                1707593791689932096L,
                null,
                serviceInfoDto
        );
        return registerDto;
    }
}