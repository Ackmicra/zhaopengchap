package com.uranus.platform.authorize.model.domain;

import com.uranus.platform.authorize.model.AccountStatus;
import com.uranus.platform.authorize.model.LoginType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 李亚斌
 * @date 2019/03/18 16:39
 * @since v1.1
 */
@Data
public class AdminDomain implements Serializable {
    /**
     * 用户名，且唯一，用于用户业务ID
     */
    private final String username;
    private String password;
    private AccountStatus accountStatus;
    private LocalDateTime loginTime;
    private LoginType loginType;
    private String salt;
    private String accId;
    private String userId;

    private List<RoleDomain> roleList;

    public AdminDomain(String username, String password) {
        this.username = username;
        this.password = password;
        this.accountStatus = AccountStatus.NORMAL;
    }
}
