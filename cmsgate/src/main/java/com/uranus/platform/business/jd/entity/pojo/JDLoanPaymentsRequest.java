package com.uranus.platform.business.jd.entity.pojo;

import javax.validation.constraints.NotBlank;

import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;

/**
 * @Describe 京东放款指令接口请求数据
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月14日 上午11:27:55
 * 
 */
@Data
@ConvertBean
public class JDLoanPaymentsRequest {
	@NotBlank
	private String applicationNo; //申请号
	
	private String policyNo;      //贷款投保单号
	

}
