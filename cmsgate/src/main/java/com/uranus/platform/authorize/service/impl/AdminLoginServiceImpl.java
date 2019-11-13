package com.uranus.platform.authorize.service.impl;

import com.uranus.platform.authorize.login.details.AdminDetails;
import com.uranus.platform.authorize.model.AccountStatus;
import com.uranus.platform.authorize.model.LoginType;
import com.uranus.platform.authorize.model.RequestMethodType;
import com.uranus.platform.authorize.model.domain.AdminDomain;
import com.uranus.platform.authorize.model.domain.PermissionDomain;
import com.uranus.platform.authorize.model.domain.RoleDomain;
import com.uranus.platform.authorize.service.AdminLoginService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李亚斌
 * @date 2019/07/24 15:38
 * @since v1.1
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    @Override
    public AdminDetails findAdminByUsername(String username) {
        AdminDomain adminDomain = defaultManagerDomain();
        return convertToAdminDetails(adminDomain);
    }

    private AdminDetails convertToAdminDetails(AdminDomain adminDomain){
        AdminDetails adminDetails = new AdminDetails(adminDomain.getUsername(),adminDomain.getPassword());
        adminDetails.setRoleDomainList(adminDomain.getRoleList());
        return adminDetails;
    }

    private AdminDomain defaultManagerDomain(){
        //仅限小微信托使用
        AdminDomain adminDomain = new AdminDomain("xiaoweixintuo","123457");

        PermissionDomain permissionDomain = new PermissionDomain("perm1");
        permissionDomain.setPermChName("权限0");
        permissionDomain.setUriExpression("/admin/**");
        permissionDomain.setRequestMethodType(RequestMethodType.ALL);
        PermissionDomain permissionDomain1 = new PermissionDomain("perm1");
        permissionDomain1.setPermChName("权限1");
        permissionDomain1.setUriExpression("/user/**");
        permissionDomain1.setRequestMethodType(RequestMethodType.ALL);
        PermissionDomain permissionDomain2 = new PermissionDomain("perm1");
        permissionDomain2.setPermChName("权限2");
        permissionDomain2.setUriExpression("/guest/**");
        permissionDomain2.setRequestMethodType(RequestMethodType.ALL);
        List<PermissionDomain> permissionDomainList = new ArrayList<PermissionDomain>(){
            {
                add(permissionDomain);
                add(permissionDomain1);
                add(permissionDomain2);
            }
        };

        RoleDomain roleDomain = new RoleDomain("ADMIN");
        roleDomain.setRoleChName("管理员");
        roleDomain.setPermList(permissionDomainList);
        List<RoleDomain> roleDomainList = new ArrayList<RoleDomain>(){{
            add(roleDomain);
        }};

        adminDomain.setAccountStatus(AccountStatus.NORMAL);
        adminDomain.setLoginTime(LocalDateTime.now());
        adminDomain.setLoginType(LoginType.WEB);
        adminDomain.setRoleList(roleDomainList);
        adminDomain.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123456"));
        return adminDomain;
    }
}
