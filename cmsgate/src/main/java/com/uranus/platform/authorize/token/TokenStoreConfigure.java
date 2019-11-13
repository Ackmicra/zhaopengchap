package com.uranus.platform.authorize.token;

import com.uranus.platform.authorize.properties.AuthorizeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author 李亚斌
 * @date 2019/07/24 18:08
 * @since v1.1
 */
@Configuration
public class TokenStoreConfigure {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    @Bean
    @ConditionalOnProperty(prefix = "security.authorize.oauth2",name = "storeType",havingValue = "redis")
    public TokenStore uranusTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Configuration
    @ConditionalOnProperty(prefix = "security.authorize.oauth2",name = "storeType",havingValue = "jwt",matchIfMissing = true)
    public static class JwtTokenConfig{
        @Autowired
        private AuthorizeProperties authorizeProperties;
        @Bean
        public TokenStore uranusTokenStore(){
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
            //配置密签
            accessTokenConverter.setSigningKey(authorizeProperties.getSignKey());
            return accessTokenConverter;
        }

        @Bean
        @ConditionalOnMissingBean(name="jwtTokenEnhancer")
        public TokenEnhancer jwtTokenEnhancer(){
            return new JwtTokenEnhancer();
        }
    }
}
