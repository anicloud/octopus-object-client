package com.ani.octopus.service.agent.service.aniservice;

import com.ani.octopus.service.agent.core.AnicelMeta;
import com.ani.octopus.service.agent.core.http.AbstractBaseService;
import com.ani.octopus.service.agent.core.http.RestTemplateFactory;
import com.ani.octopus.service.agent.core.validate.DomainObjectValidator;
import com.ani.octopus.service.agent.service.aniservice.dto.AniServiceDto;
import com.ani.octopus.service.agent.service.aniservice.dto.AniServiceRegisterDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collections;

/**
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
    public AniServiceDto register(AniServiceRegisterDto registerDto) {
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

        AniServiceDto aniServiceDto = restTemplateFactory.getRestTemplate(new Class[]{AniServiceDto.class})
                .postForObject(uriComponentsBuilder.toUriString(), requestEntity, AniServiceDto.class);
        return aniServiceDto;
    }
}
