package com.uranus.platform.authorize.permission.handler;

import com.uranus.platform.authorize.model.domain.PermissionDomain;

import java.util.List;

/**
 * @author 李亚斌
 * @date 2019/04/15 19:53
 * @since v1.1
 *
 * 基于以下原则开发此接口：
 * 每个用户同时只能以一个角色身份登陆系统。
 * 因此用户可以与权限列表对应，从而跳过角色关联直接一一对应
 */
public interface PermissionHandler {
    /**
     * 根据用户名称获取权限列表信息
     * @param token 登陆操作员名称
     * @return 该操作员绑定的用户权限列表
     */
    List<PermissionDomain> getPermissionList(String token);

    /**
     * 更新/保存用户名角色信息
     * @param token 需要绑定的登陆操作元名称
     * @param permissionDomainList 要保存的权限列表
     * 当缓存里存在该角色名称时，更新已有的角色权限列表。不存在时新增保存
     */
    void savePermissionList(String token, List<PermissionDomain> permissionDomainList);
}
