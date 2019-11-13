package com.uranus.platform.authorize;

import com.uranus.platform.authorize.login.AdminDetailsService;
import com.uranus.platform.authorize.model.domain.PermissionDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

/**
 * @author 李亚斌
 * @date 2019/07/23 18:07
 * @since v1.1
 */
@Configuration
public class AuthorizeConfigure {
    @Autowired
    private AdminDetailsService adminDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder(){
        //使用默认的BCrypt加密方法
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(adminDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }

    @Bean("permissionRedisTemplate")
    public RedisTemplate<String, PermissionDomain> permissionRedisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String, PermissionDomain> permissionRedisTemplate = new RedisTemplate<>();
        Jackson2JsonRedisSerializer<PermissionDomain> serializer = new Jackson2JsonRedisSerializer<PermissionDomain>(PermissionDomain.class);
        permissionRedisTemplate.setValueSerializer(serializer);
        permissionRedisTemplate.setHashKeySerializer(serializer);
        permissionRedisTemplate.setKeySerializer(new StringRedisSerializer());
        permissionRedisTemplate.setHashKeySerializer(new StringRedisSerializer());

        permissionRedisTemplate.setConnectionFactory(connectionFactory);
        return permissionRedisTemplate;
    }
}
