package com.uranus.platform.business.pub.entity.dto;

import java.util.List;

import lombok.Data;

/**
 * 
* @ClassName:：PaymentOrderDto 
* @Description： 支付订单
* @author ：chenwendong
* @date ：2019年8月6日 上午9:11:10 
*
 */

@Data
public class PaymentOrderDto {

	private String  brNo;           	 //机构号
	private String batNo;            	 //批次号
	private String bankNo;				 //银行流水号
	private Integer dataCnt;
	private List<PaymentOrderDto_1>  paymentOrderDto_1;  //明细列表
}
