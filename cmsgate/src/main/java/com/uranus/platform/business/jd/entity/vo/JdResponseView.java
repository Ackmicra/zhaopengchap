package com.uranus.platform.business.jd.entity.vo;

import lombok.Data;

/**
 * @Describe 
 * @author  wangshuai
 * @Date 创建时间：2019年8月6日 下午8:00:23
 * 
 */
@Data
public class JdResponseView {
	private String code;
	private String msg;
	private String tradeNo;
	private String bizContent;
	
	public JdResponseView(String code, String msg, String tradeNo, String bizContent) {
		super();
		this.code = code;
		this.msg = msg;
		this.tradeNo = tradeNo;
		this.bizContent =  bizContent;
	}

	public JdResponseView() {
		super();
	}
}
