package com.uranus.platform.business.jd.service;

import com.uranus.platform.business.jd.entity.po.JdAccountData;

/**
 * @Description: 账户业务层
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年8月10日下午12:12:58
 */
public interface JdAccountService {
	
	/**  
	* @Description 账户新增操作
	* @param
	*/  
	boolean insert(JdAccountData jdAccountData);
}
