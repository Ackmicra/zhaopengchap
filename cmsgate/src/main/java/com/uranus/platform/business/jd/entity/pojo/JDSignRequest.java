package com.uranus.platform.business.jd.entity.pojo;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;
import com.uranus.platform.business.jd.entity.po.JdSigningData;
import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;

/**@Describe  JD签约申请接口实体类
 * 
 * @author wangshuai0106@dhcc.com.cn
 *
 */

@Data
@ConvertBean(convertClass = JdSigningData.class)
public class JDSignRequest {
    @NotBlank
	private String channelProdNo;         //项目编号
    @NotBlank
	private String payeeAccountName;       //姓名
    @NotBlank
	private String payeeCeritificateType; //证件类型
    @NotBlank
	private String payeeCeritificateNo;   //证件号码
    @NotBlank
	private String payeeMobileNo;         //移动电话
    @NotBlank
	private String payeeAccountNo;        //账户号码
    @NotBlank
	private String payeeBankCode;         //银行编号
    @NotNull
	private Integer paymentChannel;       //支付通道

}
