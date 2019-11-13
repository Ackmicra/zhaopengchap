package com.uranus.platform.business.admin.dao;

import com.uranus.platform.business.admin.entity.model.SystemAdminData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemAdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SystemAdminData record);

    SystemAdminData selectByPrimaryKey(Integer id);

    List<SystemAdminData> selectAll();

    int updateByPrimaryKey(SystemAdminData record);

    SystemAdminData selectByUsername(String username);

    List<SystemAdminData>  selectByPage();
}