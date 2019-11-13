package com.uranus.platform.business.jd.entity.pojo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class JDPaymentOrderRequest {

	@NotBlank
	private String transferType; // 扣款类型1002-委托扣款；1003-代偿；1004-回购
	@NotBlank
	private String batchNumber; // 批次号  扣款类型为委托转付时必填，并且必须等于之前已上送的扣款计划批次
}
