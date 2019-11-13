package com.uranus.platform.business.jd.entity.pojo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;
import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;
/**@Describe  JD贷款申请信息实体类
 * 
 * @author wangshuai0106@dhcc.com.cn
 *
 */
@Data
@ConvertBean
public class JDLoanApply {
	  private String      contractNo       ;        //    合同号              
	  @NotBlank
	  private String      channelProdNo    ;        //    项目编号            
	  @NotBlank
	  private String      applicationPlace ;        //    申请地点            
	  @NotBlank
	  private String      applicationNo    ;        //    申请号              
	  @NotBlank
	  private String      application      ;        //    申请用途            
	  @NotNull
	  private Double      contractAmount   ;        //    合同金额            
	  @NotNull
	  private Double      amountPayed      ;        //    实付金额            
	  @NotBlank
	  private String      currencyType     ;        //  申请币种              
	  @NotBlank
	  private String      periodType       ;        //  期限类型              
	  @NotNull
	  private Integer     expiresMonth     ;        //  申请期限              
	  @NotBlank
	  private String      refundMethod     ;        //  还款方式              
	  @NotBlank
	  private String      chargeDateType   ;        //  扣款日类型            
	  @NotBlank
	  private String      chargeDateType2  ;        //  扣款日类别            
	  @NotBlank
	  private String      chargeDate         ;      //  扣款日期              
	  @NotBlank
	  private String      branchOffice       ;      //  贷款受理分支机构      
	  @NotBlank
	  private String      paymentWay         ;      //  缴费方式              
	  @NotBlank
	  private String      loanType           ;      //  贷款类型              
	  @NotBlank
	  private String      borrowerType       ;      //  借款方类型            
	  @NotNull
	  private Double      handlingCharge     ;      //  手续费                
	  @NotNull
	  private Double      handlingChargeRate ;      //  手续费率              
	  @NotBlank
	  private String      primaryRateType    ;      //  基础利率类型          
	  @NotNull
	  private Double      primaryRate        ;      //  基础利率              
	  @NotBlank
	  private String      isForward          ;      //  是否允许提前还款      
	  @NotNull
	  private Double      penaltyRate        ;      //  提前还款违约金比率    
	  @NotNull
	  private Double      monthlyPenalty     ;      //  罚息率(日)            
	  @NotNull
	  private Double      serviceCharge      ;      //  服务费                
	  @NotNull
	  private Double      serviceChargeRate  ;      //  服务费率              
	  @NotNull
	  private Double      earnestMoney       ;      //  定金    
	  private Double      deposit            ;      //  保证金                
	  @NotBlank
	  private String      expireDate         ;      //  贷款结束日期          
	  private Double      downPayment        ;      //  首期还款金额          
	  private Double      monthlyPayment     ;      //  每月还款额            
	  private String      signedCity         ;      //  合同签署城市          
	  private String      securedAccount     ;      //  担保账户       
//	  @NotEmpty
//	  @Valid
//	  private List<JDLoanUser> loanUser ;       //个人用户信息
//	  @NotEmpty
//	  @Valid
//	  private List<JDRelationUser> relationUsers ;       //关系人信息(多个)
////	  @NotEmpty
////	  @Valid
////	  private List<JDLoanEnterpriseView> loanEnterprise;   //企业信息
//	  @NotEmpty
//	  @Valid
//	  private List<JDAccount> loanAccount ;       //放款账户信息
//	  @NotEmpty
//	  @Valid
//	  private List<JDAccount> repayAccount ;       //还款账户信息
//	  @NotEmpty
//	  @Valid
//	  private List<JDLoanPaymentSteps> loanPaymentSteps ;       //放款步骤

	  
	  
	                                                                          

}
