package com.uranus.platform.business.jd.entity.pojo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;

/**
 * 
* @ClassName:：JdPaymentOrder 
* @Description： 支付单信息
* @author ：chenwendong
* @date ：2019年8月5日 下午2:55:23 
*
 */
@Data
@ConvertBean
public class JdPaymentOrder {

	private String paymentNo;                 //商户支付编号
	@NotNull
	private String tradeNo;                   //付款流水号  
	@NotBlank
	private String tradeTime;                   //交易时间    
	private String paymentChannel;            //支付通道    
	@Min(value= 0)
	private Double amount;                //交易金额    
	@NotBlank
	private String payerAccountNo;            //付款账户号码
	@NotBlank
	private String payerAccountName;          //付款账户名称
	@NotBlank
	private String payerBankCode;             //付款开户行银行编号
	private String payeeAccountNo;            //收款方账户编
	private String payeeAccountName;          //收款方账户名
	private String payeeBankCode;             //收款方开户行

}
