package com.uranus.platform.authorize;

import com.uranus.platform.authorize.handler.JwtLogoutHandler;
import com.uranus.platform.authorize.handler.JwtLogoutSuccessHandler;
import com.uranus.platform.authorize.handler.LoginFailureHandler;
import com.uranus.platform.authorize.handler.LoginSuccessHandler;
import com.uranus.platform.authorize.login.AdminDetailsService;
import com.uranus.platform.authorize.permission.comfig.AuthorizeConfigManager;
import com.uranus.platform.authorize.properties.AuthorizeProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

/**
 * @author 李亚斌
 * @date 2019/07/24 9:26
 * @since v1.1
 */
@Configuration
@EnableResourceServer
@Slf4j
public class AuthorizationResourceServerConfigure extends ResourceServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminDetailsService adminDetailsService;
    @Autowired
    private AuthorizeProperties authorizeProperties;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    private JwtLogoutHandler jwtLogoutHandler;
    @Autowired
    private JwtLogoutSuccessHandler jwtLogoutSuccessHandler;
    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;
    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
///                .loginPage(authorizeProperties.getLoginUri())
                .loginProcessingUrl(authorizeProperties.getLoginProcessUri())
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
            .and()
                .headers().xssProtection().and()
            .and()
                .logout()
                .logoutUrl(authorizeProperties.getLogoutUri())
                .addLogoutHandler(jwtLogoutHandler)
                .logoutSuccessHandler(jwtLogoutSuccessHandler)
//                .logoutSuccessHandler(securityLogoutHandler)
            .and()
                .authorizeRequests()
                .antMatchers(authorizeProperties.initWhiteListUrl())
                .permitAll()
                .anyRequest().authenticated()
            .and()
                .csrf().disable();
//        //添加rbac授权系统的鉴权拦截
        authorizeConfigManager.config(http.authorizeRequests());
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        resources.expressionHandler(expressionHandler);
//        resources.authenticationEntryPoint(authorizationExceptionEntryPoint);
    }

}
