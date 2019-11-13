package com.uranus.platform.business.pub.entity.dto;

import lombok.Data;

/**
 * 
* @ClassName:：PaymentOrderDto_2 
* @Description： 支付订单明细2
* @author ：chenwendong
* @date ：2019年8月6日 上午9:18:58 
*
 */

@Data
public class PaymentOrderDto_2 {

	private Integer cnt;				//记录数
	private String subjType;		//科目类型
	private Double subjAmt;			//科目金额
}
