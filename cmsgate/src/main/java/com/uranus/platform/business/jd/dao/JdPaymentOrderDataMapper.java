package com.uranus.platform.business.jd.dao;

import java.util.List;

import com.uranus.platform.business.jd.entity.po.JdPaymentOrderData;

public interface JdPaymentOrderDataMapper {
    int deleteByPrimaryKey(String jdId);

    int insert(JdPaymentOrderData record);

    JdPaymentOrderData selectByPrimaryKey(String jdId);

    List<JdPaymentOrderData> selectAll();

    int updateByPrimaryKey(JdPaymentOrderData record);
    
    JdPaymentOrderData getByBatchNo(String batchNumber);

	/**  
	* @Description
	* 		根据京东的批次号和批次类型查询
	*/  
	JdPaymentOrderData getByJDBatchNo(JdPaymentOrderData jdPaymentOrderData);
}