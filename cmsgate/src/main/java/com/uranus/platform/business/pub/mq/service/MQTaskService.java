package com.uranus.platform.business.pub.mq.service;

/**
 * @Description: MQ任务处理接口
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年8月27日上午11:27:43
 */
public interface MQTaskService {

	/**  
	* @param <T>
	 * @Description 审核结果回调服务
	* @param 申请号
	*/  
	void queryAuditResult(Object applicationNo, int delayLevel);
	
	/**  
	* @param <T>
	 * @Description 放款结果回调服务
	* @param 申请号
	*/  
	void queryLoanPaymentsResult(Object applicationNo, int delayLevel);
	
	/**  
	* @param <T>
	 * @Description 扣款计划校验结果回调服务
	* @param 批次号
	*/  
	void querytransferPlanResult(Object batchNo, int delayLevel, String taskType);
}
