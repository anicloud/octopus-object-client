package com.ani.octopus.service.agent.service.aniservice;

import com.ani.octopus.service.agent.core.config.AnicelMeta;
import com.ani.octopus.service.agent.core.http.AbstractBaseService;
import com.ani.octopus.service.agent.core.http.RestTemplateFactory;
import com.ani.octopus.service.agent.core.validate.DomainObjectValidator;
import com.ani.service.bus.core.application.agent.message.AniServiceHttpMessage;
import com.ani.service.bus.core.application.agent.message.Message;
import com.ani.service.bus.core.application.dto.AniServiceDto;
import com.ani.service.bus.core.application.dto.AniServiceRegisterDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.ValidationException;
import java.util.Collections;

/**
 * The implementation of the AniServiceManager and extends from AbstractBaseService. If you want to register your
 * Service(or Application) on Anicloud Platform, you need to use this class.<br><br>
 * When you want to use OAuth2 to get the Account's resources on Anicloud Platform, <b>you must register your Application on
 * Anicloud Platform</b>. Then, you can use OAuth2(which is supported by Anicloud Platform) to get the access token of the current Account,
 * who was registered on Anicloud Platform. <br><br>
 * <strong>Use Example:</strong><br>
 * <pre>
 *     AnicelMeta anicelMeta = new AnicelMeta();
 *     // create the RestTemplateFactory
 *     RestTemplateFactory templateFactory = new RestTemplateFactory();
 *     // create AniServiceManagerImpl instance
 *     AniServiceManager aniServiceManager = new AniServiceManagerImpl(
 *          anicelMeta,
 *          templateFactory
 *     );
 *     // call the methods
 *     AniServiceRegisterDto dto = new AniServiceRegisterDto();
 *     aniServiceManager.register(dto);
 *     ......
 * </pre>
 * <Strong>Notice:</Strong><br>
 *  When you want to register your Application, you don't need the accessToken.
 * <br><br>
 * Created by zhaoyu on 15-10-31.
 */
public class AniServiceManagerImpl extends AbstractBaseService implements AniServiceManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(AniServiceManagerImpl.class);

    public AniServiceManagerImpl() {
    }

    public AniServiceManagerImpl(AnicelMeta anicelMeta, RestTemplateFactory restTemplateFactory) {
        super(anicelMeta, restTemplateFactory, null);
    }

    @Override
    public AniServiceDto register(AniServiceRegisterDto registerDto) throws Exception {
        if (!DomainObjectValidator.isDomainObjectValid(registerDto)) {
            throw new ValidationException("Invalid AniServiceRegisterDto Instance.");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<AniServiceRegisterDto> requestEntity = new HttpEntity<>(registerDto, httpHeaders);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(anicelMeta.getAniServiceBusUrl() + anicelMeta.getServiceBusRegisterUrl());

        LOGGER.info("post url : {}.", uriComponentsBuilder.toUriString());

        AniServiceHttpMessage result = restTemplateFactory.getRestTemplate(new Class[]{AniServiceDto.class})
                .postForObject(uriComponentsBuilder.toUriString(), requestEntity, AniServiceHttpMessage.class);

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
    public AniServiceDto getByAniService(String aniServiceId, String clientSecret) throws Exception {
        if (StringUtils.isEmpty(aniServiceId) || StringUtils.isEmpty(clientSecret)) {
            throw new Exception("AniServiceId or ClientSecret is null.");
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(anicelMeta.getAniServiceBusUrl())
                .append(anicelMeta.getServiceBusGetByUrl())
                .append("/")
                .append(aniServiceId)
                .append("/")
                .append(clientSecret);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stringBuilder.toString());
        LOGGER.info(uriComponentsBuilder.toUriString());
        AniServiceHttpMessage result = restTemplateFactory.getRestTemplate(new Class[]{AniServiceDto.class})
                .getForObject(uriComponentsBuilder.toUriString(), AniServiceHttpMessage.class);

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
}
