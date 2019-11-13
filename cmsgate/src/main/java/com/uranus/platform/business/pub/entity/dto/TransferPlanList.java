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
public class TransferPlanList {

	private String pactNo;     //合同号
	private String repayType;  //实收类型
	private Double repayAmt;   //实收金额
	private List<TransferPlanListSubj>  listSubj; 
	
}
