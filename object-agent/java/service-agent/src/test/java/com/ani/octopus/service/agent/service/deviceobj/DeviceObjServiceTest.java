package com.ani.octopus.service.agent.service.deviceobj;

import com.ani.bus.service.commons.dto.anidevice.DeviceMasterObjInfoDto;
import com.ani.octopus.service.agent.core.config.AnicelMeta;
import com.ani.octopus.service.agent.core.http.RestTemplateFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @autor zhaoyu
 * @date 16-7-4
 * @since JDK 1.7
 */
public class DeviceObjServiceTest {

    private ObjectMapper objectMapper;
    private String accessToken = "8b6428cb-88fd-4baf-b727-e6429627e7dc";
    private Long accountId = 7581089877683183796L;
    private DeviceObjService deviceObjService;

    @Before
    public void before() {
        objectMapper = new ObjectMapper();

        AnicelMeta anicelMeta = new AnicelMeta();
        RestTemplateFactory templateFactory = new RestTemplateFactory();
        deviceObjService = new DeviceObjServiceImpl(
                anicelMeta,
                templateFactory,
                accessToken
        );
    }

    @Test
    public void getDeviceObjInfo() throws Exception {
        List<DeviceMasterObjInfoDto> list = deviceObjService.getDeviceObjInfo(accountId, Boolean.TRUE);
        System.out.println(list);
    }

}