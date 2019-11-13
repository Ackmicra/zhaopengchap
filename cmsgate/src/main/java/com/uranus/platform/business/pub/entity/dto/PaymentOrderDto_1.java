package com.uranus.platform.business.pub.entity.dto;

import java.util.List;

import lombok.Data;

/**
 * 
* @ClassName:：PaymentOrderDto_1 
* @Description： 支付订单明细
* @author ：chenwendong
* @date ：2019年8月6日 上午9:15:54 
*
 */

@Data
public class PaymentOrderDto_1 {

	private String pactNo;								//合同号
	private String repayType;							//实收类型
	private Double repayAmt;							//实收金额
	private List<PaymentOrderDto_2> paymentOrderDto_2;	//明细列表
}
