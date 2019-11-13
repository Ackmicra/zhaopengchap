package com.uranus.platform.business.pub.entity.dto;

import lombok.Data;

/**
 * 
* @ClassName:：AcLnRepayPlanQueryDto_1 
* @Description： 还款计划查询明细
* @author ：chenwendong
* @date ：2019年8月5日 下午6:04:55 
*
 */
@Data
public class AcLnRepayPlanQueryDto_1 {

	private String  pactNo;           // 合同号          
	private Integer  cnt;              // 期次          
	private String  endDate;          // 账单日      
	private Double  totalAmt;         // 期供金额      
	private Double  prcpAmt;          // 当期本金       
	private Double  normInt;          // 当期利息       
	private Double  fineInt;          // 罚息           
	private Double  repayPrcpAmt;     // 已还本金  
	private Double  repayNormInt;     // 已还利息  
	private Double  repayFineInt;     // 已还罚息  
	private Double  wvPrcpAmt;        // 减免本金     
	private Double  wvNormInt;        // 减免利息     
	private Double  wvFineInt;        // 减免罚息     
	private String  sts;              // 本期状态        
	private String  prcpSts;          // 本金状态       

}
