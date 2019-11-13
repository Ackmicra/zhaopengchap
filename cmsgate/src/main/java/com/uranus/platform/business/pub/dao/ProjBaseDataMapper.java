package com.uranus.platform.business.pub.dao;

import com.uranus.platform.business.pub.entity.po.ProjBaseData;
import java.util.List;

public interface ProjBaseDataMapper {
    int deleteByPrimaryKey(String projId);

    int insert(ProjBaseData record);

    ProjBaseData getByProjNo(String projNo);

    List<ProjBaseData> selectAll();

    int updateByPrimaryKey(ProjBaseData record);
}