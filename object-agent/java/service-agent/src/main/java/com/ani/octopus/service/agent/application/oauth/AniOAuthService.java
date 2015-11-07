package com.ani.octopus.service.agent.application.oauth;

import com.ani.octopus.service.agent.domain.oauth.AniOAuthAccessToken;
import com.ani.octopus.service.agent.domain.oauth.AuthorizationCodeParameter;
import com.ani.octopus.service.agent.domain.oauth.ImplicitParameter;
import com.ani.octopus.service.agent.domain.oauth.PasswordParameter;

/**
 * Created by zhaoyu on 15-10-31.
 */
public interface AniOAuthService {
    /**
     * the method is for OAuth2 [Authorization Code] mode to use to get access token.
     * @param code code for [Authorization Code] mode
     * @param authorizationCodeParameter parameters for get access token.
     * @return the access token information for your service.
     */
    AniOAuthAccessToken getOAuth2AccessToken(String code, AuthorizationCodeParameter authorizationCodeParameter);

    /**
     * the method is for OAuth2 [Password] mode to use to get access token.
     * @param passwordParameter parameters for get access token.
     * @return the access token information for your service.
     */
    AniOAuthAccessToken getOAuth2AccessToken(PasswordParameter passwordParameter);

    /**
     *the method is for OAuth2 [Implicit] mode to use to get access token.
     * @param implicitParameter parameters for get access token.
     * @return the access token information for your service.
     */
    AniOAuthAccessToken getOAuth2AccessToken(ImplicitParameter implicitParameter);

    /**
     * Use your refresh token to refresh a new access token.
     * @param refreshToken your refresh token
     * @param authorizationCodeParameter parameters for get refresh token.
     * @return the new access token
     */
    AniOAuthAccessToken refreshAccessToken(String refreshToken, AuthorizationCodeParameter authorizationCodeParameter);
}
