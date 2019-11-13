package com.uranus.platform.business.jd.service;

import com.uranus.platform.business.jd.entity.vo.JdResponseView;

/**
 * @Description: 还款计划业务层
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年8月10日下午12:09:51
 */
public interface JdRepayPlanService {

	/**  
	* @Description 还款计划同步接口
	* @param projNo 项目编号
	* @param channelId 京东机构编号，AOP插入ws_base记录表会使用
	* @param bizContent bizContent 京东业务数据（解密后）
	*/  
	JdResponseView syncRepayPlan(String projNo, String channelId, String bizContent);

	/**
	 * 
	* @Title：changeRepayPlan 
	* @Description：还款计划更新
	* @param ：@param projNo
	* @param ：@param channelId
	* @param ：@param bizContent
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	JdResponseView changeRepayPlan(String projNo, String channelId, String bizContent);
	
	/**
	 * 
	* @Title：queryRepayPlan 
	* @Description：还款计划查询
	* @param ：@param projNo
	* @param ：@param channelId
	* @param ：@param bizContent
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	JdResponseView queryRepayPlan(String projNo, String channelId, String bizContent);
	
}
