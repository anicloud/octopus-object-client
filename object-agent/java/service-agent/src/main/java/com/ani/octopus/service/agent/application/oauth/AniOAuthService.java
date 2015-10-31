package com.ani.octopus.service.agent.application.oauth;

import com.ani.octopus.service.agent.domain.oauth.AniOAuthAccessToken;
import com.ani.octopus.service.agent.domain.oauth.AuthorizationCodeParameter;
import com.ani.octopus.service.agent.domain.oauth.ImplicitParameter;
import com.ani.octopus.service.agent.domain.oauth.PasswordParameter;

/**
 * Created by zhaoyu on 15-10-31.
 */
public interface AniOAuthService {
    AniOAuthAccessToken getOAuth2AccessToken(String code, AuthorizationCodeParameter authorizationCodeParameter);
    AniOAuthAccessToken getOAuth2AccessToken(PasswordParameter passwordParameter);
    AniOAuthAccessToken getOAuth2AccessToken(ImplicitParameter implicitParameter);

    AniOAuthAccessToken refreshAccessToken(String refreshToken, AuthorizationCodeParameter authorizationCodeParameter);
}
