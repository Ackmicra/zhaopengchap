package com.uranus.platform.business.jd.entity.pojo;

import java.util.List;

import lombok.Data;

@Data
public class JDCallbackResponse2102 {

	private String  applicationNo;    //申请号
	private List<JDLoanStepExecuteResult> loanStepExecuteResults; //放款步骤
}
