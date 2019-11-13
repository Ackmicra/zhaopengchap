package com.uranus.platform.authorize;

import com.uranus.platform.authorize.handler.JwtLogoutHandler;
import com.uranus.platform.authorize.handler.JwtLogoutSuccessHandler;
import com.uranus.platform.authorize.handler.LoginFailureHandler;
import com.uranus.platform.authorize.handler.LoginSuccessHandler;
import com.uranus.platform.authorize.login.AdminDetailsService;
import com.uranus.platform.authorize.properties.AuthorizeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 李亚斌
 * @date 2019/07/24 10:17
 * @since v1.1
 */
//@Configuration
public class AuthorizeWebServerConfigure extends WebSecurityConfigurerAdapter {
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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
///                .loginPage(authorizeProperties.getLoginUri())
                .loginProcessingUrl(authorizeProperties.getLoginProcessUri())
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
            .and()
                .headers().xssProtection().and()
//            .and()
//                .logout()
//                .logoutUrl(authorizeProperties.getLogoutUri())
//                .addLogoutHandler(jwtLogoutHandler)
//                .logoutSuccessHandler(jwtLogoutSuccessHandler)
//                .logoutSuccessHandler(securityLogoutHandler)
            .and()
                .authorizeRequests()
                .antMatchers(authorizeProperties.initWhiteListUrl())
                .permitAll()
                .anyRequest().authenticated()
            .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminDetailsService).passwordEncoder(passwordEncoder);
    }
}
