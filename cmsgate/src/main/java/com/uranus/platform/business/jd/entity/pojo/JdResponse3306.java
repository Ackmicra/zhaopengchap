package com.uranus.platform.business.jd.entity.pojo;

import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;

@Data
@ConvertBean
public class JdResponse3306 {

	private String brNo; // 机构号
	private String projNo; // 项目号
	private String pactNo; // 合同号
	private String inTime; // 生成日期
	private String cifName; // 姓名
	private double repAmt; // 债转金额
	private double repPrcp; // 债转本金
	private double repInte; // 债转利息
	private double repFine; // 债转罚息
	private String repayDate; // 应还日期
	private String repDate; // 收款日期
	private String repSts; // 债转状态
}
