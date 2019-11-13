package com.uranus.platform.business.jd.dao;

import com.uranus.platform.business.jd.entity.po.JdTransferBatchData;
import java.util.List;

public interface JdTransferBatchDataMapper {
	
    int deleteByPrimaryKey(String jdId);

    int insert(JdTransferBatchData record);

    JdTransferBatchData selectByPrimaryKey(String jdId);

    List<JdTransferBatchData> selectAll();

    int updateByPrimaryKey(JdTransferBatchData record);
    
    int updateByBatchNo(JdTransferBatchData record);
    
    JdTransferBatchData getByBatchNo(String batchNumber);

	int updateCheckResultByBatchNo(JdTransferBatchData batchPlans);
}