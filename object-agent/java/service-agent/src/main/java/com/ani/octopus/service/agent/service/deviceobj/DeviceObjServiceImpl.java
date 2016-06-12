package com.ani.octopus.service.agent.service.deviceobj;

import com.ani.bus.service.commons.core.message.Message;
import com.ani.bus.service.commons.dto.anidevice.DeviceMasterObjInfoDto;
import com.ani.bus.service.commons.message.DeviceObjInfoHttpMessage;
import com.ani.octopus.service.agent.core.config.AnicelMeta;
import com.ani.octopus.service.agent.core.http.AbstractBaseService;
import com.ani.octopus.service.agent.core.http.RestTemplateFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Created by zhaoyu on 16-03-22.
 */
public class DeviceObjServiceImpl extends AbstractBaseService implements DeviceObjService {

    public DeviceObjServiceImpl() {
    }

    public DeviceObjServiceImpl(AnicelMeta anicelMeta, RestTemplateFactory restTemplateFactory, String accessToken) {
        super(anicelMeta, restTemplateFactory, accessToken);
    }

    @Override
    public List<DeviceMasterObjInfoDto> getDeviceObjInfo(Long accountId, boolean withSlave) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(anicelMeta.getAniServiceBusUrl())
                .append(anicelMeta.getServiceBusGetByUrl())
                .append("/")
                .append(accountId)
                .append("/")
                .append(withSlave);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stringBuilder.toString());

        DeviceObjInfoHttpMessage result = restTemplateFactory.getRestTemplate(new Class[]{DeviceMasterObjInfoDto.class})
                .getForObject(uriComponentsBuilder.toUriString(), DeviceObjInfoHttpMessage.class);

        if (result.getResultCode() == Message.ResultCode.SUCCESS) {
            return result.getReturnObj();
        } else {
            StringBuilder builder = new StringBuilder("message: ")
                    .append(result.getMsg())
                    .append(", error code:")
                    .append(result.getResultCode());
            throw new Exception(builder.toString());
        }
    }

    @Override
    public DeviceMasterObjInfoDto getDeviceObjInfo(Long accountId, Long mainObjId, boolean withSlave) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(anicelMeta.getAniServiceBusUrl())
                .append(anicelMeta.getServiceBusGetByUrl())
                .append("/")
                .append(accountId)
                .append("/")
                .append(mainObjId)
                .append("/")
                .append(withSlave);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stringBuilder.toString());

        DeviceObjInfoHttpMessage result = restTemplateFactory.getRestTemplate(new Class[]{DeviceMasterObjInfoDto.class})
                .getForObject(uriComponentsBuilder.toUriString(), DeviceObjInfoHttpMessage.class);

        if (result.getResultCode() == Message.ResultCode.SUCCESS) {
            return result.getReturnObj().size() == 1?result.getReturnObj().get(0):null;
        } else {
            StringBuilder builder = new StringBuilder("message: ")
                    .append(result.getMsg())
                    .append(", error code:")
                    .append(result.getResultCode());
            throw new Exception(builder.toString());
        }
    }

    @Override
    public void setAccessToken(String accessToken) {
        super.accessToken = accessToken;
    }
}