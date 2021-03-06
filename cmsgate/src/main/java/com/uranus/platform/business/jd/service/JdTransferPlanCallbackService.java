package com.uranus.platform.business.jd.service;

import com.uranus.platform.business.jd.entity.vo.JdCallbackRequestView;

/**
 * 
* @ClassName:：JdRepayPlanCallbackService 
* @Description： 扣款计划校验结果
* @author ：chenwendong
* @date ：2019年8月21日 下午2:23:44 
*
 */
public interface JdTransferPlanCallbackService {

	/**
	 * 
	* @Title：repayPlanCallback 
	* @Description：还款计划同步回调
	* @param ：@param applicationNo
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	JdCallbackRequestView transferPlanCallback(String batchNo, int delayLevel, String taskType);
	
}
