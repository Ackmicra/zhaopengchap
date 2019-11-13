package com.uranus.platform.business.jd.entity.status;

import com.uranus.tools.psm.status.BusinessCodeDefine;

public enum JdResponseStatus implements BusinessCodeDefine {
	
	//成功
	SUCCESS("TR00000","成功"),
	SUCCESS_FOR_DEAL("TR00001","受理成功，处理中"),
	//基本校验性错误
	UNKNOWN_ERROR("TR99999","未知异常"),
	SERIAL_REPEAT("TR00002","流水号重复"),
	AUTH_FAIL("TR00003","授权认证不通过"),
	PROJ_UNEXISTS("TR00004","项目信息不存在"),
	DATA_ERROR("TR00005","数据参数错误"),
	FORE_ELEMENTS_ERROR("TR00006","用户账户四要素错误"),
	UN_SIGNED("TR00007","用户未签约"),
	SIGN_IDENTIFY_ERROR("TR00008","签约确认验证码错误"),
	DENCRYPY_ERROR("TR00009", "签名/解密错误"),
	ACCOUNT_AGREEMENT("TR00010", "申请变更的账户与当前一致"),
	NO_PERMISSION("TR00011","无接口使用权限"),
	NO_CONTRACT_NO("TR01003","贷款单合同号重复"),
	NO_APPLICATION_NO("TR01004","申请号重复"),
	NO_APPLICATION("TR01005","申请号在数据库中不存在"),
	//扣款计划校验
	NO_LOAN_NO("TR01001","贷款单不存在"),
	NO_PAYMENTS_NO("TR03001","放款指令重复"),
	NO_LOAN_PAYMENT("TR01001","贷款单不存在"),
	PAYMENT_REPURCHASED("TR01008","贷款单已回购"),
	NO_REPAYPLAN("TR05001","还款计划不存在"),
	PAYMENT_PAYED("TR01007","贷款单已结清"),
	REPEAD_PAYED("TR06002","重复还款结果"),
	LASTPAYMENT_EXISTED("TR06001","不可越期还款"),
	REPATPLAN_EXAM_ERROR("TR06009","扣款计划验证不通过"),
	/**
	 * 支付单返回
	 */
	NO_TRANSFER_REPAYPLAN("TR06008","扣款计划不存在"),
	NO_REPAYPLAN_BATCH("TR06017","扣款计划批次不存在"),
	REPATPLAN_BATCHNO_REPEAT("TR06016","扣款计划批次号重复"),
	//还款计划
	REPATPLAN_REPEAT("TR05002","还款计划重复"),
	REPATPLAN_DATA_ERROR("TR05003","还款计划数据错误"),
	REPATPLAN_CANNOT_PROXY("TR05004","还款计划无法受理"),
	/**
	 * 还款计划类型
	 */
	PLN_SYN("0","还款计划同步"),
	PLN_CHANGE("1","还款计划变更"),
	PLN_FEE_CHANGE("2","还款计划费用更新"),
	//还款计划入京东库状态
	//扣款计划入京东库校验状态
	CHECK_STS("00","初始扣款状态"),
	CHECK_STS_SUCCESS("01","校验成功"),
	CHECK_STS_FAIL("02","校验失败"),
	//扣款计划表扣款状态
	LOAN_STS_DEFAULT("00","默认状态"),
	LOAN_STS_DEALING("01","处理中"),
	LOAN_SUCCESS("02","扣款成功"),
	LOAN_FAIL("03","扣款失败"),
	LOAN_STS_DEAL_FAIL("04","处理失败"), 	
	//线上扣款科目类型
	CHARGE_PRINCIPLE("1001","应扣本金"),
	CHARGED_PRINCIPLE("2001","实收本金"),
	CHARGE_RATE("1002","应扣利息"),
	CHARGED_RATE("2002","实收利息"),
	PENALTY_RATE("1003","应扣罚息"),
	PENALTYED_RATE("2003","实收罚息"),
	PENALTY_AMOUNT("4104","应扣违约金"),
	CHARGE_MOENY("4105","应扣手续费"),
	REFUND_SECURE_CHARGE("4103","应扣担保费"),
	REFUND_SERVICE_CHARGE("4102","应扣服务费"),
	//2313扣款查询线上标示
	REPAY_TYPE_ONLINE("01","线上扣款"),
	REPAY_TYPE_OUTLINE("02","线下实收"),
	//2312实收类型
	REPAY_TYPE_NORMAL("01","正常线下扣款"),
	REPAY_TYPE_CLEAR("02","提前清贷"),
	//京东扣款类型
	TRANSFER_TYPE_ONLINE("1001","线上代扣"),
	TRANSFER_TYPE_OUTLINE("1002","委托扣款"),
	TRANSFER_TYPE_COMPENSATORY("1003","代偿"),
	TRANSFER_TYPE_REPURCHASE("1004","回购"),
	//京东回购表状态
	ON_REPURCHASE("01","回购中"),
	SUCCESS_REPURCHASE("02","回购中"),
	FAIL_REPURCHASE("03","回购失败"),
	//京东审核结果查询审批结果魔法值
	APPROVAL_QUERY_NO("00","未审核或审核中"),
	APPROVAL_QUERY_PASS("01","审批通过"),
	APPROVAL_QUERY_REFUSE("02","审批拒绝"),
	//京东贷款申请响应数据审批结果魔法值
	ADUIT_RESULT_NO("00","未审核"),
	ADUIT_RESULT_SUCCESS("01","审核成功"),
	ADUIT_RESULT_FAIL("02","审核失败"),
	//京东贷款申请响应数据审批结果魔法值
	LOAN_STS_WORKING("00","处理中"),
	LOAN_STS_SUCCESS("01","放款成功"),
	LOAN_STS_FAIL("02","放款失败"),
	/**
	 * 京东签约查询响应数据签约状态
	 * 00-未签约;01-已签约；02-签约失败;03-处理中
	 */
	SIGN_NO("00","未签约"),
	SIGN_SUCCESS("01","已签约"),
	SIGN_FAIL("02","签约失败"),
	SIGN_DEALING("03","处理中"),
	/**
	 * 京东回调业务类型
	 */
	CALLBACK_CREDIT("011", "审核结果回调"),
	CALLBACK_LOAN("012", "放款结果回调"),
	CALLBACK_SYNC_REPAY_PLAN("023", "还款计划同步"),
	CALLBACK_CHANGE_REPAY_PLAN("024", "还款计划费用更新"),
	CALLBACK_TRANSFER_PLANS("035", "扣款计划校验结果"),
	//扣款计划校验结果validateStatus
	CHECK_FAILED("0","校验失败"),
	CHECK_SUCCESS("1","校验成功"),
	PAY_FAILED("2","支付失败"),
	PAY_SUCCESS("3","支付成功"),
	//扣款计划执行结果dealStatus
	DEAL_STATUS_UNEXECUTED("00","未执行"),
	DEAL_STATUS_SUCCESS("01","成功"),
	DEAL_STATUS_DEALING("02","处理中"),
	DEAL_STATUS_FAILED("03","失败"),
	//扣款计划小微返回执行结果
	DEAL_STS_UNEXECUTED("01","未执行"),
	DEAL_STS_DEALING("02","处理中"),
	DEAL_STS_SUCCESS("03","处理成功"),
	DEAL_STS_FAILED("04","处理失败"),
	SERVER_EXCEPTION("05","服务异常"),
	
