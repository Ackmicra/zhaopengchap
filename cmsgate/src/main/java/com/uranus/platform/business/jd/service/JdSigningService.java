package com.uranus.platform.business.jd.service;

import com.uranus.platform.business.jd.entity.po.JdSigningData;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;

/**
 * @Description: 签约业务层
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年8月10日下午12:01:17
 */
public interface JdSigningService {
	
	/**  
	* @Description 签约新增
	* @param 签约数据
	* @return 签约是否成功
	*/  
	boolean insert(JdSigningData jdSigningData);
	
	/**  
	* @Description 签约申请接口和签约确认接口
	* @param projNo  项目编号
	* @param channelId 京东传过来的机构编号
	* @param bizContent 解密后的业务数据
	* @param businessType 01-签约申请接口  02-签约确认接口 03-签约查询接口
	* @return
	*/  
	JdResponseView sign(String projNo, String channelId, String bizContent, String businessType);
}
