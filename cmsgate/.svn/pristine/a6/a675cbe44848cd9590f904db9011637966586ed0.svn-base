package com.uranus.platform.business.jd.entity.pojo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;
/**
 * 
* @ClassName:：JdLoanSyncRepayPlan 
* @Description： 还款计划同步
* @author ：chenwendong
* @date ：2019年8月5日 上午10:37:44 
*
 */
@Data
@ConvertBean
public class JdLoanSyncRepayPlan {

	@NotBlank
	private String applicationNo;          //申请号 
	@NotEmpty
	@Valid
	List<JdRepayPlan> repayPlans;  //还款计划信息列表
}
