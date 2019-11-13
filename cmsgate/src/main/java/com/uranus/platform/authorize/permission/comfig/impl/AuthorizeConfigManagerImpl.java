package com.uranus.platform.authorize.permission.comfig.impl;

import com.uranus.platform.authorize.permission.comfig.AuthorizeConfigManager;
import com.uranus.platform.authorize.permission.comfig.AuthorizeConfigProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 李亚斌
 * @date 2019/04/15 19:48
 * @since v1.1
 */
@Component
public class AuthorizeConfigManagerImpl implements AuthorizeConfigManager {
    @Autowired
    private List<AuthorizeConfigProvider> authorizeConfigProviderList;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        authorizeConfigProviderList.forEach(provider -> provider.config(config));
    }
}
