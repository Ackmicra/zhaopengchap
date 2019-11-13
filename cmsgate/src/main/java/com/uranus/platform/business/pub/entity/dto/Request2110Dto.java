package com.uranus.platform.business.pub.entity.dto;

import lombok.Data;

/**
 * @Description: 小微2110请求实体
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年9月18日下午3:24:42
 */
@Data
public class Request2110Dto {

	private String brNo; // 机构号
	private String transNo; // 业务主键
	private String docType; //文档类型
}
