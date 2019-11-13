package com.uranus.platform.business.pub.entity.dto;

import lombok.Data;

/**
 * @Describe (借款关联人信息)
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月7日 下午5:07:37
 * 
 */
@Data
public class LnRelDto {
    private  String         relName        ;     //     借款人名称      
	private  String         relIdtype      ;     //     证件类型        
	private  String         relIdno        ;     //     证件号码        
	private  String         relTel         ;     //     联系电话   
	
	//工具对象，解决拼接报文的问题
	private  String         relType        ;     //     关系人类型

}
