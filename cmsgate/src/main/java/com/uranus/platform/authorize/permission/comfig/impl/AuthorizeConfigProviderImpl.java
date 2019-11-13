package com.uranus.platform.authorize.permission.comfig.impl;

import com.uranus.platform.authorize.permission.comfig.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author 李亚斌
 * @date 2019/04/15 19:48
 * @since v1.1
 */
@Component
@Order(Integer.MAX_VALUE)
public class AuthorizeConfigProviderImpl implements AuthorizeConfigProvider {
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.anyRequest().access("@rbacService.hasPermission(request)");
    }
}
