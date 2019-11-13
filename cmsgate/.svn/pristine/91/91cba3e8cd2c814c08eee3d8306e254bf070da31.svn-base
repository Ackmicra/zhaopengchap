package com.uranus.platform.authorize.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.tools.psm.ResponseEntity;
import com.uranus.tools.psm.status.BusinessStatus;
import com.uranus.tools.psm.status.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 李亚斌
 * @date 2019/07/24 15:10
 * @since v1.1
 */
@Component
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("登陆失败");
        response.setContentType("application/json;charset=UTF-8");
        String errorMessage = "";
        if(exception instanceof UsernameNotFoundException) {
            errorMessage = exception.getMessage();
        }

        else if(exception instanceof BadCredentialsException) {
            errorMessage = "密码校验错误";
        }

        else if(exception instanceof LockedException) {
            errorMessage = "用户被锁定";
        }

        else if(exception instanceof DisabledException) {
            errorMessage = "用户不可用";
        }

        else if(exception instanceof AccountExpiredException) {
            errorMessage = "用户超过有效期";
        }
        else if(exception instanceof SessionAuthenticationException) {
            errorMessage = "用户session验证失败";
        }
        log.error(errorMessage);
        ResponseEntity result = ResponseEntity.Failure.other(HttpStatus.UNAUTHORIZED, BusinessStatus.LOGIN_FAILURE).failureMessage(errorMessage).response();
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
