package com.uranus.platform.business.jd.entity.pojo;

import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;

/**@Describe  JD贷款申请企业信息实体类
 * 
 * @author wangshuai0106@dhcc.com.cn
 *
 */

@Data
@ConvertBean
public class JDLoanEnterprise {
	
	 private String      registrationNo         ;        //    组织机构代码    
	 private String      businessLicense        ;        //    登记注册号      
	 private String      taxRegistration        ;        //    税务登记号      
	 private String      taxRegistrationType    ;        //    税务登记证号类型
	 private String      creditCode             ;        //    中征码          
	 private String      name                   ;        //    企业名称        
	 private String      enterpriseKind         ;        //    企业特征        
	 private String      enterpriseNature       ;        //    企业性质        
	 private String      industryType           ;        //    行业分类        
	 private String      industryDetail         ;        //    明细分类        
	 private String      enterpriseType         ;        //    企业类型        
	 private String      provinceCode           ;        //    企业所在省份编号
	 private String      cityCode               ;        //    企业所在城市编号
	 private String      investorsElements      ;   	    //    企业出资人经济成
	 private String      organizationDetail     ;   	    //    机构细分        
	 private String      registrationKind       ;   	    //    注册类型        
	 private String      economType             ;        //    经济类型        
	 private Double  registeredCapital      ;        //    注册资本        
	 private String        licenseStartDate       ;        //    登记开始日期    
	 private String        licenseEndDate         ;        //    登记到期日期    
	 private String      location               ;        //    地址            
	 private String      legalRepresentative    ;        //    法定代表人      
	 private String      certificateType        ;        //    证件类型        
	 private String      certificateNo          ;        //    证件号码        
	 private String      telephone              ;        //    联系电话        
	 private Double  totalAssets            ;        //    企业总资产      
	 private Double  totalDebt              ;        //    企业总负债      
	 private String      isListend              ;        //    是否上市        
	 private String      isGroup                ;        //    是否集团        
	 private String      relationType           ;        //    关联类型        
	 private String      importAndExportFlag    ;        //    进出口权标识    
	 private String      financePhone           ;        //    财务部电话      
	 private String      enterprisePhone        ;        //    企业联系电话    
	 private String      enterpriseZipCode      ;        //    企业邮编        
	 private String      creditLevel            ;        //    信用评级        
	 private String      creditScore            ;        //    信用评分        
	 private String      customerNo             ;        //    客户号          
	 private Double  monthlyStockAmount     ;        //    月均库存额      
	 private Double  monthlyFlowAmount      ;        //    月均流水        
	 private Double  monthlySaleIncome      ;        //    月均销售收入    
	 private Double  debtInMonthlyStockRatio;        //    负债额占比月均库


}
