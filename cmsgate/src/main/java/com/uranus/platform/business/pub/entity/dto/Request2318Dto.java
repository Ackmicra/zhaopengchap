package com.uranus.platform.business.pub.entity.dto;

import java.util.List;

import lombok.Data;

/**
 * 
* @ClassName:：TransferPlansDto 
* @Description： 扣款计划上送
* @author ：chenwendong
* @date ：2019年8月5日 下午6:20:10 
*
 */
@Data
public class Request2318Dto {

	private String brNo;       //机构号
	private Integer dataCnt;    //记录数
	private String batNo;      //批次号
	private String bankNo;		//银行流水号
	List<TransferPlanList> list; //扣款计划上送明细列表
}
