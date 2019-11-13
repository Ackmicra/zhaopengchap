package com.uranus.platform.business.jd.entity.pojo;

import java.util.List;

import lombok.Data;

/**
 * 
* @ClassName:：JdResponse2313 
* @Description： 京东返回2313
* @author ：chenwendong
* @date ：2019年8月22日 下午6:33:42 
*
 */
@Data
public class JdResponse2313 {

	private String batchNumber;
	private String batchNum;
	private String validateStatus;
	private String validateMsg;
	private Long chargeDate;
	private Double chargeAmount;
	private String dealStatus;
	private String message;
	private List<JdPayments> payments;
}
