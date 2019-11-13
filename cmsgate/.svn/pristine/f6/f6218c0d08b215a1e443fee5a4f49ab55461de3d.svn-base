package com.uranus.platform.authorize.permission.handler.impl;

import com.uranus.platform.authorize.model.domain.PermissionDomain;
import com.uranus.platform.authorize.permission.handler.PermissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 李亚斌
 * @date 2019/04/15 20:19
 * @since v1.1
 */
@Service
public class PermissionHandlerImpl implements PermissionHandler {

    @Value("#{authorizeProperties.refreshTokenValiditySeconds}")
    private int refreshTokenValiditySeconds;

    @Autowired
    @Qualifier("permissionRedisTemplate")
    private RedisTemplate<String, PermissionDomain> permissionDomainRedisTemplate;

    @Autowired
    @Qualifier("uranusTokenStore")
    private TokenStore uranusTokenStore;

    @Override
    public List<PermissionDomain> getPermissionList(String token) {
        OAuth2Authentication authentication = uranusTokenStore.readAuthentication(token);
        String permUsernameKey = "permission_" + authentication.getName();
        List<PermissionDomain> permissionList = permissionDomainRedisTemplate.opsForList().range(permUsernameKey,0,-1);
        return permissionList;
    }

    @Override
    public void savePermissionList(String token, List<PermissionDomain> permissionDomainList) {
        OAuth2Authentication authentication = uranusTokenStore.readAuthentication(token);
        String permUsernameKey = "permission_" + authentication.getName();
        permissionDomainRedisTemplate.opsForList().leftPushAll(permUsernameKey,permissionDomainList);
        permissionDomainRedisTemplate.expire(permUsernameKey,refreshTokenValiditySeconds, TimeUnit.SECONDS);
    }
}
