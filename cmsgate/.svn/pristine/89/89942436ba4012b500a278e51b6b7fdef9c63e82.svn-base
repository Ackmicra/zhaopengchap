package com.uranus.platform.utils.jd.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 李亚斌
 * @date 2019/07/30 18:36
 * @since v1.1
 */
@Configuration
public class JdSecurityConfigure {
    @Value("${jd.security.priCert}")
    private String priCertPath;
    @Value("${jd.security.pubCert}")
    private String pubCertPath;
    @Value("${jd.security.password}")
    private String password;

    @Bean
    public SignEnvelopServiceKey signEnvelopServiceKey(){
        return new SignEnvelopServiceKey(priCertPath,pubCertPath,password);
    }
}
