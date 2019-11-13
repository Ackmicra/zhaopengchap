package com.uranus.platform.business.pub.entity.dto;

import lombok.Data;

/**
 * @Describe 小微2002接口响应数据List实体类
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月21日 下午2:36:02
 * 
 */
@Data
public class Response2002ListDto {
	
	private String prePactNo; //预审批合同号
	private String appId;     //预审批ID
	private String dealSts;   //处理结果
	private String dealDesc;  //处理描述
}
