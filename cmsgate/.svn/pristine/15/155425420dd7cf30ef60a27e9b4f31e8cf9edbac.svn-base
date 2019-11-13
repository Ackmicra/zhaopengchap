package com.uranus.platform.business.jd.entity.vo;

import lombok.Data;

/**
 * @Description: 京东回调服务请求
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年8月20日下午2:03:22
 */
@Data
public class JdCallbackRequestView {
	
	private String channelId;//合作机构编号
	private String channelProdNo;//项目编号
	private String outTradeNo;//交易流水号
	private String messageType;//回传业务类型
	private String timestamp;//回传时间
	private String bizContent;//业务数据
	public JdCallbackRequestView() {
		super();
	}
	public JdCallbackRequestView(String channelId, String channelProdNo, String outTradeNo, String messageType, String timestamp,
			String bizContent) {
		super();
		this.channelId = channelId;
		this.channelProdNo = channelProdNo;
		this.outTradeNo = outTradeNo;
		this.messageType =  messageType;
		this.timestamp = timestamp;
		this.bizContent =  bizContent;
	}
	public JdCallbackRequestView(String bizContent) {
		super();
		this.bizContent = bizContent;
	}
	
}
