package com.uranus.platform.business.pub.entity.po;

import lombok.Data;

@Data
public class PrdtBaseData {
    private String prdtId;

    private String prdtNo;

    private String brNo;

    private String brAccType;

    private String prdtName;

    private String begDate;

    private String endDate;

    private double minAmt;

    private double maxAmt;

    private Short minMon;

    private Short maxMon;

    private double minRate;

    private double maxRate;

    private String opNo;

    private String txDate;

    private String upOpno;

    private String upDate;

}