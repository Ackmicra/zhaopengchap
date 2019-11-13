package com.uranus.platform.business.pub.entity.dto;

import java.util.List;

import lombok.Data;

/**
 * @Describe 小微2001接口响应数据Dto
 * @author  wangshuai
 * @Date 创建时间：2019年8月8日 下午5:59:42
 * 
 */
@Data
public class Response2001Dto {
	
	private String batNo; //批次编号
	private Integer dataCnt; //接收记录数
	private List<Response2001ListDto> list ; //错误数据记录

}
