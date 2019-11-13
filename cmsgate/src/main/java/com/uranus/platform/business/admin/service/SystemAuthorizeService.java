package com.uranus.platform.business.admin.service;

import com.uranus.platform.business.admin.entity.bo.SystemAdminDomain;
import com.uranus.platform.business.admin.entity.model.SystemAdminData;

import java.util.List;

/**
 * @author 李亚斌
 * @date 2019/07/25 17:09
 * @since v1.1
 */
public interface SystemAuthorizeService {
    /**
     * 新增用户
     * @return 新增成功
     */
    boolean addNewAdmin(SystemAdminDomain systemAdminDomain);

    /**
     * 修改用户的密码
     * @param username 用户名
     * @param password 用户密码
     * @return 修改成功
     */
    boolean updateAdminPassword(String username,String password);

    /**
     * 修改某个用户为不可用
     * @param username 用户名称
     * @return 修改成功与否
     */
    boolean lockAdmin(String username);

    /**
     * 解锁某个用户为可用
     * @param username 用户名称
     * @return 修改成功与否
     *
     * 不建议使用参数将多种状态通过一个方法进行修改，例如(updatestatus(status))代码上虽然简单了，但理解难度增加，以及并发情境下可能会出现脏读和脏写
     */
    boolean unlockAdmin(String username);

    /**
     * 根据用户名查询管理员
     * @param username 用户名
     * @return 管理员信息
     */
    SystemAdminDomain findAdminByUsername(String username);

    /**
     * 查询全部管理员信息
     * @return 全部管理员列表
     */
    List<SystemAdminDomain> findAllSystemAdmin();

    /**
     * 分页查询管理员信息
     * @param pageNum 当前页
     * @param pageSize 当前页显示范围
     * @return 查询结果
     */
    List<SystemAdminDomain> findAllByPage(SystemAdminDomain adminDomain,int pageNum,int pageSize);

}
