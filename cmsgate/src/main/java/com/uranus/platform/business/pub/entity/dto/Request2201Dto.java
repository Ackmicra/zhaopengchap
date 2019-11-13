package com.uranus.platform.business.pub.entity.dto;

import java.util.List;

import lombok.Data;
/**
 * 
* @ClassName:：AcLnRepayDto 
* @Description： 还款计划
* @author ：chenwendong
* @date ：2019年8月5日 下午5:44:26 
*
 */
@Data
public class Request2201Dto {

	private String brNo;  //机构号
	private Integer dataCnt; //记录数
	List<AcLnRepayPlanDto> list;  //还款计划详细
	
}
