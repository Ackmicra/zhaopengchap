package com.uranus.platform.business.jd.entity.pojo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;
/**
 * 
* @ClassName:：JdRepayPlan
* @Description： 还款计划
* @author ：chenwendong
* @date ：2019年8月5日 上午10:38:32 
*
 */
@Data
@ConvertBean
public class JdRepayPlan {

	@Min(value = 1)
	private Integer issue;     //还款期次
	@Min(value = 1)
	private long startDate;  //开始日期
	@Min(value = 1)
	private long startRateDate;  //起息日
	@Min(value = 1)
	private long refundDate;    //应还款日期
	@Min(value = 1)
	private long endDate;     //结束日期
	@Min(value = 0)
	private Double refundPrincipal;   //应还本金
	@Min(value = 0)
	private Double refundInterest;    //应还利息
	@Min(value = 0)
	private Double surplusPrincipal;  //剩余本金
	@Min(value = 0)
	private Double refundServiceCharge;   //应还服务费
}
