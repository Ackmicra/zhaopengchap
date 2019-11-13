package com.uranus.platform.authorize.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 李亚斌
 * @date 2019/07/24 15:44
 * @since v1.1
 */
@Slf4j
@Component
public class JwtLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("用户执行登出操作");
    }
}
