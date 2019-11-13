package com.uranus.platform.business.jd.entity.pojo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;
import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;

/**
 * @Describe JD贷款申请实体类
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月28日 下午5:52:13
 * 
 */
@Data
@ConvertBean
public class JDLoanApplyInfo {
	  @NotNull 
	  private JDLoanApply  loanApply  ;   // 贷款单信息             
	  
	  private JDLoanUser loanUser ;       //个人用户信息
	  @Valid
	  private List<JDRelationUser> relationUsers ;       //关系人信息(多个)
//	  @NotEmpty
//	  @Valid
//	  private List<JDLoanEnterpriseView> loanEnterprise;   //企业信息
	  @NotNull 
	  private JDAccount loanAccount ;       //放款账户信息
	  @NotNull 
	  private JDAccount repayAccount ;       //还款账户信息
	  @NotEmpty
	  @Valid
	  private List<JDLoanPaymentSteps> loanPaymentSteps ;       //放款步骤

}
