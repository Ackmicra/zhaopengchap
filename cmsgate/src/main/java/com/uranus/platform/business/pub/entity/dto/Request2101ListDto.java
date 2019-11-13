package com.uranus.platform.business.pub.entity.dto;

import java.util.List;

import lombok.Data;

/**
 * @Describe 小微2101接口报文数据DtoList
 * @author   wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月14日 下午7:47:53
 * 
 */
@Data
public class Request2101ListDto {

	    private  String               pactNo               ;           //      合同号码            
		private  String            custName             ;           //      客户名称                
		private  String            idType               ;           //      证件类型                
		private  String            idNo                 ;           //      证件号码                
		private  String            idPreDate            ;           //      证件起始日期            
		private  String            idEndDate            ;           //      证件截止日期            
		private  String            trade                ;           //      行业                    
		private  String            custType             ;           //      客户类型                
		private  String            sex                  ;           //      性别                    
		private  String            birth                ;           //      出生日期                
		private  String            marriage             ;           //      婚姻状况                
		private  String            children             ;           //      是否有子女              
		private  String            edu                  ;           //      最高学历                
		private  String            degree               ;           //      最高学位                
		private  String            telNo                ;           //      联系电话                
		private  String            phoneNo              ;           //      手机号码                
		private  String            postCode             ;           //      通讯邮编                
		private  String            postAddr             ;           //      通讯地址                
		private  String            homeArea             ;           //      户籍归属地              
		private  String            homeTel              ;           //      住宅电话                
		private  String            homeCode             ;           //      住宅邮编                
		private  String            homeAddr             ;           //      住宅地址                
		private  String            homeSts              ;           //      居住状况    
		
		//与2001Dto不同
		private  Double             income              ;           //      月收入        
		
		
		
		private  String            mateName             ;           //      配偶名称                
		private  String            mateIdtype           ;           //      配偶证件类型            
		private  String            mateIdno             ;           //      配偶证件号码            
		private  String            mateWork             ;           //      配偶工作单位            
		private  String            mateTel              ;           //      配偶联系电话            
		private  String            projNo               ;           //      信托项目编号            
		private  String            prdtNo               ;           //      产品号                  
		private  Double            pactAmt              ;           //      合同金额  
		
		//比2001Dto新增
		private  String            feeTotal             ;            //   趸交费总额
		private  String            premNo               ;            //   保单编号
		private  String            premRate             ;            //   保费费率
		//比较2001dto新增
		private  String           vouAmt               ;           //      期缴（保）费金额
		//比较2001dto新增
		private  String            prePactNo            ;           //      预审批ID
		private  String            sigDate              ;             //     合同签订日期
		private  String            cardChn              ;             //     虚拟账户渠道
		//必填新增字段（以上为非必填）
		private  String            factPactNo           ;             //     实际贷款合同号		
				
				
		private  Double            lnRate               ;           //      利率（月）              
		private  String            appArea              ;           //      申请地点                
		private  String            appUse               ;           //      申请用途                
		private  Integer           termMon              ;           //      合同期限（月）          
		private  Integer           termDay              ;           //      合同期限（日）          
		private  String            vouType              ;           //      担保方式                
		private  String            endDate              ;           //      到期日期                
		private  String            payType              ;           //      扣款日类型              
		private  Integer           payDay               ;           //      扣款日期    
		private  String            homeIncome           ;           //      家庭年收入                 
		private  String            repaySource          ;           //      还款来源                
		private  String            workType             ;           //      职业                    
		private  String            workName             ;           //      工作单位名称            
		private  String            workSts              ;           //      工作状态                
		private  String            workWay              ;           //      工作单位所属行业        
		private  String            workCode             ;           //      工作单位邮编            
		private  String            workAddr             ;           //      工作单位地址     
		private  String            workDuty             ;           //      职务                    
		private  String            workTitle            ;           //      职称                    
		private  String            workYear             ;           //      工作起始年份            
		private  String            ifCar                ;           //      是否有车                
		private  String            ifCarCred            ;           //      是否有按揭车贷          
		private  String            ifRoom               ;           //      是否有房                
		private  String            ifMort               ;           //      是否有按揭房贷          
		private  String            ifCard               ;           //      是否有贷记卡            
		private  String            cardAmt              ;           //      贷记卡最低额度          
		private  String            ifApp                ;           //      是否填写申请表          
		private  String            ifId                 ;           //      是否有身份证信息        
		private  String            ifPact               ;           //      是否以签订合同     
		private  String            czPactNo             ;           //      查证流水号   
		private  String            ifLaunder            ;           //      是否具有洗钱风险        
		private  String            Launder              ;           //      反洗钱风险关联度        
		private  String            sales                ;           //      销售渠道                
		private  String            ifAgent              ;           //      是否有代理人            
		private  String            country              ;           //      国籍                    
		private  String            profession           ;           //      职业                    
	    private  List<LnGageDto>    listGage        ;           //      押品信息                
	    private  List<LnComDto>     listCom         ;           //      共同借款人              
	    private  List<LnRelDto>     listRel         ;           //      借款关联人         
	    private  List<LnAcDto>      listAc         ;            //       账户信息

	    //工具数值
	    private  String             RateType        ;           //      利率类型
	    private  String             periodType      ;           //      期限类型
	    
	    
	    
}
