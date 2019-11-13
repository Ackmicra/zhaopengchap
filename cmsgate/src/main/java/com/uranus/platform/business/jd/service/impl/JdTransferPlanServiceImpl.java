package com.uranus.platform.business.jd.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.business.jd.dao.JdLoanPaymentStepDataMapper;
import com.uranus.platform.business.jd.dao.JdPaymentOrderDataMapper;
import com.uranus.platform.business.jd.dao.JdRepurchaseApplyDataMapper;
import com.uranus.platform.business.jd.dao.JdTransferBatchDataMapper;
import com.uranus.platform.business.jd.dao.JdTransferPlansDataMapper;
import com.uranus.platform.business.jd.entity.po.JdLoanPaymentStepData;
import com.uranus.platform.business.jd.entity.po.JdPaymentOrderData;
import com.uranus.platform.business.jd.entity.po.JdRepurchaseApplyData;
import com.uranus.platform.business.jd.entity.po.JdTransferBatchData;
import com.uranus.platform.business.jd.entity.po.JdTransferPlansData;
import com.uranus.platform.business.jd.entity.pojo.JDLoanRepurchaseApply;
import com.uranus.platform.business.jd.entity.pojo.JDPaymentOrderRequest;
import com.uranus.platform.business.jd.entity.pojo.JDTransferPlansRequset;
import com.uranus.platform.business.jd.entity.pojo.JdLoanPaymentOrder;
import com.uranus.platform.business.jd.entity.pojo.JdLoanTransferPlan;
import com.uranus.platform.business.jd.entity.pojo.JdPaymentOrder;
import com.uranus.platform.business.jd.entity.status.JdResponseStatus;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;
import com.uranus.platform.business.jd.service.JdTransferPlanService;
import com.uranus.platform.business.jd.service.trans.PaymentOrder2312Service;
import com.uranus.platform.business.jd.service.trans.PaymentOrder3304Service;
import com.uranus.platform.business.jd.service.trans.RepurchaseApply3306Service;
import com.uranus.platform.business.jd.service.trans.TransferPlan2311Service;
import com.uranus.platform.business.jd.service.trans.TransferPlan2318Service;
import com.uranus.platform.business.jd.service.trans.TransferPlans2313Service;
import com.uranus.platform.business.pub.entity.dto.Request2202Dto;
import com.uranus.platform.business.pub.entity.dto.Response2202Dto;
import com.uranus.platform.business.pub.entity.po.ProjBaseData;
import com.uranus.platform.business.pub.mq.domain.MQParmsDomain;
import com.uranus.platform.business.pub.mq.producer.JDMQProducerManager;
import com.uranus.platform.business.pub.service.CreateKeyService;
import com.uranus.platform.business.pub.service.ProjBaseService;
import com.uranus.platform.business.ws.search.Request;
import com.uranus.platform.business.ws.search.Response;
import com.uranus.platform.business.ws.service.SearchService;
import com.uranus.platform.utils.aop.log.WsBaseLog;
import com.uranus.platform.utils.exception.PlatformExceptionFactory;
import com.uranus.platform.utils.status.CmsBusinessStatus;
import com.uranus.tools.beans.BeanCopyUtils;
import com.uranus.tools.utils.DateUtils;
import com.uranus.tools.validator.UranusValidator;

/**
 * 
 * @ClassName:：JdTransferPlanServiceImpl 
 * @author ：chenwendong
 * @date ：2019年8月15日 上午11:22:43
 *
 */
@Service
public class JdTransferPlanServiceImpl implements JdTransferPlanService {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private UranusValidator uranusValidator;
	@Autowired
	private ProjBaseService projBaseService;
	@Autowired
	private CreateKeyService createKeyService;
	@Autowired
	JdTransferPlansDataMapper jdTransferPlansDataMapper;
	@Autowired
	JdTransferBatchDataMapper jdTransferBatchDataMapper;
	@Autowired
	TransferPlan2311Service transferPlan2311Service;
	@Autowired
	JdLoanPaymentStepDataMapper jdLoanPaymentStepDataMapper;
	@Autowired
	JdRepurchaseApplyDataMapper jdRepurchaseApplyDataMapper;
	@Autowired
	private SearchService searchService;
	@Autowired
	PaymentOrder2312Service paymentOrder2312Service;
	@Autowired
	PaymentOrder3304Service paymentOrder3304Service;
	@Autowired
	JdPaymentOrderDataMapper jdPaymentOrderDataMapper;
	@Autowired
	RepurchaseApply3306Service repurchaseApply3306Service;
	@Autowired
	private JDMQProducerManager jDMQProducerManager;
	@Autowired
	private TransferPlans2313Service transferPlans2313Service;
	@Autowired
	TransferPlan2318Service transferPlan2318Service;

