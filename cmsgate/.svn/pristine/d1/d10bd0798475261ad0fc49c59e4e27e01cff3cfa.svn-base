package com.uranus.platform.business.jd.entity.pojo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;

/**
 * 
* @ClassName:：JdLoanChangeRepayPlan
* @Description： 还款计划变更实体类
* @author ：chenwendong
* @date ：2019年8月5日 上午10:49:18 
*
 */
@Data
@ConvertBean
public class JdLoanChangeRepayPlan {

	@NotBlank
	private String applicationNo; //申请号
	@NotBlank
	private String changeReason; //还款计划变更原因
	@Valid
	List<JdRepayPlan> repayPlans;   //还款计划列表信息
}
