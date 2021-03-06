package com.uranus.platform.business.jd.entity.po;

import lombok.Data;

@Data
public class JdLoanApplyInfoData {
    private String jdId;

    private String applicationNo;

    private String brNo;

    private String projNo;

    private String prdtNo;

    private String contractNo;

    private String channelProdNo;

    private String applicationPlace;

    private String application;

    private Double contractAmount;

    private Double amountPayed;

    private String currencyType;

    private String periodType;

    private Integer expiresMonth;

    private String refundMethod;

    private String chargeDateType;

    private String chargeDateType2;

    private String chargeDate;

    private String branchOffice;

    private String paymentWay;

    private String loanType;

    private String borrowerType;

    private Double handlingCharge;

    private Double handlingChargeRate;

    private String primaryRateType;

    private Double primaryRate;

    private String isForward;

    private Double penaltyRate;

    private Double monthlyPenalty;

    private Double serviceCharge;

    private Double serviceChargeRate;

    private Double earnestMoney;

    private Double deposit;

    private String expireDate;

    private Double downPayment;

    private Double monthlyPayment;

    private String signedCity;

    private String securedAccount;

    private String auditResult;

    private String auditRejectReason;

    private String auditDate;

    private String createDate;

    private String createTime;
 
    private String batchNo;
    
    public JdLoanApplyInfoData() {
		super();
	}
    
    public JdLoanApplyInfoData(String jdId, String batchNo) {
		super();
		this.jdId = jdId;
		this.batchNo = batchNo;
	} 
    public JdLoanApplyInfoData(String jdId, String auditResult, String auditDate) {
		super();
		this.jdId = jdId;
		this.auditResult = auditResult;
		this.auditDate = auditDate;
	}
    public JdLoanApplyInfoData(String jdId, String auditResult, String auditDate, String auditRejectReason) {
		super();
		this.jdId = jdId;
		this.auditResult = auditResult;
		this.auditDate = auditDate;
		this.auditRejectReason = auditRejectReason;
	}
    
}