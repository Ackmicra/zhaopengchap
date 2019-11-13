package com.uranus.platform.business.pub.entity.dto;

import java.util.List;

import lombok.Data;

/**
 * @Describe 小微2101接口响应数据Dto
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月29日 下午7:55:49
 * 
 */
@Data
public class Response2101Dto {
	private String batNo; //批次编号
	private Integer dataCnt; //接收记录数
	private List<Response2001ListDto> list ; //错误数据记录
}
