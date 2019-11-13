package com.uranus.platform.business.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.uranus.platform.business.admin.dao.SystemAdminMapper;
import com.uranus.platform.business.admin.entity.bo.SystemAdminDomain;
import com.uranus.platform.business.admin.entity.model.SystemAdminData;
import com.uranus.platform.business.admin.service.SystemAuthorizeService;
import com.uranus.tools.beans.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李亚斌
 * @date 2019/07/25 17:29
 * @since v1.1
 */
@Service
public class SystemAuthorizeServiceImpl implements SystemAuthorizeService {
    @Autowired
    private SystemAdminMapper systemAdminMapper;
    @Override
    public boolean addNewAdmin(SystemAdminDomain systemAdminDomain) {
        SystemAdminData systemAdminData = BeanCopyUtils.INSTANCE.convertTo(systemAdminDomain,SystemAdminData.class);
        return systemAdminMapper.insert(systemAdminData) > 0;
    }

    @Override
    public boolean updateAdminPassword(String username, String password) {
        return false;
    }

    @Override
    public boolean lockAdmin(String username) {
        return false;
    }

    @Override
    public boolean unlockAdmin(String username) {
        return false;
    }

    @Override
    public SystemAdminDomain findAdminByUsername(String username) {
        SystemAdminData systemAdminData = systemAdminMapper.selectByUsername(username);
        return BeanCopyUtils.INSTANCE.convertTo(systemAdminData,SystemAdminDomain.class);
    }

    @Override
    public List<SystemAdminDomain> findAllSystemAdmin() {
        return BeanCopyUtils.INSTANCE.convertToAsList(systemAdminMapper.selectAll(),SystemAdminDomain.class);
    }

    @Override
    public List<SystemAdminDomain> findAllByPage(SystemAdminDomain adminDomain,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return BeanCopyUtils.INSTANCE.convertToAsList(systemAdminMapper.selectByPage(),SystemAdminDomain.class);
    }
}
