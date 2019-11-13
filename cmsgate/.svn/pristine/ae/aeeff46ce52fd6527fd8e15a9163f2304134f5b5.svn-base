package com.uranus.platform.business.jd.entity.pojo;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;

/**
 * 
* @ClassName:：JdLoanChangeRepayPlanFee 
* @Description： 还款计划费用更新
* @author ：chenwendong
* @date ：2019年8月5日 上午11:01:07 
*
 */
@Data
@ConvertBean
public class JdLoanChangeRepayPlanFee {

	@NotNull
	private String applicationNo;  //申请号
	@NotNull
	private Integer issue;             //期次
	@NotNull
	private String changeType;     //更新类型
	@NotNull
	private String changeReason;   //更新原因
	@NotNull
	List<JdRepayPlanFeeDetail> repayPlanFeeDetails;   //还款计划费用明细
	
}
