package com.uranus.platform.business.pub.entity.dto;

import lombok.Data;

/**
 * 
* @ClassName:：TransferPlanResultQueryDto 
* @Description： 扣款计划处理结果查询
* @author ：chenwendong
* @date ：2019年8月5日 下午7:59:38 
*
 */
@Data
public class TransferPlanResultQueryDto {

	private String brNo;				//机构号
	private String batNo;				//批次号
	private String onLine;				//线上标识
	private String pactNo;				//合同号
	private Integer pageNo;				//页码
	private Integer pageSize;			//每页条数
}
