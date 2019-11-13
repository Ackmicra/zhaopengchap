package com.uranus.platform.business.pub.entity.dto;

import lombok.Data;

/**
 * 
* @ClassName:：AcLnRepayDto_1 
* @Description： 还款计划详细
* @author ：chenwendong
* @date ：2019年8月5日 下午5:54:43 
*
 */
@Data
public class AcLnRepayPlanDto {
	
	private String pactNo;				//合同号
	private Integer cnt;					//期次
	private String endDate;				//账单日
	private Double totalAmt;			//期供金额
	private Double prcpAmt;			//当期本金
	private Double normInt;				//当期利息
}
