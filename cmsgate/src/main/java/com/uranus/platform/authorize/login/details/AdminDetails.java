package com.uranus.platform.authorize.login.details;

import com.uranus.platform.authorize.model.domain.PermissionDomain;
import com.uranus.platform.authorize.model.domain.RoleDomain;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 李亚斌
 * @date 2019/07/24 10:59
 * @since v1.1
 */
@Setter
@Getter
public class AdminDetails implements UserDetails {
    private final String username;
    private final String password;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private List<RoleDomain> roleDomainList;

    public AdminDetails(String username, String password) {
        this.username = username;
        this.password = password;
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
        this.roleDomainList = new ArrayList<>();
    }

    public AdminDetails(AdminDetails adminDetails,String password) {
        this.username = adminDetails.getUsername();
        this.password = password;
        this.isCredentialsNonExpired = adminDetails.isCredentialsNonExpired();
        this.isEnabled = adminDetails.isEnabled();
        this.isAccountNonLocked = adminDetails.isAccountNonLocked();
        this.isAccountNonExpired = adminDetails.isAccountNonExpired();
        this.roleDomainList = adminDetails.getRoleDomainList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(roleDomainList.stream().map(RoleDomain::getRoleName).collect(Collectors.joining(",")));
    }

    public List<PermissionDomain> getPermissionList(){
        return roleDomainList.stream().flatMap(role -> role.getPermList().stream()).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * 账户是否过期
     * @return false为账户已过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    /**
     * 账户是否被锁定
     * @return false为被锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    /**
     * 密码是否过期
     * @return false为已过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    /**
     * 账户是否可用
     * @return false为不可用
     */
    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
