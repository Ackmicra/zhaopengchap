package com.uranus.platform.business.jd.dao;

import com.uranus.platform.business.jd.entity.po.JdSigningData;
import java.util.List;

public interface JdSigningDataMapper {
    int deleteByPrimaryKey(String jdId);

    int insert(JdSigningData record);

    JdSigningData selectByPrimaryKey(String jdId);

    List<JdSigningData> selectAll();

    int updateByPrimaryKey(JdSigningData record);
    
    int updateTransactionNo(JdSigningData jdSigningData);
    
    String selectJdId();

	JdSigningData getByTransactionNo(String signTransactionNo);
}