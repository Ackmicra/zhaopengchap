package com.uranus.platform.authorize.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.authorize.login.details.AdminDetails;
import com.uranus.platform.authorize.model.LoginType;
import com.uranus.platform.authorize.model.view.AdminView;
import com.uranus.platform.authorize.permission.handler.PermissionHandler;
import com.uranus.tools.psm.AccessToken;
import com.uranus.tools.psm.ResponseEntity;
import com.uranus.tools.psm.status.BusinessStatus;
import com.uranus.tools.psm.status.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

/**
 * @author 李亚斌
 * @date 2019/07/24 14:42
 * @since v1.1
 */
@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final static String TOKEN_PREFIX ="basic ";

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    @Qualifier("defaultAuthorizationServerTokenServices")
    private AuthorizationServerTokenServices authorizationServerTokenServices;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PermissionHandler permissionHandler;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //获取OAuth2服务认证
        response.setContentType("application/json;charset=UTF-8");
        try {
            String header = request.getHeader("Authorization");
            if (header == null || !header.toLowerCase().startsWith(TOKEN_PREFIX)) {
                throw new UnapprovedClientAuthenticationException("请求头中无client信息");
            }

            //验证服务认证
            String[] tokens = extractAndDecodeHeader(header, request);
            assert tokens.length == 2;
            String clientId = tokens[0];
            String clientSecret = tokens[1];
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
            if(clientDetails == null){
                throw new UnapprovedClientAuthenticationException("ClientId对应配置信息不存在：" + clientId);
            }else if( !passwordEncoder.matches(clientSecret,clientDetails.getClientSecret())){
                throw new UnapprovedClientAuthenticationException("clientSecret不匹配：" + clientSecret);
            }

            //获取Token令牌
            TokenRequest tokenRequest = new TokenRequest(new HashMap<>(0),clientId,clientDetails.getScope(),"custom");
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);
            OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

            //将权限信息保存至redis中
            AdminDetails adminDetails = (AdminDetails) authentication.getPrincipal();
            permissionHandler.savePermissionList(token.getValue(),adminDetails.getPermissionList());

            log.info("登陆成功");
            response.getWriter().write(objectMapper.writeValueAsString(createAdminViewResponse(authentication,token)));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("登陆失败");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            ResponseEntity responseEntity = ResponseEntity.Failure.other(HttpStatus.UNAUTHORIZED, BusinessStatus.LOGIN_FAILURE)
                    .failureMessage(e.getMessage()).response();
            response.getWriter().write(objectMapper.writeValueAsString(responseEntity));
        }
    }

    private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
            throws IOException {
        final String charset = "UTF-8";
        byte[] base64Token = header.substring(6).getBytes(charset);
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        }
        catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }

        String token = new String(decoded,charset);

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[] { token.substring(0, delim), token.substring(delim + 1) };
    }

    private ResponseEntity<AdminView> createAdminViewResponse(Authentication authentication, OAuth2AccessToken token){
        AccessToken accessToken = createAccessToken(token);
        AdminDetails adminDetails = (AdminDetails) authentication.getPrincipal();
        AdminView adminView = new AdminView();
        adminView.setUsername(adminDetails.getUsername());
///        adminView.setRoleNoList(adminDetails.getRoleList().stream().map(RoleDomain::getRoleName).collect(Collectors.toList()));
        adminView.setAccessToken(accessToken);
        adminView.setLoginType(LoginType.WEB.name());
        return ResponseEntity.Success.OK(adminView).response();
    }

    private AccessToken createAccessToken(OAuth2AccessToken token){
        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(token.getValue());
        accessToken.setTokenType(token.getTokenType());
        accessToken.setExpiresIn(token.getExpiresIn());
        accessToken.setRefreshToken(token.getRefreshToken().getValue());
        return accessToken;
    }
}
