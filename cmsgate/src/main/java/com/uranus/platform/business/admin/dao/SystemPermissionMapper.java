package com.uranus.platform.business.admin.dao;

import com.uranus.platform.business.admin.entity.model.SystemPermissionData;

import java.util.List;

public interface SystemPermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemPermissionData record);

    SystemPermissionData selectByPrimaryKey(Long id);

    List<SystemPermissionData> selectAll();

    int updateByPrimaryKey(SystemPermissionData record);
}