package com.uranus.platform.business.pub.entity.dto;

import lombok.Data;

/**
 * @Describe (押品信息)
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月7日 下午5:07:15
 * 
 */
@Data
public class LnGageDto {
	//新增（2101）
	private   String             gAgeBrId            ;         //   合作机构押品编号   
	
	
	
	private   String             gcustName           ;         //   押品所有权人名称
    private   String             gcustIdtype         ;         //   押品所有权人证件
    private   String             gcustIdno           ;         //   押品所有权人证件
    private   String             gType               ;         //   押品类型       
    private   String             gName               ;         //   押品名称       
	private   String             gDesc               ;         //   押品描述     
	private   Double            gValue              ;         //   押品评估价值   
	private   String             gLicno              ;         //   权证号码       
	private   String             gLicType            ;         //   权证类型       
	private   Double            gBegBal             ;         //   前手抵押余额   
	private   String             gSmType             ;         //   押品小类       
	private   String             gWorkType           ;         //   所有权人职业 
	private   String             gArea               ;         //   抵押物归属区域 
	private   Double            gSpace              ;         //   押品面积     
	private   String             gageAddr            ;         //   押品地址     
	private   Integer            gageProp            ;         //   押品产权     
	private   String             Position            ;         //   抵押顺位       
	private   Double            gageRate            ;         //   抵押率         
	private   Integer            gYearCom            ;         //   建成年份     
	private   String             gAreaType           ;         //   押品区域类型 
	private   String             gstats              ;         //   押品状态     

}
