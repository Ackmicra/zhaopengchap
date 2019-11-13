package com.uranus.platform.business.jd.dao;

import com.uranus.platform.business.jd.entity.po.JdRepayPlanData;
import java.util.List;

public interface JdRepayPlanDataMapper {
    int deleteByPrimaryKey(String jdId);

    int insert(List<JdRepayPlanData> records);

    JdRepayPlanData selectByPrimaryKey(String jdId);

    List<JdRepayPlanData> selectAll();

    int updateByPrimaryKey(JdRepayPlanData record);
    
    String getJdId();
}