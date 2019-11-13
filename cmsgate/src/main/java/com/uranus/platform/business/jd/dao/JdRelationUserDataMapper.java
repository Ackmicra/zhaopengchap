package com.uranus.platform.business.jd.dao;

import java.util.List;

import com.uranus.platform.business.jd.entity.po.JdRelationUserData;

public interface JdRelationUserDataMapper {
    int deleteByPrimaryKey(String jdId);

    int insert(JdRelationUserData record);

    JdRelationUserData selectByPrimaryKey(String jdId);

    List<JdRelationUserData> selectAll();

    int updateByPrimaryKey(JdRelationUserData record);

	List<JdRelationUserData> getByApplicationNo(String applicationNo);
}