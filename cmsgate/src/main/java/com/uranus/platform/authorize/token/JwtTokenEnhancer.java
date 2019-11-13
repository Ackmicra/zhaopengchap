package com.uranus.platform.authorize.token;

import com.uranus.platform.authorize.login.details.AdminDetails;
import com.uranus.tools.password.MD5Utils;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Map;

/**
 * @author 李亚斌
 * @date 2019/07/24 19:04
 * @since v1.1
 */
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        AdminDetails adminDetails = (AdminDetails)oAuth2Authentication.getUserAuthentication().getPrincipal();
        Map<String,Object> additionalInfo = null;
        try {
            additionalInfo = Map.of(
                    "company","dhcc",
                    "username", MD5Utils.md5Digest(adminDetails.getUsername())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((DefaultOAuth2AccessToken)oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;

    }
}
