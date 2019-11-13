package com.uranus.platform.business.jd.entity.pojo;

import java.util.List;

import lombok.Data;

@Data
public class JDPaymentOrderResponse {

	private List<String> errorMessages;// 错误信息
	private String transferType; // 扣款类型1002-委托扣款；1003-代偿；1004-回购
	private String batchNumber;// 批次号
	private List<JDPaymentOrders> paymentOrders; // 支付单信息
}
