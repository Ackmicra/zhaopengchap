package com.uranus.platform.business.jd.entity.pojo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;
/**
 * 
* @ClassName:：JdLoanTransferPlans
* @Description： 扣款计划上送
* @author ：chenwendong
* @date ：2019年8月5日 下午1:52:40 
*
 */
@Data
@ConvertBean
public class JdLoanTransferPlan {

	@NotBlank
	private String batchNumber;                     //批次号
	@NotBlank
	private String transferType;                    //扣款类型
	@NotBlank
	private String paymentAgentType;                //支付受托方类型
	@Valid
	private List<JdTransferPlan> transferPlans;    //扣款计划明细
	
}
