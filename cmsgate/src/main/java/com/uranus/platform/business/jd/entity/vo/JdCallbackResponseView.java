package com.uranus.platform.business.jd.entity.vo;

import lombok.Data;

/**
 * @Description: 京东回调服务响应
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年8月20日下午2:03:06
 */
@Data
public class JdCallbackResponseView {

	private String code;//业务返回码
	private String msg;//业务返回描述
}
