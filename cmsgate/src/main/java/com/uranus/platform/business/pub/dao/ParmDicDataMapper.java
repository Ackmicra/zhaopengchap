package com.uranus.platform.business.pub.dao;

import com.uranus.platform.business.pub.entity.po.ParmDicData;
import java.util.List;

public interface ParmDicDataMapper {
    int insert(ParmDicData record);

    List<ParmDicData> selectAll();
    
    ParmDicData getByKeyNameOptCode(ParmDicData parmDicData);
    
    ParmDicData selectByPrimaryKey(String keyId);

	ParmDicData deleteParmDic(ParmDicData parmDicData);

	int update(ParmDicData parmDicData);

	ParmDicData getByKeyNameMateCode(ParmDicData parmDicData);
}