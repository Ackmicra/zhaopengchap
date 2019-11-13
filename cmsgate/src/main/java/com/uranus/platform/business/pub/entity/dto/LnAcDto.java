package com.uranus.platform.business.pub.entity.dto;

import lombok.Data;

/**
 * @Describe 账户信息（包括借款还款）
 * @author   wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月14日 下午8:42:45
 * 
 */
@Data
public class LnAcDto {
	//新增
	private  String				acUse        ;     //       账户用途        
	private  Double				acAmt        ;     //       放款金额        
	private  String				validDate    ;     //       信用卡有效期 
    private  String				bankSite     ;     //       开户银行网点    
    private  String				cvvNo        ;     //       信用卡CVV2码    
    
	//accountType
	private  String				acType       ;     //       账户类型        
	//accountNo
	private  String				acno         ;     //       账户号          
	//accountName
	private  String				acName       ;     //       账户户名        
	
	private  String				bankCode     ;     //       银行代码        
	//bankBranchProvince
	private  String				bankProv     ;     //       账户开户省      
	//bankBranchCity
	private  String				bankCity     ;     //       账户开户市      
	
	
	
	//holderIdType
	private  String				idType       ;     //       证件类型        
	//holderIdNo
	private  String				idNo         ;     //       证件号码        
	//holderMobileNo
	private  String				phoneNo      ;     //       银行预留手机号  
	
	

}
