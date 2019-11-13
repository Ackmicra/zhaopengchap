package com.uranus.platform.business.pub.entity.dto;

import java.util.List;

import lombok.Data;

/**
 * @Describe 小微2102接口响应数据List实体类
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月23日 下午3:31:54
 * 
 */
@Data
public class Response2102ListDto {

	private String pactNo; //合同号码
	private String dealSts; //处理结果
	private String dealDesc; //处理描述
	private String payDate; //放款日期
	
	private List<Response2102AcListDto> listAc; 
}
