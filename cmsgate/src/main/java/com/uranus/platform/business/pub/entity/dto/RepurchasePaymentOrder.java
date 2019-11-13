package com.uranus.platform.business.pub.entity.dto;

import lombok.Data;

@Data
public class RepurchasePaymentOrder {

	private String pactNo; // 合同号
	private String brNo; // 合作机构号
	private String projNo; // 信托项目号
	private String repDate; // 触发债转日期
	private double repAmt; // 债转金额
	private double repPrcp; // 债转本金
	private double repInte; // 债转利息
	private double repFine; // 债转罚息
}
