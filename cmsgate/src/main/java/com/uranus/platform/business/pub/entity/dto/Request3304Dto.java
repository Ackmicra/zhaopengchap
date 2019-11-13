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
public class Request3304Dto {

	private Integer dataCnt;    //记录数
	private String batchNo;      //批次号
	List<RepurchasePaymentOrder> list; //扣款计划上送明细列表
}
