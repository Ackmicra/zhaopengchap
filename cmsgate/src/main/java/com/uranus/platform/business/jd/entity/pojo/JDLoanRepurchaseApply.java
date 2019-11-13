package com.uranus.platform.business.jd.entity.pojo;

import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;

/**
 * 
* @ClassName:：JDLoanRepurchaseApply 
* @Description：回购
* @author ：chenwendong
* @date ：2019年8月20日 下午1:49:29 
*
 */
@Data
@ConvertBean
public class JDLoanRepurchaseApply {

	private String applicationNo; // 贷款单申请号
	private String channelBuybackApplyNo; // 回购单号
	private String repurchaseType; // 回购类型
	private String principalAmount; // 回购本金
	private String interestAmount; // 回购利息
	private String penaltyRate; // 回购罚息
	private String otherFeeAmount; // 回购其他费用
}