	/**
	 * 京东放款状态 00-未做；01-成功；02-处理中；03-失败；04-等候
	 */
	CALLBACK_PAY_SUCCESS("01", "成功"),
	CALLBACK_PAY_DEALING("02", "处理中"),
	CALLBACK_PAY_UNSUCCESS("03", "失败"),
	
	/**
	 * 支付订单查询接口响应状态
	 */
	PAYMENT_DUALING("00","初始化"),
	PAYMENT_SUCCESS("01","支付成功"),
	PAYMENT_FAIL("02","支付失败"),
	
	/**
	 * 京东获取文件方式
	 */
	FINDING_FILE_SFPT("S","SFTP方式"),
	FINDING_FILE_BASE64("P","数据包形式"),
	
	/**
	 * 京东签章状态
	 */
	SIGN_UNKNOWN_STATUS("0","未知状态"),
	SIGN_NO_PACT("1","未生成合同"),
	SIGN_PACT_SUCCESS("2","生成合同成功"),
	SIGN_PACT_FAIL("3","生成合同失败"),
	PACT_SIGN_SUCCESS("4","合同签章成功"),
	PACT_SIGN_FAIL("5","合同签章失败"),
	PACT_SIGN_DEALING("8","签章处理中"),
	;
	private String businessCode;
	private String responseMsg;
	@Override
	public String businessCode() {
		return this.businessCode;
	}

	@Override
	public String businessMessage() {
		return this.responseMsg;
	}

	private JdResponseStatus(String businessCode, String responseMsg) {
		this.businessCode = businessCode;
		this.responseMsg = responseMsg;
	}
}
