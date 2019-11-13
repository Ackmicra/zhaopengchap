package com.uranus.platform.business.jd.entity.pojo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;
/**
 * 
* @ClassName:：JdTransferPlan 
* @Description： 扣款计划
* @author ：chenwendong
* @date ：2019年8月15日 上午11:52:50 
*
 */
@Data
@ConvertBean
public class JdTransferPlan {

	@NotBlank
	private String applicationNo;                   //申请号
	@NotBlank
	private String transferPlanNo;                  //扣款计划ID
	@Min(value = 1)
	private Integer currentIssue;                       //本期期次
	@Min(value = 1)
	private Long refundDate;                        //应还日期
	@Min(value = 1)
	private Long chargeDate;                        //应发起扣款日期
	@Min(value = 0)
	private Double chargeAmount;                //应扣金额
	@Min(value = 0)
	private Double chargePrincipal;             //应扣本金
	@Min(value = 0)
	private Double chargeRate;                  //应扣利息
	@Min(value = 0)
	private Double penaltyRate;                 //应扣罚息
	@Min(value = 0)
	private Double penaltyAmount;               //应扣违约金
	@Min(value = 0)
	private Double chargeMoney;                 //应扣手续费
	@Min(value = 0)
	private Double refundSecureCharge;          //应扣担保费
	@Min(value = 0)
	private Double refundServiceCharge;         //应扣服务费
	@NotBlank
	private String isPayOff;                       //本期还清
	@NotBlank
	private String settledEarly;                   //是否提前结清
	
}
