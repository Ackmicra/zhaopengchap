package com.uranus.platform.business.jd.dao;

import com.uranus.platform.business.jd.entity.po.JdAccountData;



import java.util.List;

public interface JdAccountDataMapper {
    int deleteByPrimaryKey(String jdId);

    int insert(List<JdAccountData> record);
    
    int insert(JdAccountData record);

    JdAccountData selectByPrimaryKey(String jdId);

    List<JdAccountData> selectAll();

    int updateByPrimaryKey(JdAccountData record);
    
    JdAccountData getByApplicationNoAndAcUse(String applicationNo,String AcUse);

    JdAccountData selectByApplicationNo(String applicationNo);
}
