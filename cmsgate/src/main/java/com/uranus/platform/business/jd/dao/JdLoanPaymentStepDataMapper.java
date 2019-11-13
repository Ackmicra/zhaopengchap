package com.uranus.platform.business.jd.dao;

import java.util.List;

import com.uranus.platform.business.jd.entity.po.JdLoanPaymentStepData;

public interface JdLoanPaymentStepDataMapper {
    int deleteByPrimaryKey(String jdId);

    int insert(JdLoanPaymentStepData record);

    JdLoanPaymentStepData selectByPrimaryKey(String jdId);

    List<JdLoanPaymentStepData> selectAll();

    int updateByPrimaryKey(JdLoanPaymentStepData record);

	List<JdLoanPaymentStepData> getByApplicationNo(String applicationNo);
	
	int updatePayment(JdLoanPaymentStepData jdLoanApplyInfoData);
	
	JdLoanPaymentStepData getByApplicationNo1(String applicationNo);
	
	String getJdIdByApplicationNo(String applicationNo);
	
}