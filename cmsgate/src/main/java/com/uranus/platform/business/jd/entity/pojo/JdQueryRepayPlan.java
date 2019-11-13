package com.uranus.platform.business.jd.entity.pojo;

import javax.validation.constraints.NotBlank;

import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;

/**
 * 
* @ClassName:：JdRequest2202 
* @Description： 还款计划查询
* @author ：chenwendong
* @date ：2019年8月13日 上午11:01:17 
*
 */
@Data
@ConvertBean
public class JdQueryRepayPlan {

	@NotBlank
	private String applicationNo; //申请号
}
