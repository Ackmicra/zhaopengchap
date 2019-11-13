package com.uranus.platform.authorize.login;

import com.uranus.platform.authorize.login.details.AdminDetails;
import com.uranus.platform.authorize.service.AdminLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author 李亚斌
 * @date 2019/07/24 10:36
 * @since v1.1
 */
@Component
@Slf4j
public class AdminDetailsService implements UserDetailsService {
    @Autowired
    private AdminLoginService adminLoginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("登录用户：" + username);
        AdminDetails adminDetails = adminLoginService.findAdminByUsername(username);
        return new AdminDetails(adminDetails,adminDetails.getPassword());
    }
}
