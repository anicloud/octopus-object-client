package com.ani.octopus.service.agent.application.oauth;

import com.ani.octopus.service.agent.domain.oauth.AniOAuthAccessToken;
import com.ani.octopus.service.agent.domain.oauth.AuthorizationCodeParameter;
import com.ani.octopus.service.agent.domain.oauth.ImplicitParameter;
import com.ani.octopus.service.agent.domain.oauth.PasswordParameter;

/**
 * Created by zhaoyu on 15-10-31.
 */
public class AniOAuthServiceImpl implements AniOAuthService {

    @Override
    public AniOAuthAccessToken getOAuth2AccessToken(String code, AuthorizationCodeParameter authorizationCodeParameter) {
        // TODO
        return null;
    }

    @Override
    public AniOAuthAccessToken getOAuth2AccessToken(PasswordParameter passwordParameter) {
        // TODO
        return null;
    }

    @Override
    public AniOAuthAccessToken getOAuth2AccessToken(ImplicitParameter implicitParameter) {
        // TODO
        return null;
    }

    @Override
    public AniOAuthAccessToken refreshAccessToken(String refreshToken, AuthorizationCodeParameter authorizationCodeParameter) {
        // TODO
        return null;
    }
}
