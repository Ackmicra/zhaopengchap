package com.uranus.platform.business.jd.service;

import com.uranus.platform.business.jd.entity.vo.JdCallbackRequestView;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;

/**
 * @Describe 放款接口
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月14日 上午11:11:32
 * 
 */
public interface JdLoanPaymentsService {
	
	/**  
	* @Description 放款指令接口
	* @param projNo  项目编号
	* @param channelId 京东传过来的机构编号
	* @param bizContent 解密后的业务数据
	* @return
	*/  
	JdResponseView requestfor2101(String projNo, String channelId, String bizContent);
	/**  
	* @Description 进件处理结果查询接口
	* @param applicationNo 申请号
	*/ 
	JdCallbackRequestView applyFor2102(String applicationNo, int delayLevel);
	/**  
	* @Description 放款结果查询接口
	* @param projNo  项目编号
	* @param channelId 京东传过来的机构编号
	* @param bizContent 解密后的业务数据
	* @return
	*/  
	JdResponseView requestfor2102(String channelProdNo, String channelId, String decryptDate);
}