	/**
	 * 扣款计划上传校验部分
	 * 
	 * @param projNo     信托项目编号
	 * @param bizContent 已经解密后的消息正文
	 * @param channelId  京东机构编号，AOP插入ws_base记录表会使用
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	@WsBaseLog
	public JdResponseView syncTransferPlan(String projNo, String channelId, String bizContent) {
		JdResponseView JdResponseView = null;
		// 通过项目号查询机构信息
		ProjBaseData projBaseData = projBaseService.getByProjNo(projNo);
		if (projBaseData != null && !StringUtils.isEmpty(projBaseData.getBrNo())) {
			// 此处要处理的是京东的消息正文，，此部分不是走的rest服务，所以这部分不是View对象，而是一个Bean对象。你可以理解为一个POJO，或者把这个理解为一个DTO也可以。我建议就把它当作一个POJO对象，就叫JdLoanSyncRepayPlan或者JdLoanSyncRepayPlanEntity
			try {
				JdLoanTransferPlan jdLoanTransferPlan = objectMapper.readValue(bizContent, JdLoanTransferPlan.class);
				List<String> errorMessList = uranusValidator.validatorAll(jdLoanTransferPlan);
				if (errorMessList.size() == 0) {
					String brNo = projBaseData.getBrNo();
					/**
					 * 扣款计划校验
					 */
					Map<String, Object> checkMap = checkRepayPlan(brNo, jdLoanTransferPlan, projNo);
					JdResponseView = (JdResponseView)checkMap.get("jdResponseView");
					// 校验成功
					if (JdResponseView != null && JdResponseStatus.SUCCESS_FOR_DEAL.businessCode().equals(JdResponseView.getCode())) {
						String batchNo = (String)checkMap.get("batchNo");
						//线下扣款,回购直接返回
						if(JdResponseStatus.TRANSFER_TYPE_OUTLINE.businessCode().equals(jdLoanTransferPlan.getTransferType()) || 
								JdResponseStatus.TRANSFER_TYPE_REPURCHASE.businessCode().equals(jdLoanTransferPlan.getTransferType())) {
							
							/**
							 * 处理成功后发送，将任务放到MQ消息队列中
							 * 
							 * 	1. 创建MQ消息参数对象
							 * 	2. 将消息发送到MQ队列
							 */
							 MQParmsDomain message = new MQParmsDomain();
							 message.setTaskData(batchNo);
							 message.setTaskType(CmsBusinessStatus.CALLBACK_TRANSFER_PLAN_RESULT.businessCode());
							 message.setDelayLevel(Integer.parseInt(CmsBusinessStatus.MQ_DELAY_LEVEL_TWO.businessCode()));
							 jDMQProducerManager.send(message);
							return JdResponseView;
						} else if(JdResponseStatus.TRANSFER_TYPE_ONLINE.businessCode().equals(jdLoanTransferPlan.getTransferType())) {
							//线上扣款发送2311接口
							List<JdTransferPlansData> jdTransferPlansDataList = (List<JdTransferPlansData>)checkMap.get("dataList");
							return getTransferPlan(brNo, jdTransferPlansDataList, batchNo);
						}
					} else {//若校验失败则直接返回
						return JdResponseView;
					}

				} else {
					throw PlatformExceptionFactory.paramInValidateException(CmsBusinessStatus.INVALIDATE_PARAM)
							.build(errorMessList);
				}
			} catch (IOException e) {
				throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
			}
		} else {
			throw PlatformExceptionFactory.exception(JdResponseStatus.PROJ_UNEXISTS).build(JdResponseStatus.PROJ_UNEXISTS.businessMessage());
		}
		return JdResponseView;
	}

	/**
	 * @Description 扣款计划上传接口入库、请求小微、将响应转换成京东所需响应
	 * @param projNo              信托项目编号
	 * @param brNo                合作机构编号
	 * @param jdLoanSyncRepayPlan 还款计划数据
	 */
	public JdResponseView getTransferPlan(String brNo, List<JdTransferPlansData> jdTransferPlansDataList,
			String batchNo) {
		// 线上扣款
		JdResponseView jdResponseView = transferPlan2311Service.request2311(brNo, createKeyService.getJdTradeNo(),
				jdTransferPlansDataList, batchNo);
		if (JdResponseStatus.SUCCESS_FOR_DEAL.businessCode().equals(jdResponseView.getCode())) {
			// 成功后更新扣款表状态为处理中
			JdTransferPlansData jdTransferPlansData = new JdTransferPlansData();
			jdTransferPlansData.setBatchNumber(batchNo);
			jdTransferPlansData.setUpDate(DateUtils.nowDateFormat());
			jdTransferPlansData.setUpTime(DateUtils.nowTimeFormat());
			jdTransferPlansData.setLoanSts(JdResponseStatus.LOAN_STS_DEALING.businessCode());
			jdTransferPlansDataMapper.updateLoanResultByBatchNo(jdTransferPlansData);
			
			/**
			 * 处理成功后发送，将任务放到MQ消息队列中
			 * 
			 * 	1. 创建MQ消息参数对象
			 * 	2. 将消息发送到MQ队列
			 */
			 MQParmsDomain message = new MQParmsDomain();
			 message.setTaskData(batchNo);
			 message.setTaskType(CmsBusinessStatus.CALLBACK_TRANSFER_PLAN_RESULT.businessCode());
			 message.setDelayLevel(Integer.parseInt(CmsBusinessStatus.MQ_DELAY_LEVEL_TWO.businessCode()));
			 jDMQProducerManager.send(message);
		} else {
			// 失败更新扣款表状态为处理失败
			JdTransferPlansData jdTransferPlansData = new JdTransferPlansData();
			jdTransferPlansData.setBatchNumber(batchNo);
			jdTransferPlansData.setUpDate(DateUtils.nowDateFormat());
			jdTransferPlansData.setUpTime(DateUtils.nowTimeFormat());
			jdTransferPlansData.setLoanSts(JdResponseStatus.LOAN_STS_FAIL.businessCode());
			jdTransferPlansDataMapper.updateLoanResultByBatchNo(jdTransferPlansData);
		}
		return jdResponseView;
	}

	/**
	 * 
	* @Title：getRequest2202ForTransfer 
	* @Description：拼接小薇2202请求报文
	* @param ：@param brNo
	* @param ：@param tradeNo
	* @param ：@param aplicationNo
	* @param ：@return 
	* @return ：Request 
	* @throws
	 */
	public Request getRequest2202ForTransfer(String brNo, String tradeNo, String aplicationNo) {
		Request2202Dto request2202Dto = new Request2202Dto();
		request2202Dto.setBrNo(brNo);
		request2202Dto.setPactNo(aplicationNo);
		// 拼发送小微报文
		Request request = new Request();
		request.setTxCode(CmsBusinessStatus.WS_2202.businessCode()); // 接口编号
		request.setBrNo(brNo); // 机构号
		request.setReqDate(DateUtils.nowDateFormat()); // 设置请求日期
		request.setToken("test"); // 设置token
		request.setReqTime(DateUtils.nowTimeFormat()); // 设置请求时间
		request.setReqSerial(tradeNo); // 设置请求流水号
		try {
			request.setContent(objectMapper.writeValueAsString(request2202Dto));
		} catch (JsonProcessingException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		return request;
	}

	/**
	 * 
	* @Title：transplanExam 
	* @Description：线下扣款校验
	* @param ：@param brNo
	* @param ：@param jdLoanTransferPlan
	* @param ：@param projNo
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	public Map<String, Object> checkRepayPlan(String brNo, JdLoanTransferPlan jdLoanTransferPlan, String projNo) {

		// 获取申请号
		String batchNo = jdLoanTransferPlan.getBatchNumber(); // 批次号
		String transferType = jdLoanTransferPlan.getTransferType(); // 扣款类型
		String paymentAgentType = jdLoanTransferPlan.getPaymentAgentType(); // 支付受托方类型
		/**
		 * 将请求转换成DATA对象
		 */
		List<JdTransferPlansData> jdTransferPlansDataList = jdLoanTransferPlan.getTransferPlans().stream().map(pojo -> {
			JdTransferPlansData jdTransferPlansData = BeanCopyUtils.INSTANCE.convertTo(pojo, JdTransferPlansData.class);
			jdTransferPlansData.setBatchNumber(batchNo);
			jdTransferPlansData.setCheckSts(JdResponseStatus.CHECK_STS.businessCode());
			jdTransferPlansData.setLoanSts(JdResponseStatus.LOAN_STS_DEFAULT.businessCode());
			jdTransferPlansData.setCreateDate(DateUtils.nowDateFormat());
			jdTransferPlansData.setCreateTime(DateUtils.nowTimeFormat());
			return jdTransferPlansData;
		}).collect(Collectors.toList());

		/**
		 * 校验并入库操作
		 */
		Map<String, Object> resultMap = checkBatchNoAndInsert(batchNo, transferType, paymentAgentType, brNo, projNo, jdTransferPlansDataList);
		
		return resultMap;
	}


	/**  
	* @Description
	* 			校验并入库
	*/  
	private Map<String, Object> checkBatchNoAndInsert(String batchNo, String transferType, String paymentAgentType, String brNo, String projNo,
			List<JdTransferPlansData> jdTransferPlansDataList) {
		JdResponseView jdResponseView = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String tradeNo = createKeyService.getJdTradeNo();
		//校验批次号
		JdTransferBatchData jdTransferBatchData =null;
		if(jdTransferBatchDataMapper.getByBatchNo(batchNo) != null) {
			jdResponseView = new JdResponseView(JdResponseStatus.REPATPLAN_BATCHNO_REPEAT.businessCode(),
					JdResponseStatus.REPATPLAN_BATCHNO_REPEAT.businessMessage(), tradeNo, "{}");
			resultMap.put("jdResponseView", jdResponseView);
			return resultMap;
		}else {
			//若批次号校验成功则插入批次表
			 jdTransferBatchData = new JdTransferBatchData();
			 jdTransferBatchData.setTransferType(transferType);
			 jdTransferBatchData.setPaymentAgentType(paymentAgentType);
			 jdTransferBatchData.setBatchNumber(batchNo);
			 jdTransferBatchData.setProjNo(projNo);
			 jdTransferBatchData.setCheckSts(JdResponseStatus.CHECK_STS.businessCode());
			 jdTransferBatchData.setCreateDate(DateUtils.nowDateFormat());
			 jdTransferBatchData.setCreateTime(DateUtils.nowTimeFormat());
			jdTransferBatchDataMapper.insert(jdTransferBatchData);
		}
		//插入明细表
		jdTransferPlansDataMapper.insert(jdTransferPlansDataList);
		
		/**
		 * 校验还款计划明细
		 */
		resultMap = checkTransferPlans(batchNo, brNo, projNo, tradeNo, jdTransferPlansDataList);
		
		return resultMap;
	}


	/**  
	* @Description
	* 			校验扣款计划上送明细
	*/  
	private Map<String, Object> checkTransferPlans(String batchNo, String brNo, String projNo, String tradeNo,
			List<JdTransferPlansData> jdTransferPlansDataList) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JdResponseView jdResponseView = null;
		/**
		 * 校验京东上传的扣款计划
		 */
		//取最小期次
		int minIssue = jdTransferPlansDataList.get(0).getCurrentIssue();
		if(jdTransferPlansDataList.size() > 1) {
			for (JdTransferPlansData M : jdTransferPlansDataList) {
				if(M.getCurrentIssue() < minIssue) {
					minIssue = M.getCurrentIssue();
				} 
			}
		}
		
		for (JdTransferPlansData V : jdTransferPlansDataList) {
			String applicationNo = V.getApplicationNo();

			// 1. 校验贷款单不存在(通过申请号查询JD_LOAN_PAYMENT_STEP放款步骤表，是否存在记录)；
			List<JdLoanPaymentStepData> jdLoanPaymentStepData = jdLoanPaymentStepDataMapper
					.getByApplicationNo(applicationNo);
			if (jdLoanPaymentStepData.size() == 0) {// 返回贷款单不存在
				/**
				 * 更新扣款计划上送批次表及明细表	
				 */
				updateTransfer(batchNo, JdResponseStatus.CHECK_STS_FAIL.businessCode(), JdResponseStatus.NO_LOAN_PAYMENT.businessMessage());
				
				jdResponseView = new JdResponseView(JdResponseStatus.NO_LOAN_PAYMENT.businessCode(),
						JdResponseStatus.NO_LOAN_PAYMENT.businessMessage(), tradeNo, "{}");
				resultMap.put("jdResponseView", jdResponseView);
				return resultMap;
				
			}

			// 2. 校验贷款单已回购（根据申请号查询JD_REPURCHASE_APPLY回购申请表，是否存在回购中或回购成功的申请
			List<JdRepurchaseApplyData> jdRepurchaseApplyList = jdRepurchaseApplyDataMapper
					.getByApplicationNoAndSts(applicationNo);
			if (jdRepurchaseApplyList.size() > 0) {// 返回贷款单已回购
				/**
				 * 更新扣款计划上送批次表及明细表	
				 */
				updateTransfer(batchNo, JdResponseStatus.CHECK_STS_FAIL.businessCode(), JdResponseStatus.PAYMENT_REPURCHASED.businessMessage());
				
				jdResponseView = new JdResponseView(JdResponseStatus.PAYMENT_REPURCHASED.businessCode(),
						JdResponseStatus.PAYMENT_REPURCHASED.businessMessage(), tradeNo, "{}");
				resultMap.put("jdResponseView", jdResponseView);
				return resultMap;
			}
			
			// 3.  拼报文查询小微系统还款计划信息，校验扣款计划信息（期次、金额、结清状态）
			resultMap = checkRepayPlanDetail(V, brNo, tradeNo, applicationNo, batchNo,minIssue);
			resultMap.put("dataList", jdTransferPlansDataList);
			resultMap.put("batchNo", batchNo);
		}
		return resultMap;
	}

	/**  
	* @Description
	* 			更新扣款计划上送批次表及明细表
	*/  
	private void updateTransfer(String batchNo, String checkSts, String checkDesc) {
		String nowDate = DateUtils.nowDateFormat();
		String nowTime = DateUtils.nowTimeFormat();
		//更新扣款计划表
		JdTransferPlansData plans = new JdTransferPlansData();
		plans.setCheckSts(checkSts);
		plans.setUpDate(nowDate);
		plans.setUpTime(nowTime);
		plans.setBatchNumber(batchNo);

		jdTransferPlansDataMapper.updateCheckResultByBatchNo(plans);
		
		//更新批次表
		JdTransferBatchData batchPlans = new JdTransferBatchData();
		batchPlans.setUpDate(nowDate);
		batchPlans.setUpTime(nowTime);
		batchPlans.setCheckSts(checkSts);
		batchPlans.setBatchNumber(batchNo);
		batchPlans.setCheckDesc(checkDesc);
		jdTransferBatchDataMapper.updateCheckResultByBatchNo(batchPlans);
	}

	/**
	 * 
	* @Title：findAndExamTransplan 
	* @Description：拼报文查询小微系统还款计划信息，校验期次及金额
	* 		1. 查询小微还款计划，校验还款计划是否存在
	* 		2. 校验前一期未还不能还下一期
	* 		3.校验该期次是否已经结清 
	* 		4. 校验扣款计划上送的该期次的本金和利息是否等于小微还款计划该期次的本金和利息
	 */
	public Map<String, Object> checkRepayPlanDetail(JdTransferPlansData V, String brNo, String tradeNo,
			String applicationNo, String batchNo,int minIssue) {
		JdResponseView jdResponseView = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			/**
			 * 3. 查询2202接口，校验还款计划是否存在；
			 * 
			 */
			Request request = getRequest2202ForTransfer(brNo, createKeyService.getJdTradeNo(), applicationNo);
			// 发送小微系统
			Response response = searchService.search(CmsBusinessStatus.MFS.businessCode(), request);
			// 将小微响应转换成还款计划对象
			List<Response2202Dto> response2202DtoList = objectMapper.readValue(response.getContent(),
					new TypeReference<List<Response2202Dto>>() {
					});
			if (response2202DtoList.size() == 0 || response2202DtoList == null) {
				/**
				 * 更新扣款计划上送批次表及明细表	
				 */
				updateTransfer(batchNo, JdResponseStatus.CHECK_STS_FAIL.businessCode(), JdResponseStatus.NO_REPAYPLAN.businessMessage());
				
				// 返回还款计划不存在
				jdResponseView = new JdResponseView(JdResponseStatus.NO_REPAYPLAN.businessCode(),
						JdResponseStatus.NO_REPAYPLAN.businessMessage(), tradeNo, "{}");
				resultMap.put("jdResponseView", jdResponseView);
				return resultMap;
			}

			/**
			 * 将还款计划以期次为键，还款计划为值放入map中
			 */
			double prcpAmtSum = 0.00;
			double chargeRateSum = 0.00;
			
			Map<String, Response2202Dto> map2202 = new HashMap<String, Response2202Dto>();
			for(Response2202Dto pojo:response2202DtoList) {
				if(CmsBusinessStatus.UN_SETTLE.businessCode().equals(pojo.getSts())) {
					prcpAmtSum += pojo.getPrcpAmt();
					chargeRateSum += pojo.getNormInt();
				}
				map2202.put(String.valueOf(pojo.getCnt()), pojo);
			}
			//判断是否提前结清
			if("true".equals(V.getSettledEarly())) {
				//提前结清
				if(V.getChargePrincipal() == prcpAmtSum && V.getChargeRate() == chargeRateSum ) {
					//校验通过
					/**
					 * 更新扣款计划上送批次表及明细表	
					 */
					updateTransfer(batchNo, JdResponseStatus.CHECK_STS_SUCCESS.businessCode(), JdResponseStatus.CHECK_STS_SUCCESS.businessMessage());
					
					// 返回校验通过
					jdResponseView = new JdResponseView(JdResponseStatus.SUCCESS_FOR_DEAL.businessCode(),
							JdResponseStatus.SUCCESS_FOR_DEAL.businessMessage(), tradeNo, "{}");
					
					resultMap.put("jdResponseView", jdResponseView);
					return resultMap;
				}else {
					//本金和利息总额不等
					/**
					 * 更新扣款计划上送批次表及明细表	
					 */
					updateTransfer(batchNo, JdResponseStatus.CHECK_STS_FAIL.businessCode(), JdResponseStatus.REPATPLAN_EXAM_ERROR.businessMessage());
					
					// 返回数据参数错误（本金，利息不等）
					jdResponseView = new JdResponseView(JdResponseStatus.REPATPLAN_EXAM_ERROR.businessCode(),
							JdResponseStatus.REPATPLAN_EXAM_ERROR.businessMessage(), tradeNo, "{}");
					
					resultMap.put("jdResponseView", jdResponseView);
					return resultMap;
				}
				
			}
			/**
			 * 4. 校验前期未还不允许还下一期；
			 */			
			if (minIssue != 1) {// 如果不是第一期，则前一期必须结清
				// 获取前一期的还款计划
				Response2202Dto response2202Dto = map2202.get(String.valueOf(minIssue - 1));
				// 如果前一起未结清，则校验失败
				if (CmsBusinessStatus.UN_SETTLE.businessCode().equals(response2202Dto.getSts())) {
					/**
					 * 更新扣款计划上送批次表及明细表	
					 */
					updateTransfer(batchNo, JdResponseStatus.CHECK_STS_FAIL.businessCode(), "校验期次"+ V.getCurrentIssue() + ":" + JdResponseStatus.LASTPAYMENT_EXISTED.businessMessage());
					
					jdResponseView = new JdResponseView(JdResponseStatus.LASTPAYMENT_EXISTED.businessCode(),
							JdResponseStatus.LASTPAYMENT_EXISTED.businessMessage(), tradeNo, "{}");
					resultMap.put("jdResponseView", jdResponseView);
					return resultMap;
				} 
			}
			// 5. 校验该期次还款计划是否已结清，若以结清，则校验失败，若未结清，则继续校验本金（根据还款计划结清状态校验）；
			Response2202Dto response2202Dto1 = map2202.get(String.valueOf(V.getCurrentIssue()));
			if (CmsBusinessStatus.UN_SETTLE.businessCode().equals(response2202Dto1.getSts())) {// 当期未结清
				// 6. 校验京东所传本金和利息是否等于小微还款计划本金和利息，期次为：京东方扣款计划上送期次；
				if (V.getChargePrincipal() == response2202Dto1.getPrcpAmt() && V.getChargeRate() == response2202Dto1.getNormInt()) {
					/**
					 * 更新扣款计划上送批次表及明细表	
					 */
					updateTransfer(batchNo, JdResponseStatus.CHECK_STS_SUCCESS.businessCode(), JdResponseStatus.CHECK_STS_SUCCESS.businessMessage());
					
					// 返回校验通过
					jdResponseView = new JdResponseView(JdResponseStatus.SUCCESS_FOR_DEAL.businessCode(),
							JdResponseStatus.SUCCESS_FOR_DEAL.businessMessage(), tradeNo, "{}");
				} else {
					/**
					 * 更新扣款计划上送批次表及明细表	
					 */
					updateTransfer(batchNo, JdResponseStatus.CHECK_STS_FAIL.businessCode(), JdResponseStatus.REPATPLAN_EXAM_ERROR.businessMessage());
					
					// 返回数据参数错误（本金，利息不等）
					jdResponseView = new JdResponseView(JdResponseStatus.REPATPLAN_EXAM_ERROR.businessCode(),
							JdResponseStatus.REPATPLAN_EXAM_ERROR.businessMessage(), tradeNo, "{}");
				}
			} else {
				/**
				 * 更新扣款计划上送批次表及明细表	
				 */
				updateTransfer(batchNo, JdResponseStatus.CHECK_STS_FAIL.businessCode(), "期次"+V.getCurrentIssue()+":"+JdResponseStatus.REPEAD_PAYED.businessMessage());
				
				// 返回本期已经结清
				jdResponseView = new JdResponseView(JdResponseStatus.REPEAD_PAYED.businessCode(),
						JdResponseStatus.REPEAD_PAYED.businessMessage(), tradeNo, "{}");
			}
		} catch (IOException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		resultMap.put("jdResponseView", jdResponseView);
		return resultMap;
	}

	/**
	 * 
	* @Title：paymentOrder
	* @Description：支付订单
	* @param ：@param projNo
	* @param ：@param channelId
	* @param ：@param tradeNo
	* @param ：@param bizContent
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	@Override
	@WsBaseLog
	public JdResponseView paymentOrder(String projNo, String channelId, String bizContent) {
		// 通过项目号查询机构信息
		ProjBaseData projBaseData = projBaseService.getByProjNo(projNo);
		if (projBaseData != null && !StringUtils.isEmpty(projBaseData.getBrNo())) {
			// 此处要处理的是京东的消息正文，，此部分不是走的rest服务，所以这部分不是View对象，而是一个Bean对象。你可以理解为一个POJO，或者把这个理解为一个DTO也可以。我建议就把它当作一个POJO对象，就叫JdLoanSyncRepayPlan或者JdLoanSyncRepayPlanEntity
			try {
				JdLoanPaymentOrder jdLoanPaymentOrder = objectMapper.readValue(bizContent, JdLoanPaymentOrder.class);
				List<String> errorMessList = uranusValidator.validatorAll(jdLoanPaymentOrder);
				if (errorMessList.size() == 0) {
					String brNo = projBaseData.getBrNo();

					return checkPaymentOrder(brNo, jdLoanPaymentOrder, projNo);

				} else {
					throw PlatformExceptionFactory.paramInValidateException(CmsBusinessStatus.INVALIDATE_PARAM)
							.build(errorMessList);
				}
			} catch (IOException e) {
				throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
			}
		} else {
			throw PlatformExceptionFactory.exception(JdResponseStatus.PROJ_UNEXISTS).build("项目不存在");
		}
	}
	/**
	 * @Description 校验支付单
	 * @param projNo              信托项目编号
	 * @param brNo                合作机构编号
	 * @param jdLoanPaymentOrder 支付单信息
	 */
	public JdResponseView checkPaymentOrder(String brNo, JdLoanPaymentOrder jdLoanPaymentOrder, String projNo) {
		String batchNumber = jdLoanPaymentOrder.getBatchNumber(); // 批次号
		String transferType = jdLoanPaymentOrder.getTransferType(); // 扣款类型
		// 根据批次号查扣款计划上送表
		List<JdTransferPlansData> jdTransferPlanDataList = jdTransferPlansDataMapper.getByBatchNo(batchNumber);
		if (jdTransferPlanDataList.isEmpty()) {
			// 返回无批次号对应扣款计划信息
			return new JdResponseView(JdResponseStatus.NO_REPAYPLAN_BATCH.businessCode(),
					JdResponseStatus.NO_REPAYPLAN_BATCH.businessMessage(), createKeyService.getJdTradeNo(), "{}");
		} else {
			// 根据批次号获取扣款计划上送数据，拼接口报文发送给小微
			// 小微用批次号
			String batNo = createKeyService.getJdBatchNo();
			return getPaymentOrder(brNo, jdLoanPaymentOrder, jdTransferPlanDataList, batNo, transferType,
					batchNumber,projNo);
		}

	}
	/**
	 * 
	* @Title：getPaymentOrder 
	* @param ：@param applicationNo
	* @param ：@param brNo
	* @param ：@param jdLoanPaymentOrder
	* @param ：@param jdTransferPlanDataList
	* @param ：@param batNo
	* @param ：@param transferType
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	public JdResponseView getPaymentOrder(String brNo, JdLoanPaymentOrder jdLoanPaymentOrder,
			List<JdTransferPlansData> jdTransferPlanDataList, String batNo, String transferType, String batchNumber,
			String projNo) {
		JdResponseView jdResponseView = null;
		// 此部分的对象同上文解释，也应该是一个Bean对象
		JdPaymentOrder jdPaymentOrder = jdLoanPaymentOrder.getPaymentOrder();

		JdPaymentOrderData jdPaymentOrderData = BeanCopyUtils.INSTANCE.convertTo(jdPaymentOrder,
				JdPaymentOrderData.class);
		jdPaymentOrderData.setTransferType(transferType);
		jdPaymentOrderData.setBatchNumber(batchNumber);
		jdPaymentOrderData.setMfsBatchNo(batNo);
		// jdPaymentOrderData.setApplicationNo(applicationNo);
		jdPaymentOrderData.setCreateDate(DateUtils.nowDateFormat());
		jdPaymentOrderData.setCreateTime(DateUtils.nowTimeFormat());

		jdPaymentOrderDataMapper.insert(jdPaymentOrderData);

		// 判断扣款类型，1002-线下-调小微2312B；1004-回购-调小微3304
		if (JdResponseStatus.TRANSFER_TYPE_OUTLINE.businessCode().equals(transferType)) {
			jdResponseView = paymentOrder2312Service.request2312(brNo, createKeyService.getJdTradeNo(),
					jdTransferPlanDataList, batNo);
		} else if (JdResponseStatus.TRANSFER_TYPE_REPURCHASE.businessCode().equals(transferType)) {
			jdResponseView = paymentOrder3304Service.request3304(brNo, createKeyService.getJdTradeNo(),
					jdTransferPlanDataList, batNo, projNo);
		}
		return jdResponseView;

	}

	/**
	 * 
	* @Title：paymentOrder
	* @Description：回购申请
	* @param ：@param projNo
	* @param ：@param channelId
	* @param ：@param bizContent
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	@Override
	@WsBaseLog
	public JdResponseView repurchaseApply(String projNo, String channelId, String bizContent) {
		// 通过项目号查询机构信息
		ProjBaseData projBaseData = projBaseService.getByProjNo(projNo);
		if (projBaseData != null && !StringUtils.isEmpty(projBaseData.getBrNo())) {
			// 此处要处理的是京东的消息正文，，此部分不是走的rest服务，所以这部分不是View对象，而是一个Bean对象。你可以理解为一个POJO，或者把这个理解为一个DTO也可以。我建议就把它当作一个POJO对象，就叫JdLoanSyncRepayPlan或者JdLoanSyncRepayPlanEntity
			try {
				JDLoanRepurchaseApply jDLoanRepurchaseApply = objectMapper.readValue(bizContent,
						JDLoanRepurchaseApply.class);
				List<String> errorMessList = uranusValidator.validatorAll(jDLoanRepurchaseApply);
				if (errorMessList.size() == 0) {
					String brNo = projBaseData.getBrNo();

					return getRepurchaseApply(brNo, jDLoanRepurchaseApply, projNo);

				} else {
					throw PlatformExceptionFactory.paramInValidateException(CmsBusinessStatus.INVALIDATE_PARAM)
							.build(errorMessList);
				}
			} catch (IOException e) {
				throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
			}
		} else {
			throw PlatformExceptionFactory.exception(JdResponseStatus.PROJ_UNEXISTS).build("项目不存在");
		}
	}

	/**
	 * 
	* @Title：getRepurchaseApply 
	* @Description：回购申请发送小微
	* @param ：@param brNo
	* @param ：@param jdLoanRepurchaseAplly
	* @param ：@param projNo
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	public JdResponseView getRepurchaseApply(String brNo, JDLoanRepurchaseApply jdLoanRepurchaseApply, String projNo) {
		String applicationNo = jdLoanRepurchaseApply.getApplicationNo();
		// 校验该贷款单是否存在回购中或回购成功的申请
		List<JdRepurchaseApplyData> jdRepurchaseApplyDataList = jdRepurchaseApplyDataMapper
				.getByApplicationNoAndSts(applicationNo);
		if (!jdRepurchaseApplyDataList.isEmpty()) {
			// 已经存在回购申请，直接返回
			return new JdResponseView(JdResponseStatus.PAYMENT_REPURCHASED.businessCode(),
					JdResponseStatus.PAYMENT_REPURCHASED.businessMessage(), createKeyService.getJdTradeNo(), "{}");
		}
		// 入库
		JdRepurchaseApplyData jdRepurchaseApplyData = BeanCopyUtils.INSTANCE.convertTo(jdLoanRepurchaseApply,
				JdRepurchaseApplyData.class);
		jdRepurchaseApplyData.setCreateDate(DateUtils.nowDateFormat());
		jdRepurchaseApplyData.setCreateTime(DateUtils.nowTimeFormat());
		jdRepurchaseApplyDataMapper.insert(jdRepurchaseApplyData);

		// 发送小微
		JdResponseView jdResponseView = repurchaseApply3306Service.request3306(brNo, jdRepurchaseApplyData,
				createKeyService.getJdTradeNo(), projNo);

		if (JdResponseStatus.SUCCESS.businessCode().equals(jdResponseView.getCode())) {
			// 成功修改数据为回购中
			jdRepurchaseApplyData.setRepurchaseSts(JdResponseStatus.ON_REPURCHASE.businessCode());
			jdRepurchaseApplyDataMapper.updateByApplicationNo(jdRepurchaseApplyData);
		} else {
			// 其他为回购失败
			jdRepurchaseApplyData.setRepurchaseSts(JdResponseStatus.FAIL_REPURCHASE.businessCode());
			jdRepurchaseApplyDataMapper.updateByApplicationNo(jdRepurchaseApplyData);
		}
		return jdResponseView;

	}

	/**
	 * 扣款计划结果查询接口
	 */
	@Override
	@WsBaseLog
	public JdResponseView queryForTransferPlans(String projNo, String channelId, String bizContent) {
		// 通过项目号查询机构信息
		ProjBaseData projBaseData = projBaseService.getByProjNo(projNo);
		if (projBaseData != null && !StringUtils.isEmpty(projBaseData.getBrNo())) {
			// 此处要处理的是京东的消息正文，，此部分不是走的rest服务，所以这部分不是View对象，而是一个Bean对象。你可以理解为一个POJO，或者把这个理解为一个DTO也可以。我建议就把它当作一个POJO对象，就叫JdLoanSyncRepayPlan或者JdLoanSyncRepayPlanEntity
			try {
				JDTransferPlansRequset jDTransferPlansRequset = objectMapper.readValue(bizContent,
						JDTransferPlansRequset.class);
				List<String> errorMessList = uranusValidator.validatorAll(jDTransferPlansRequset);
				if (errorMessList.size() == 0) {
					String brNo = projBaseData.getBrNo();

					return queryForTransferPlans(brNo, jDTransferPlansRequset.getBatchNum());

				} else {
					throw PlatformExceptionFactory.paramInValidateException(CmsBusinessStatus.INVALIDATE_PARAM)
							.build(errorMessList);
				}
			} catch (IOException e) {
				throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
			}
		} else {
			throw PlatformExceptionFactory.exception(JdResponseStatus.PROJ_UNEXISTS).build(JdResponseStatus.PROJ_UNEXISTS.businessMessage());
		}
	}

	private JdResponseView queryForTransferPlans(String brNo, String batchNum) {
		JdResponseView jdResponseView = null;
		// 根据批次号查询扣款计划批次信息
		JdTransferBatchData jdTransferBatch = jdTransferBatchDataMapper.getByBatchNo(batchNum);
		String tradeNo = createKeyService.getJdTradeNo();
		if(jdTransferBatch != null) {
			// 根据批此扣款类型【1001-线上扣款，1002-线下扣款】
			String transferType = jdTransferBatch.getTransferType();

			/**
			 * 1. 判断是线上还是线下，
			 * 		如果是线上需调用小微2313查询接口
			 * 		如果是线下或回购只走校验
			 */
			jdResponseView = transferPlans2313Service.queryFor2313(batchNum, brNo, transferType, tradeNo,
					jdTransferBatch.getCheckSts(), jdTransferBatch.getCheckDesc());
		} else {
			jdResponseView = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(),
					JdResponseStatus.DATA_ERROR.businessMessage(), tradeNo, "{}");
		}
		
		return jdResponseView;
	}

	/** 
	 *  支付订单查询接口
	 */
	@Override
	@WsBaseLog
	public JdResponseView queryPaymentOrder(String projNo, String channelId, String bizContent) {
		// 通过项目号查询机构信息
		ProjBaseData projBaseData = projBaseService.getByProjNo(projNo);
		if (projBaseData != null && !StringUtils.isEmpty(projBaseData.getBrNo())) {
			// 此处要处理的是京东的消息正文，，此部分不是走的rest服务，所以这部分不是View对象，而是一个Bean对象。你可以理解为一个POJO，或者把这个理解为一个DTO也可以。我建议就把它当作一个POJO对象，就叫JdLoanSyncRepayPlan或者JdLoanSyncRepayPlanEntity
			try {
				JDPaymentOrderRequest jDPaymentOrderRequest = objectMapper.readValue(bizContent,
						JDPaymentOrderRequest.class);
				List<String> errorMessList = uranusValidator.validatorAll(jDPaymentOrderRequest);
				if (errorMessList.size() == 0) {
					String brNo = projBaseData.getBrNo();
					JdPaymentOrderData jdPaymentOrderData = new JdPaymentOrderData();
					jdPaymentOrderData.setBatchNumber(jDPaymentOrderRequest.getBatchNumber());
					jdPaymentOrderData.setTransferType(jDPaymentOrderRequest.getTransferType());
					// 根据京东传的批次号和批次类型，查询发送给小微的批次号
					JdPaymentOrderData jdPaymentOrder = jdPaymentOrderDataMapper.getByJDBatchNo(jdPaymentOrderData);
					if(jdPaymentOrder == null) {
						return new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(),
								JdResponseStatus.DATA_ERROR.businessMessage(), createKeyService.getJdTradeNo(), "{}");
					} else {
						return queryForPaymentOrder(brNo, jdPaymentOrder.getMfsBatchNo(), jdPaymentOrder.getTransferType());
					}

				} else {
					throw PlatformExceptionFactory.paramInValidateException(CmsBusinessStatus.INVALIDATE_PARAM)
							.build(errorMessList);
				}
			} catch (IOException e) {
				throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
			}
		} else {
			throw PlatformExceptionFactory.exception(JdResponseStatus.PROJ_UNEXISTS).build(JdResponseStatus.PROJ_UNEXISTS.businessMessage());
		}
	}

	/**  
	* @Description
	* 		支付订单查询接口处理
	*/  
	private JdResponseView queryForPaymentOrder(String brNo, String batchNumber, String transferType) {
		JdResponseView jdResponseView = null;
		String tradeNo = createKeyService.getJdTradeNo();
		if (JdResponseStatus.TRANSFER_TYPE_OUTLINE.businessCode().equals(transferType)) {
			jdResponseView = transferPlans2313Service.transPaymentOrder(batchNumber, brNo, tradeNo, transferType);
		}
		return jdResponseView;
	}
	
	/**
	 * 扣款计划上传校验部分
	 * 
	 * @param projNo     信托项目编号
	 * @param bizContent 已经解密后的消息正文
	 * @param channelId  京东机构编号，AOP插入ws_base记录表会使用
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	@WsBaseLog
	public JdResponseView syncTransferPlanNew(String projNo, String channelId, String bizContent) {
		JdResponseView JdResponseView = null;
		// 通过项目号查询机构信息
		ProjBaseData projBaseData = projBaseService.getByProjNo(projNo);
		if (projBaseData != null && !StringUtils.isEmpty(projBaseData.getBrNo())) {
			// 此处要处理的是京东的消息正文，，此部分不是走的rest服务，所以这部分不是View对象，而是一个Bean对象。你可以理解为一个POJO，或者把这个理解为一个DTO也可以。我建议就把它当作一个POJO对象，就叫JdLoanSyncRepayPlan或者JdLoanSyncRepayPlanEntity
			try {
				JdLoanTransferPlan jdLoanTransferPlan = objectMapper.readValue(bizContent, JdLoanTransferPlan.class);
				List<String> errorMessList = uranusValidator.validatorAll(jdLoanTransferPlan);
				if (errorMessList.size() == 0) {
					String brNo = projBaseData.getBrNo();
					/**
					 * 扣款计划校验
					 */
					Map<String, Object> checkMap = checkRepayPlanNew(brNo, jdLoanTransferPlan, projNo);
					JdResponseView = (JdResponseView)checkMap.get("jdResponseView");
					// 校验成功
					if (JdResponseView != null && JdResponseStatus.SUCCESS_FOR_DEAL.businessCode().equals(JdResponseView.getCode())) {
						String batchNo = (String)checkMap.get("batchNo");
						//线下扣款,回购直接返回
						if(JdResponseStatus.TRANSFER_TYPE_OUTLINE.businessCode().equals(jdLoanTransferPlan.getTransferType()) || 
								JdResponseStatus.TRANSFER_TYPE_REPURCHASE.businessCode().equals(jdLoanTransferPlan.getTransferType())) {
							
							/**
							 * 处理成功后发送，将任务放到MQ消息队列中
							 * 
							 * 	1. 创建MQ消息参数对象
							 * 	2. 将消息发送到MQ队列
							 */
							 MQParmsDomain message = new MQParmsDomain();
							 message.setTaskData(batchNo);
							 message.setTaskType(CmsBusinessStatus.CALLBACK_TRANSFER_PLAN_RESULT.businessCode());
							 message.setDelayLevel(Integer.parseInt(CmsBusinessStatus.MQ_DELAY_LEVEL_TWO.businessCode()));
							 jDMQProducerManager.send(message);
							return JdResponseView;
						} else if(JdResponseStatus.TRANSFER_TYPE_ONLINE.businessCode().equals(jdLoanTransferPlan.getTransferType())) {
							//线上扣款发送2311接口
							List<JdTransferPlansData> jdTransferPlansDataList = (List<JdTransferPlansData>)checkMap.get("dataList");
							return getTransferPlan(brNo, jdTransferPlansDataList, batchNo);
						}
					} else {//若校验失败则直接返回
						return JdResponseView;
					}

				} else {
					throw PlatformExceptionFactory.paramInValidateException(CmsBusinessStatus.INVALIDATE_PARAM)
							.build(errorMessList);
				}
			} catch (IOException e) {
				throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
			}
		} else {
			throw PlatformExceptionFactory.exception(JdResponseStatus.PROJ_UNEXISTS).build(JdResponseStatus.PROJ_UNEXISTS.businessMessage());
		}
		return JdResponseView;
	}
	
	/**
	 * 
	* @Title：transplanExam 
	* @Description：线下扣款校验
	* @param ：@param brNo
	* @param ：@param jdLoanTransferPlan
	* @param ：@param projNo
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	public Map<String, Object> checkRepayPlanNew(String brNo, JdLoanTransferPlan jdLoanTransferPlan, String projNo) {

		// 获取申请号
		String batchNo = jdLoanTransferPlan.getBatchNumber(); // 批次号
		String transferType = jdLoanTransferPlan.getTransferType(); // 扣款类型
		String paymentAgentType = jdLoanTransferPlan.getPaymentAgentType(); // 支付受托方类型
		/**
		 * 将请求转换成DATA对象
		 */
		List<JdTransferPlansData> jdTransferPlansDataList = jdLoanTransferPlan.getTransferPlans().stream().map(pojo -> {
			JdTransferPlansData jdTransferPlansData = BeanCopyUtils.INSTANCE.convertTo(pojo, JdTransferPlansData.class);
			jdTransferPlansData.setBatchNumber(batchNo);
			jdTransferPlansData.setCheckSts(JdResponseStatus.CHECK_STS.businessCode());
			jdTransferPlansData.setLoanSts(JdResponseStatus.LOAN_STS_DEFAULT.businessCode());
			jdTransferPlansData.setCreateDate(DateUtils.nowDateFormat());
			jdTransferPlansData.setCreateTime(DateUtils.nowTimeFormat());
			return jdTransferPlansData;
		}).collect(Collectors.toList());

		/**
		 * 校验并入库操作
		 */
		Map<String, Object> resultMap = checkBatchNoAndInsertNew(batchNo, transferType, paymentAgentType, brNo, projNo, jdTransferPlansDataList);
		
		return resultMap;
	}
	
	/**  
	* @Description
	* 			校验并入库
	*/  
	private Map<String, Object> checkBatchNoAndInsertNew(String batchNo, String transferType, String paymentAgentType, String brNo, String projNo,
			List<JdTransferPlansData> jdTransferPlansDataList) {
		JdResponseView jdResponseView = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String tradeNo = createKeyService.getJdTradeNo();
		//校验批次号
		JdTransferBatchData jdTransferBatchData =null;
		if(jdTransferBatchDataMapper.getByBatchNo(batchNo) != null) {
			jdResponseView = new JdResponseView(JdResponseStatus.REPATPLAN_BATCHNO_REPEAT.businessCode(),
					JdResponseStatus.REPATPLAN_BATCHNO_REPEAT.businessMessage(), tradeNo, "{}");
			resultMap.put("jdResponseView", jdResponseView);
			return resultMap;
		}else {
			//若批次号校验成功则插入批次表
			 jdTransferBatchData = new JdTransferBatchData();
			 jdTransferBatchData.setTransferType(transferType);
			 jdTransferBatchData.setPaymentAgentType(paymentAgentType);
			 jdTransferBatchData.setBatchNumber(batchNo);
			 jdTransferBatchData.setProjNo(projNo);
			 jdTransferBatchData.setCheckSts(JdResponseStatus.CHECK_STS.businessCode());
			 jdTransferBatchData.setCreateDate(DateUtils.nowDateFormat());
			 jdTransferBatchData.setCreateTime(DateUtils.nowTimeFormat());
			jdTransferBatchDataMapper.insert(jdTransferBatchData);
		}
		//插入明细表
		jdTransferPlansDataMapper.insert(jdTransferPlansDataList);
		
		/**
		 * 校验还款计划明细
		 */
		resultMap = checkTransferPlansNew(batchNo, brNo, projNo, tradeNo, jdTransferPlansDataList);
		
		return resultMap;
	}
	
	/**  
	* @Description
	* 			校验扣款计划上送明细
	*/  
	private Map<String, Object> checkTransferPlansNew(String batchNo, String brNo, String projNo, String tradeNo,
			List<JdTransferPlansData> jdTransferPlansDataList) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JdResponseView jdResponseView = null;

		for (JdTransferPlansData V : jdTransferPlansDataList) {
			String applicationNo = V.getApplicationNo();

			// 1. 校验贷款单不存在(通过申请号查询JD_LOAN_PAYMENT_STEP放款步骤表，是否存在记录)；
			List<JdLoanPaymentStepData> jdLoanPaymentStepData = jdLoanPaymentStepDataMapper
					.getByApplicationNo(applicationNo);
			if (jdLoanPaymentStepData.size() == 0) {// 返回贷款单不存在
				/**
				 * 更新扣款计划上送批次表及明细表	
				 */
				updateTransfer(batchNo, JdResponseStatus.CHECK_STS_FAIL.businessCode(), JdResponseStatus.NO_LOAN_PAYMENT.businessMessage());
				
				jdResponseView = new JdResponseView(JdResponseStatus.NO_LOAN_PAYMENT.businessCode(),
						JdResponseStatus.NO_LOAN_PAYMENT.businessMessage(), tradeNo, "{}");
				resultMap.put("jdResponseView", jdResponseView);
				return resultMap;
				
			}

			// 2. 校验贷款单已回购（根据申请号查询JD_REPURCHASE_APPLY回购申请表，是否存在回购中或回购成功的申请
			List<JdRepurchaseApplyData> jdRepurchaseApplyList = jdRepurchaseApplyDataMapper
					.getByApplicationNoAndSts(applicationNo);
			if (jdRepurchaseApplyList.size() > 0) {// 返回贷款单已回购
				/**
				 * 更新扣款计划上送批次表及明细表	
				 */
				updateTransfer(batchNo, JdResponseStatus.CHECK_STS_FAIL.businessCode(), JdResponseStatus.PAYMENT_REPURCHASED.businessMessage());
				
				jdResponseView = new JdResponseView(JdResponseStatus.PAYMENT_REPURCHASED.businessCode(),
						JdResponseStatus.PAYMENT_REPURCHASED.businessMessage(), tradeNo, "{}");
				resultMap.put("jdResponseView", jdResponseView);
				return resultMap;
			}
			
		}
		// 3.  拼报文调小微校验接口
		resultMap = transferPlan2318Service.request2318(brNo, tradeNo,jdTransferPlansDataList, batchNo);
		resultMap.put("dataList", jdTransferPlansDataList);
		resultMap.put("batchNo", batchNo);
		return resultMap;
	}

}
