package com.uranus.platform.business.pub.entity.dto;

import lombok.Data;

/**
 * @Describe 小微签约关系接口查询Dto数据
 * @author   wangshuai0106@dhcc.com.cn
 * @version  创建时间：2019年8月5日 下午5:01:16
 * 
 */
@Data
public class SignQueryDto {
	
	private String    brNo       ;        // 合作机构号         
	private String    projNo     ;        // 信托项目号         
	private String    custName   ;        // 客户名称           
	private String    idType     ;        // 证件类型           
	private String    idNo       ;        // 证件号码           
	private String    acno       ;        // 账户号             
	private String    cardChn    ;        // 虚拟账户渠道       
	private String    serialNo   ;        // 签约流水号         
	private String    agreeCode  ;        // 协议号             
	                                                            


}
