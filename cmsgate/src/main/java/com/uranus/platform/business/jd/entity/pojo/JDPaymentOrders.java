package com.uranus.platform.business.jd.entity.pojo;

import lombok.Data;

@Data
public class JDPaymentOrders {

	private String paymentNo; // 商户支付编号
	private String tradeNo; // 付款流水号 委托转付的线下打款时必须填写，资金方使用这个编号完成资金核对和还款计划核销
	private String paymentStatus; // 支付状态 支付状态 00 初始化, 01-支付成功, 02-支付失败
	private Double amount; // 交易金额   转付金额
	private String paymentTime; // 支付时间  yyyy-MM-dd HH:mm:ss
}
