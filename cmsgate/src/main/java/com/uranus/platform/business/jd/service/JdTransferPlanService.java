package com.uranus.platform.business.jd.service;

import com.uranus.platform.business.jd.entity.vo.JdResponseView;
/**
 * 
* @ClassName:：JdTransferPlanService 
* @Description： 扣款计划业务层
* @author ：chenwendong
* @date ：2019年8月15日 上午11:15:56 
*
 */
public interface JdTransferPlanService {

	 /**
	  * 
	 * @Title：syncTransferPlan 
	 * @Description：扣款计划上送
	 * @param ：@param projNo
	 * @param ：@param channelId
	 * @param ：@param bizContent
	 * @param ：@return 
	 * @return ：JdResponseView 
	 * @throws
	  */
	JdResponseView syncTransferPlan(String projNo, String channelId, String bizContent);
	/**
	 * 
	 * @Title：syncTransferPlan 
	 * @Description：扣款计划上送
	 * @param ：@param projNo
	 * @param ：@param channelId
	 * @param ：@param bizContent
	 * @param ：@return 
	 * @return ：JdResponseView 
	 * @throws
	 */
	JdResponseView syncTransferPlanNew(String projNo, String channelId, String bizContent);
	
	/**
	 * 
	* @Title：paymentOrder 
	* @Description：支付单
	* @param ：@param projNo
	* @param ：@param channelId
	* @param ：@param bizContent
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	JdResponseView paymentOrder(String projNo, String channelId, String bizContent);
	/**
	 * 
	* @Title：repurchaseApply 
	* @Description：回购申请
	* @param ：@param projNo
	* @param ：@param channelId
	* @param ：@param bizContent
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	JdResponseView repurchaseApply(String projNo, String channelId, String bizContent);

	/**  
	* @Description
	* 			扣款计划处理结果查询接口
	*/  
	JdResponseView queryForTransferPlans(String channelProdNo, String channelId, String decryptDate);

	/**  
	* @Description
	* 			支付订单查询接口
	*/  
	JdResponseView queryPaymentOrder(String channelProdNo, String channelId, String decryptDate);
}
