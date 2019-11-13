package com.uranus.platform.authorize;

import com.uranus.platform.authorize.login.AdminDetailsService;
import com.uranus.platform.authorize.properties.AuthorizeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李亚斌
 * @date 2019/07/24 9:23
 * @since v1.1
 */
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfigure extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorizeProperties authorizeProperties;
    @Autowired
    private AdminDetailsService adminDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    @Qualifier("uranusTokenStore")
    private TokenStore uranusTokenStore;
    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired(required = false)
    @Qualifier("jwtTokenEnhancer")
    private TokenEnhancer jwtTokenEnhancer;


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(passwordEncoder);
        security.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()");
        super.configure(security);
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();

        builder.withClient(authorizeProperties.getClientId())
                .secret( passwordEncoder.encode(authorizeProperties.getClientSecret()))
                .accessTokenValiditySeconds(authorizeProperties.getAccessTokenValiditySecond())
                .authorizedGrantTypes("password","refresh_token","client_credentials")
                .refreshTokenValiditySeconds(authorizeProperties.getRefreshTokenValiditySeconds())
                .scopes("all")
//         增加其他项目的令牌配置
///        .and().withClient()
        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)  {
        endpoints
            //使用redis存储令牌
            .tokenStore(uranusTokenStore)
            .authenticationManager(authenticationManager)
            .userDetailsService(adminDetailsService);
        if(jwtAccessTokenConverter != null && jwtTokenEnhancer != null){
            //添加jwt信息增强链
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>(2);
            enhancers.add(jwtAccessTokenConverter);
            enhancers.add(jwtTokenEnhancer);
            enhancerChain.setTokenEnhancers(enhancers);
            endpoints
                    .tokenEnhancer(enhancerChain)
                    .accessTokenConverter(jwtAccessTokenConverter);
        }
    }
}
