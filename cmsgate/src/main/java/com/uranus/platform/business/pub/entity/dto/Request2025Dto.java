package com.uranus.platform.business.pub.entity.dto;

import lombok.Data;

/**
 * @Describe 小微签约关系2025接口申请Dto数据
 * @author   wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月16日 下午2:02:00
 * 
 */
@Data
public class Request2025Dto {

	 private String    projNo        ;        // 项目号 
	 private String    custName      ;        // 客户名称   
	 private String    idType        ;        // 证件类型   
	 private String    idNo          ;        // 证件号码   
	 private String    phoneNo       ;        // 手机号     
	 private String    acno          ;        // 账户号     
	 private String    bankCode      ;        // 银行代码  
	 private String    cardChn       ;        // 渠道号
}
