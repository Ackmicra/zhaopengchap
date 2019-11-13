package com.uranus.platform.authorize.permission.rbac.impl;

import com.uranus.platform.authorize.model.RequestMethodType;
import com.uranus.platform.authorize.model.domain.PermissionDomain;
import com.uranus.platform.authorize.permission.handler.PermissionHandler;
import com.uranus.platform.authorize.permission.rbac.RBACService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 李亚斌
 * @date 2019/07/24 20:50
 * @since v1.1
 */
@Component("rbacService")
@Slf4j
public class RBACServiceImpl implements RBACService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final String token_prefix = "bearer ";
    @Value("#{authorizeProperties.enablePermission}")
    private boolean enablePermission;
    @Value("#{authorizeProperties.enableAuthorize}")
    private boolean enableAuthorize;
    @Autowired
    @Qualifier("uranusTokenStore")
    private TokenStore uranusTokenStore;
    @Autowired
    private PermissionHandler permissionHandler;

    @Override
    public boolean hasPermission(HttpServletRequest request) {
        return hasPermission(request.getHeader("Authorization"),request.getRequestURI(),request.getMethod());
    }

    @Override
    public boolean hasPermission(String accessToken, String requestUri, String requestMethodType) {
        if(!enableAuthorize){
            return true;
        }
        if(accessToken == null || accessToken.isBlank()){
            return false;
        }
        if(accessToken.startsWith(token_prefix)){
            accessToken = accessToken.substring(token_prefix.length());
        }
        try {
            OAuth2AccessToken token = uranusTokenStore.readAccessToken(accessToken);
            if(token == null || token.isExpired()){
                log.warn("token 信息不存在或已过期");
                return false;
            }
            if(!enablePermission){
                return true;
            }
            List<PermissionDomain> permissionList = permissionHandler.getPermissionList(token.getValue());
            Optional<PermissionDomain> hashPermission = permissionList.stream()
                    //TODO 是否允许PermissionUri以逗号隔离，暂时不做此处理
                    .filter(permission -> antPathMatcher.match(permission.getUriExpression(),requestUri))
                    .filter(permission -> ( permission.getRequestMethodType() == RequestMethodType.ALL
                            || requestMethodType.equalsIgnoreCase(permission.getRequestMethodType().name())))
                    .findAny();
            if(!hashPermission.isPresent()){
                log.error("权限校验不通过！----------请求路径：" + requestUri +
                        "请求方式：" + requestMethodType+
                        ";权限表达式：" + permissionList.stream().map(per -> per.getUriExpression() + ":"  + per.getRequestMethodType()).collect(Collectors.joining(","))
                );
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }
}
