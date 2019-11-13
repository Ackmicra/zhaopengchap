package com.uranus.platform.business.pub.entity.dto;

import java.util.List;

import lombok.Data;

/**
 * @Describe 影响信息上传Dto
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月20日 下午2:19:15
 * 
 */
@Data
public class Request2108Dto {
	private String brNo;  //机构号
	private String batNo;  //批次号
	private Integer dataCnt; //记录数
	List<Response2108ListDto> list;  //文档信息
}
