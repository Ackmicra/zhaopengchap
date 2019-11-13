package com.uranus.platform.business.jd.service;

import com.uranus.platform.business.jd.entity.vo.JdResponseView;

/**
 * 
* @ClassName:：JdRepayPlanCallbackService 
* @Description： 还款计划同步回调
* @author ：chenwendong
* @date ：2019年8月21日 下午2:23:44 
*
 */
public interface JdRepayPlanCallbackService {

	/**
	 * 
	* @Title：repayPlanCallback 
	* @Description：还款计划同步回调
	* @param ：@param applicationNo
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	JdResponseView repayPlanCallback(String applicationNo);
	
}
