package com.uranus.platform.authorize.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 李亚斌
 * @date 2019/07/24 11:20
 * @since v1.1
 */
@Component
@PropertySource(value = "classpath:/authorizationConfig.properties")
@ConfigurationProperties(prefix = "authorization")
@Getter
@Setter
@Slf4j
public class AuthorizeProperties {
    private final String authorizeTokenPrefix = "bearer ";

    private String loginUri="/authorization/loginPage";
    private String loginProcessUri="/authorization/login";

    private String logoutUri="/authorization/logout";
    private String logoutSuccessUri="/authorization/logoutSuccess";

    private String allAuthorityRoleName="ALL_ADMIN";

    private String sessionExpiredUrl = "/authorization/sessionExpired";

    private String sessionInvalid="/authorization/sessionInvalid";

    private String clientId = "demo-id";
    private String clientSecret= "demo-secret";
    private String signKey= "demo-key";

    private int accessTokenValiditySecond = 60 * 20;
    private int refreshTokenValiditySeconds = 60 * 60 * 12;

    private boolean enablePermission;
    private boolean enableAuthorize;

    public String[] initWhiteListUrl(){
        String whiteUrlPaths = "/login";
        File whitelistConf = new File(AuthorizeProperties.class.getClassLoader().getResource("whitelist.conf").getPath());
        if(!whitelistConf.exists()){
            log.warn("没有配置whitelist.conf，授权页面将被默认设置为" + whiteUrlPaths);
            return new String[]{whiteUrlPaths};
        }
        try (Stream<String> stream = Files.lines(whitelistConf.toPath())) {
            List<String> whiteUrlList = stream.filter(line -> !line.startsWith("#") && line.trim().length() > 0).collect(Collectors.toList());
            return whiteUrlList.toArray(String[]::new);
        } catch (IOException e) {
            throw new RuntimeException("读取白名单数据时出现错误");
        }
    }
}
