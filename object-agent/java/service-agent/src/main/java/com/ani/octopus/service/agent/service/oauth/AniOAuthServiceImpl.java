package com.ani.octopus.service.agent.service.oauth;

import com.ani.octopus.service.agent.core.config.AnicelMeta;
import com.ani.octopus.service.agent.core.http.AbstractBaseService;
import com.ani.octopus.service.agent.core.http.RestTemplateFactory;
import com.ani.octopus.service.agent.core.validate.DomainObjectValidator;
import com.ani.octopus.service.agent.service.oauth.dto.AniOAuthAccessToken;
import com.ani.octopus.service.agent.service.oauth.dto.AuthorizationCodeParameter;
import com.ani.octopus.service.agent.service.oauth.dto.ImplicitParameter;
import com.ani.octopus.service.agent.service.oauth.dto.PasswordParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.ValidationException;
import java.util.Collections;

/**
 * The implementation of the AniOAuthService and extends from AbstractBaseService. <br><br>
 * This class provides Authorization Code Mode and Password Mode for OAuth2. And also provides the
 * token refresh method.
 * <br><br>
 * <strong>Use Example:</strong><br>
 * <pre>
 *     AnicelMeta anicelMeta = new AnicelMeta();
 *     // create the RestTemplateFactory
 *     RestTemplateFactory templateFactory = new RestTemplateFactory();
 *     // create AccountGroupService instance
 *     AniOAuthService aniOAuthService = new AniOAuthServiceImpl(
 *          anicelMeta,
 *          templateFactory
 *     );
 *     // get access token
 *     AniOAuthAccessToken accessToken = aniOAuthService.getOAuth2AccessToken(code, authorizationCodeParameter);
 *     // refresh token
 *     AniOAuthAccessToken refreshToken = aniOAuthService.refreshAccessToken(.., ..);
 *     ......
 * </pre>
 * Created by zhaoyu on 15-10-31.
 */
public class AniOAuthServiceImpl extends AbstractBaseService implements AniOAuthService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AniOAuthServiceImpl.class);

    public AniOAuthServiceImpl() {
        super();
    }

    public AniOAuthServiceImpl(AnicelMeta anicelMeta, RestTemplateFactory restTemplateFactory) {
        super(anicelMeta, restTemplateFactory, null);
    }

    @Override
    public AniOAuthAccessToken getOAuth2AccessToken(String code, AuthorizationCodeParameter authorizationCodeParameter) {
        if (!DomainObjectValidator.isDomainObjectValid(authorizationCodeParameter)) {
            throw new ValidationException("Invalid AuthorizationCodeParameter Instance.");
        }

        MultiValueMap<String, String> valueMap = authorizationCodeParameter.convertParameterToMapForAccessToken();
        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(anicelMeta.getAniServiceBusUrl() + anicelMeta.getAccountOAuthTokenUrl())
                .queryParams(valueMap)
                .queryParam(RestTemplateFactory.CODE, code);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<AniOAuthAccessToken> requestEntity = new HttpEntity<>(httpHeaders);

        AniOAuthAccessToken result = this.restTemplateFactory.getRestTemplate(new Class[]{AniOAuthAccessToken.class})
                .postForObject(componentsBuilder.toUriString(), requestEntity, AniOAuthAccessToken.class);
        LOGGER.info("access-token is {}.", result);
        return result;
    }

    @Override
    public AniOAuthAccessToken getOAuth2AccessToken(PasswordParameter passwordParameter) {
        if (!DomainObjectValidator.isDomainObjectValid(passwordParameter)) {
            throw new ValidationException("Invalid PasswordParameter Instance.");
        }
        MultiValueMap<String, String> valueMap = passwordParameter.convertParameter();
        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(anicelMeta.getAniServiceBusUrl() + anicelMeta.getAccountOAuthTokenUrl())
                .queryParams(valueMap);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<AniOAuthAccessToken> requestEntity = new HttpEntity<>(httpHeaders);

        AniOAuthAccessToken result = this.restTemplateFactory.getRestTemplate(new Class[]{AniOAuthAccessToken.class})
                .postForObject(componentsBuilder.toUriString(), requestEntity, AniOAuthAccessToken.class);
        LOGGER.info("access-token is {}.", result);
        return result;
    }

    @Override
    public AniOAuthAccessToken getOAuth2AccessToken(ImplicitParameter implicitParameter) {
        if (!DomainObjectValidator.isDomainObjectValid(implicitParameter)) {
            throw new ValidationException("Invalid ImplicitParameter Instance.");
        }
        if (true)
            throw new RuntimeException("DO NOT SUPPORT NOW.");
        // TODO
        return null;
    }

    @Override
    public AniOAuthAccessToken refreshAccessToken(String refreshToken, AuthorizationCodeParameter authorizationCodeParameter) {
        MultiValueMap<String, String> valueMap = authorizationCodeParameter.convertParameterToMapForRefreshToken();
        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(anicelMeta.getAniServiceBusUrl() + anicelMeta.getAccountOAuthTokenUrl())
                .queryParams(valueMap)
                .queryParam(restTemplateFactory.REFRESH_TOKEN, refreshToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<AniOAuthAccessToken> requestEntity = new HttpEntity<>(httpHeaders);

        AniOAuthAccessToken result = this.restTemplateFactory.getRestTemplate(new Class[]{AniOAuthAccessToken.class})
                .postForObject(componentsBuilder.toUriString(), requestEntity, AniOAuthAccessToken.class);
        return result;
    }
}
