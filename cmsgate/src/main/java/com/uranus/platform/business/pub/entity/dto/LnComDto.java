package com.uranus.platform.business.pub.entity.dto;

import lombok.Data;

/**
 * @Describe (共同借款人信息) 
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月7日 下午5:07:27
 * 	
 */
@Data
public class LnComDto {
	
    private  String         comName            ;     //     借款人名称
	private  String         comIdtype          ;     //     证件类型  
	private  String         comIdno            ;     //     证件号码  
	private  String         comTel             ;     //     联系电话  
}
