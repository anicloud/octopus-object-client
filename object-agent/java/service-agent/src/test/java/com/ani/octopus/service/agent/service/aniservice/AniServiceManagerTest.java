package com.ani.octopus.service.agent.service.aniservice;

import com.ani.octopus.service.agent.core.config.AnicelMeta;
import com.ani.octopus.service.agent.core.http.RestTemplateFactory;
import com.ani.service.bus.core.application.dto.AniServiceDto;
import com.ani.service.bus.core.application.dto.AniServiceEntranceDto;
import com.ani.service.bus.core.application.dto.AniServiceInfoDto;
import com.ani.service.bus.core.application.dto.AniServiceRegisterDto;
import com.ani.service.bus.core.domain.enums.LanguageEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * Created by zhaoyu on 15-11-13.
 */
public class AniServiceManagerTest {

    private ObjectMapper objectMapper;
    private AniServiceManager aniServiceManager;

    @Before
    public void before() {
        objectMapper = new ObjectMapper();

        AnicelMeta anicelMeta = new AnicelMeta();
        RestTemplateFactory templateFactory = new RestTemplateFactory();
        aniServiceManager = new AniServiceManagerImpl(
                anicelMeta,
                templateFactory
        );
    }

    @Test
    public void testAniServiceRegister() {
        AniServiceRegisterDto registerDto = createRegisterDto();
        System.out.println(registerDto);
        AniServiceDto serviceDto = null;
        try {
            serviceDto = aniServiceManager.register(registerDto);
            System.out.println(objectMapper.writeValueAsString(serviceDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAniService() throws Exception {
        String aniServiceId = "1058595963104900977";
        String clientSecret = "34d54214721d6077ae021ab5d8215258";
        AniServiceDto aniServiceDto = aniServiceManager.getByAniService(aniServiceId, clientSecret);
        System.out.println(objectMapper.writeValueAsString(aniServiceDto));
    }

    @Test
    public void testModifyAniService() throws Exception {
        String aniServiceId = "1058595963104900977";
        String clientSecret = "34d54214721d6077ae021ab5d8215258";
        AniServiceDto aniServiceDto = aniServiceManager.getByAniService(aniServiceId, clientSecret);

        System.out.println(objectMapper.writeValueAsString(aniServiceDto));
        AniServiceRegisterDto registerDto = createRegisterDto();
        registerDto.aniServiceId = aniServiceDto.aniServiceId;
        registerDto.aniServiceInfo = aniServiceDto.aniServiceInfo;
        registerDto.aniServiceInfo.addTag("good app");
        registerDto.entranceList = aniServiceDto.entranceList;

        aniServiceDto = aniServiceManager.register(registerDto);
        System.out.println(objectMapper.writeValueAsString(aniServiceDto));

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
                null,
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