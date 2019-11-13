package com.uranus.platform.business.jd.entity.po;

import java.math.BigDecimal;

import lombok.Data;
@Data
public class JdRepurchaseApplyData {
    private String jdId;

    private String applicationNo;

    private String channelBuybackApplyNo;

    private String repurchaseType;

    private BigDecimal principalAmount;

    private BigDecimal interestAmount;

    private BigDecimal penaltyRate;

    private BigDecimal otherFeeAmount;

    private String createDate;

    private String createTime;
    
    private String repurchaseSts;

    public String getJdId() {
        return jdId;
    }

    public void setJdId(String jdId) {
        this.jdId = jdId;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getChannelBuybackApplyNo() {
        return channelBuybackApplyNo;
    }

    public void setChannelBuybackApplyNo(String channelBuybackApplyNo) {
        this.channelBuybackApplyNo = channelBuybackApplyNo;
    }

    public String getRepurchaseType() {
        return repurchaseType;
    }

    public void setRepurchaseType(String repurchaseType) {
        this.repurchaseType = repurchaseType;
    }

    public BigDecimal getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(BigDecimal principalAmount) {
        this.principalAmount = principalAmount;
    }

    public BigDecimal getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(BigDecimal interestAmount) {
        this.interestAmount = interestAmount;
    }

    public BigDecimal getPenaltyRate() {
        return penaltyRate;
    }

    public void setPenaltyRate(BigDecimal penaltyRate) {
        this.penaltyRate = penaltyRate;
    }

    public BigDecimal getOtherFeeAmount() {
        return otherFeeAmount;
    }

    public void setOtherFeeAmount(BigDecimal otherFeeAmount) {
        this.otherFeeAmount = otherFeeAmount;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

	public String getRepurchaseSts() {
		return repurchaseSts;
	}

	public void setRepurchaseSts(String repurchaseSts) {
		this.repurchaseSts = repurchaseSts;
	}
}