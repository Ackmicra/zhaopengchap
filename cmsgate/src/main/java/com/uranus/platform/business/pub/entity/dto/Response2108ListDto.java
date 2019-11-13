package com.uranus.platform.business.pub.entity.dto;

import lombok.Data;

/**
 * @Describe 文档信息ListDto belong to 影响信息上传Dto
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月20日 下午2:21:43
 * 
 */
@Data
public class Response2108ListDto {
	private String transNo ;  //业务号码
	private String docType ;  //文档类型
	private String docName ;  //文档名称
	private String businessType ;  //业务场景类型
	private String baseCode ;  //Base64码
}
