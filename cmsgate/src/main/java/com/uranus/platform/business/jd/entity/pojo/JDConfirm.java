package com.uranus.platform.business.jd.entity.pojo;

import javax.validation.constraints.NotBlank;

import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;

/**@Describe  JD签约绑定接口实体类
 * 
 * @author wangshuai0106@dhcc.com.cn
 *
 */

@Data
@ConvertBean
public class JDConfirm {
	 @NotBlank
	 private String channelProdNo;            //项目编号     
	 @NotBlank
	 private String signTransactionNo;        //签约流水号 
	 @NotBlank
	 private String verificationCode;         //短信验证码      
}
