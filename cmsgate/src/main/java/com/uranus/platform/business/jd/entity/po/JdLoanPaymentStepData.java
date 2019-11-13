package com.uranus.platform.business.jd.entity.po;

import lombok.Data;

@Data
public class JdLoanPaymentStepData {
    private String jdId;

    private String applicationNo;

    private String paymentNo;

    private Double paymentAmount;

    private String accountName;

    private String accountNo;

    private String bankCode;

    private String holderMobileNo;

    private String holderIdType;

    private String holderIdNo;

    private String createDate;

    private String createTime;

    private String loanSts;
    
    private String upDate;
    
    private String upTime;

	public JdLoanPaymentStepData() {
		super();
	}

	public JdLoanPaymentStepData(String jdId, String applicationNo, String paymentNo, Double paymentAmount,
			String accountName, String accountNo, String bankCode, String holderMobileNo, String holderIdType,
			String holderIdNo, String createDate, String createTime, String loanSts, String upDate, String upTime) {
		super();
		this.jdId = jdId;
		this.applicationNo = applicationNo;
		this.paymentNo = paymentNo;
		this.paymentAmount = paymentAmount;
		this.accountName = accountName;
		this.accountNo = accountNo;
		this.bankCode = bankCode;
		this.holderMobileNo = holderMobileNo;
		this.holderIdType = holderIdType;
		this.holderIdNo = holderIdNo;
		this.createDate = createDate;
		this.createTime = createTime;
		this.loanSts = loanSts;
		this.upDate = upDate;
		this.upTime = upTime;
	}

	public JdLoanPaymentStepData(String applicationNo, String loanSts, String upDate, String upTime) {
		super();
		this.applicationNo = applicationNo;
		this.loanSts = loanSts;
		this.upDate = upDate;
		this.upTime = upTime;
	}

  
}