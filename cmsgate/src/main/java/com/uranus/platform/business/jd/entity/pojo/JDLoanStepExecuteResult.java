package com.uranus.platform.business.jd.entity.pojo;

import lombok.Data;

/**
 * @Describe JD审核结果回调接口响应数据放款步骤
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月23日 下午3:45:20
 * 
 */
@Data
public class JDLoanStepExecuteResult {

	private String  applicationNo;    //申请号
	private String  paymentNo;        //放款步骤序号
	private Double  paymentAmount;    //本步骤放款额
	private String  paymentStatus;    //放款状态
	private String  paymentTime;      //放款时间
	private String  paymentTradeNo;   //支付流水
	private String  message;          //申请号
}
