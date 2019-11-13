package com.uranus.platform.business.jd.dao;

import com.uranus.platform.business.jd.entity.po.JdTransferPlansData;
import java.util.List;

public interface JdTransferPlansDataMapper {
    int deleteByPrimaryKey(String jdId);

    int insert(List<JdTransferPlansData> record);

    JdTransferPlansData selectByPrimaryKey(String jdId);

    List<JdTransferPlansData> selectAll();

    int updateByPrimaryKey(JdTransferPlansData record);
    
    List<JdTransferPlansData> getByBatchNo(String batchNumber);
    
    List<JdTransferPlansData> getByBatchNoAndSts(String batchNumber);
    
    int updateByApplicationNo(JdTransferPlansData record);

	int updateCheckResultByBatchNo(JdTransferPlansData jdTransferPlansData);

	int updateLoanResultByBatchNo(JdTransferPlansData jdTransferPlansData);
}