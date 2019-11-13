package com.uranus.platform.authorize.service;

import com.uranus.platform.authorize.login.details.AdminDetails;

/**
 * @author 李亚斌
 * @date 2019/07/24 15:30
 * @since v1.1
 */
public interface AdminLoginService {
    /**
     * 根据用户名查询用户信息
     * @param username 登陆用户名
     * @return 查询到的用户信息
     */
    AdminDetails findAdminByUsername(String username);
}
