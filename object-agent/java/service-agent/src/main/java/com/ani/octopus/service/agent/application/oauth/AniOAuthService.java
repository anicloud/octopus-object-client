package com.ani.octopus.service.agent.application.oauth;

import com.ani.octopus.service.agent.domain.oauth.AniOAuthAccessToken;
import com.ani.octopus.service.agent.domain.oauth.AuthorizationCodeParameter;
import com.ani.octopus.service.agent.domain.oauth.ImplicitParameter;
import com.ani.octopus.service.agent.domain.oauth.PasswordParameter;

/** This class is for OAuth2 to use.
 * Created by zhaoyu on 15-10-31.
 */
public interface AniOAuthService {
    /**
     * the method is for OAuth2 [Authorization Code] mode to use to get access token.
     * @param code
     * @param authorizationCodeParameter
     * @return
     */
    AniOAuthAccessToken getOAuth2AccessToken(String code, AuthorizationCodeParameter authorizationCodeParameter);

    /**
     * the method is for OAuth2 [Password] mode to use to get access token.
     * @param passwordParameter
     * @return
     */
    AniOAuthAccessToken getOAuth2AccessToken(PasswordParameter passwordParameter);

    /**
     *the method is for OAuth2 [Implicit] mode to use to get access token.
     * @param implicitParameter
     * @return
     */
    AniOAuthAccessToken getOAuth2AccessToken(ImplicitParameter implicitParameter);

    /**
     *
     * @param refreshToken
     * @param authorizationCodeParameter
     * @return
     */
    AniOAuthAccessToken refreshAccessToken(String refreshToken, AuthorizationCodeParameter authorizationCodeParameter);
}
