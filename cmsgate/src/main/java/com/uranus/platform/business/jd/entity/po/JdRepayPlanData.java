package com.uranus.platform.business.jd.entity.po;

import lombok.Data;

@Data
public class JdRepayPlanData {
    private String jdId;

    private String applicationNo;

    private Integer issue;

    private Long startDate;

    private Long startRateDate;

    private Long refundDate;

    private Long endDate;

    private Double refundPrincipal;

    private Double refundInterest;

    private Double surplusPrincipal;

    private Double refundServiceCharge;

    private Double interestPenalty;

    private Double overdueService;

    private String upType;

    private String changeType;

    private String changeReason;

    private String type;

    private String createDate;

    private String createTime;

    private String upDate;

    private String upTime;

	

   
}