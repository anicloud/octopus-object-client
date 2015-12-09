package com.ani.octopus.service.agent.service.aniservice;

import com.ani.octopus.service.agent.core.config.AnicelMeta;
import com.ani.octopus.service.agent.core.http.RestTemplateFactory;
import com.ani.octopus.service.agent.service.aniservice.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

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
        System.out.println(registerDto);
        AniServiceDto serviceDto = aniServiceManager.register(registerDto);
        System.out.println(serviceDto);
    }

    private AniServiceRegisterDto createRegisterDto() {
        Set<String> tagSet = new HashSet<>();
        tagSet.add("life");


        AniServiceInfoDto serviceInfoDto = new AniServiceInfoDto(
                "http://localhost:8080/xinwo",
                "https://raw.githubusercontent.com/anicloud/anicloud.github.io/master/images/logo/ani_logo.png",
                null,
                tagSet,
                0.0,
                new Date(),
                "xinmo app"
        );
        serviceInfoDto.addTag("life");
        serviceInfoDto.addLanguage(LanguageEnum.ZH_CN);

        AniServiceRegisterDto registerDto = new AniServiceRegisterDto(
                "xinwo-app",
                "1.0",
                "http://localhost:8080/xinwo/redirect",
                1707593791689932096L,
                null,
                serviceInfoDto,
                null
        );

        AniServiceEntranceDto serviceEntranceDto = new AniServiceEntranceDto(
                "xinwo entrance",
                "http://localhost:8080/xinwo",
                "https://raw.githubusercontent.com/anicloud/anicloud.github.io/master/images/logo/ani_logo.png",
                null,
                "description"
        );
        serviceEntranceDto.addTag("life");

        registerDto.addStub(1L, 1);
        registerDto.addAniServiceEntrance(serviceEntranceDto);
        return registerDto;
    }

    @Ignore
    public void testMap() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        /*Map<Long, List<Integer>> map = new HashMap<>();
        List<Integer> idList = new ArrayList<>();
        idList.add(1);
        map.put(1L, idList);

        String json = objectMapper.writeValueAsString(map);
        System.out.println(json);

        Map<Long, List<Integer>> returnMap = objectMapper.readValue(json, Map.class);
        System.out.println(returnMap);*/


        String json = objectMapper.enableDefaultTyping().writeValueAsString(createRegisterDto());
        System.out.println(json);
    }
}