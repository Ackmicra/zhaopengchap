package com.uranus.platform.business.jd.dao;

import com.uranus.platform.business.jd.entity.po.JdRepurchaseApplyData;
import java.util.List;

public interface JdRepurchaseApplyDataMapper {
    int deleteByPrimaryKey(String jdId);

    int insert(JdRepurchaseApplyData record);

    JdRepurchaseApplyData selectByPrimaryKey(String jdId);

    List<JdRepurchaseApplyData> selectAll();

    int updateByPrimaryKey(JdRepurchaseApplyData record);
    
    int updateByApplicationNo(JdRepurchaseApplyData record);
    
    List<JdRepurchaseApplyData> getByApplicationNoAndSts(String applicationNo);
    
    JdRepurchaseApplyData getByApplicationNoAndSts01(String applicationNo);
}