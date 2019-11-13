package com.uranus.platform.business.pub.entity.dto;

import java.util.List;

import lombok.Data;

/**
 * 
* @ClassName:：TransferPlansDto_1 
* @Description： 扣款计划上送明细列表
* @author ：chenwendong
* @date ：2019年8月5日 下午6:38:38 
*
 */

@Data
public class RepayPlanList {

	private String pactNo;     //合同号
	private String serialNo;   //扣款流水号
	private String acNo;       //扣款账号
	private String cardChn;    //扣款渠道
	private String repayType;  //扣款类型
	private Double repayAmt;   //扣款总金额
	private String apprId;     //提前还款申请编号
	private List<RepayPlanListSubj>  listSubj; 
	
}
