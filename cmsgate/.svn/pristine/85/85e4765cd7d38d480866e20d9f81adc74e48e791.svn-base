package com.uranus.platform.business.jd.service.trans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.business.jd.callback.JdCallbackHttpClient;
import com.uranus.platform.business.jd.dao.JdLoanPaymentStepDataMapper;
import com.uranus.platform.business.jd.entity.po.JdLoanPaymentStepData;
import com.uranus.platform.business.jd.entity.pojo.JDCallbackResponse2102;
import com.uranus.platform.business.jd.entity.pojo.JDLoanStepExecuteResult;
import com.uranus.platform.business.jd.entity.pojo.JdResponse2102;
import com.uranus.platform.business.jd.entity.status.JdResponseStatus;
import com.uranus.platform.business.jd.entity.vo.JdCallbackRequestView;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;
import com.uranus.platform.business.pub.dao.CallbackFailLogDataMapper;
import com.uranus.platform.business.pub.entity.dto.Request2102Dto;
import com.uranus.platform.business.pub.entity.dto.Response2102AcListDto;
import com.uranus.platform.business.pub.entity.dto.Response2102ListDto;
import com.uranus.platform.business.pub.entity.po.CallbackFailLogData;
import com.uranus.platform.business.ws.search.Request;
import com.uranus.platform.business.ws.search.Response;
import com.uranus.platform.business.ws.service.SearchService;
import com.uranus.platform.utils.exception.PlatformExceptionFactory;
import com.uranus.platform.utils.status.CmsBusinessStatus;
import com.uranus.tools.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Describe: 向小微2102进件批量申请查询接口发送请求，并且向京东返回响应数据
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月23日 上午11:39:50
 * 
 */
@Service
@Slf4j
public class LnPaymentsQueryTrans2102Service {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private SearchService searchService;
	@Autowired
	private JdCallbackHttpClient jdCallbackHttpClient;
	@Autowired
	private JdLoanPaymentStepDataMapper jdLoanPaymentStepDataMapper;
	@Autowired
	private CallbackFailLogDataMapper callbackFailLogDataMapper;
	/**
	 * @Description 拼装报文、请求小微、转换响应
	 * @param tradeNo 交易流水号
	 * @param brNo    合作机构编号
	 * @param projNo  信托项目编号
	 * @param Data    京东数据
	 * @return 京东响应数据、京东业务数据
	 */
	public JdCallbackRequestView request2102(String brNo, String batchNo, String tradeNo, String pactNo, String projNo, int delayLevel) {
		// 拼发送小微业务数据
		Request request2102 = getRequest2102(batchNo, brNo, tradeNo, pactNo);  
		// 发送小微系统
		Response response2102 = searchService.search(CmsBusinessStatus.MFS.businessCode(), request2102); 
		// 转换小微的响应为京东所需响应数据
		JdCallbackRequestView jdCallbackRequestView = transLnPaymentsQueryResponse(brNo, response2102, projNo, pactNo, delayLevel);

		return jdCallbackRequestView;
	}
 
	/**  
	* @Description
	* 			拼请求小微报文
	*/  
	private Request getRequest2102(String batchNo, String brNo, String tradeNo, String pactNo) {
		Request2102Dto requestDto = new Request2102Dto();
		
	    //request请求赋值
 		requestDto.setBatchNo(batchNo);
 		requestDto.setBrNo(brNo);
 		requestDto.setPactNo(pactNo);
 		
        //发送request请求给小微
 		Request request = new Request();
 		request.setTxCode(CmsBusinessStatus.WS_2102.businessCode()); // 接口编号
 		request.setBrNo(brNo); // 机构号
 		request.setReqDate(DateUtils.nowDateFormat()); // 设置请求日期
 		request.setToken("test"); // 设置token
 		request.setReqTime(DateUtils.nowTimeFormat()); // 设置请求时间
 		request.setReqSerial(tradeNo); // 设置请求流水号
 		try {
 			request.setContent(objectMapper.writeValueAsString(requestDto));
 		} catch (JsonProcessingException e) {
 			throw PlatformExceptionFactory.jsonParseException(JdResponseStatus.UNKNOWN_ERROR).build(e);
 		}
 		return request;
	}

	/**  
	* @Description
	* 			判断并转换小微响应
	*/  
	private JdCallbackRequestView transLnPaymentsQueryResponse(String brNo, Response response2102,
			String projNo, String pactNo, int delayLevel) {
		JdCallbackRequestView jdCallbackRequestView = null;
		try {
			if(response2102 != null) {
				// 如果小微响应成功
				if(CmsBusinessStatus.MFS_SUCCESS.businessCode().equals(response2102.getRespCode())) {
					String mfsContent = response2102.getContent();
					if(mfsContent != null || !"".equals(mfsContent)) {
						List<Response2102ListDto> responseDto = objectMapper.readValue(response2102.getContent(), new TypeReference<List<Response2102ListDto>>() {});
						//查询成功， 小微正常返回数据
						if(responseDto != null && responseDto.size() > 0) {
							Response2102ListDto response2102List = responseDto.get(0);
							// 查询成功，放款成功
							if(CmsBusinessStatus.PAYMENTS_SUCCESS.businessCode().equals(response2102List.getDealSts())) {
								// 更新放款步骤表状态
								JdLoanPaymentStepData loanPaymentStep = new JdLoanPaymentStepData();
								loanPaymentStep.setApplicationNo(pactNo);
								loanPaymentStep.setLoanSts(JdResponseStatus.LOAN_STS_SUCCESS.businessCode());
								loanPaymentStep.setUpDate(DateUtils.nowDateFormat());
								loanPaymentStep.setUpTime(DateUtils.nowTimeFormat());
								jdLoanPaymentStepDataMapper.updatePayment(loanPaymentStep);
								
								// 查询成功，放款成功，拼京东响应数据并回调京东成功
								jdCallbackRequestView = transJDDate(pactNo, response2102List, response2102List.getDealSts());
							
							} else if(CmsBusinessStatus.PAYMENTS_WORKING.businessCode().equals(response2102List.getDealSts())
									|| CmsBusinessStatus.PAYMENTS_READY.businessCode().equals(response2102List.getDealSts())) {
								/**
								 * 查询成功，放款中， 重新放回到MQ再次查询
								 */
								jdCallbackHttpClient.callbackFailAndSendMQ(pactNo, delayLevel, CmsBusinessStatus.WS_2102.businessCode(), CmsBusinessStatus.CALLBACK_LOAN_PAYMENTS_RESULT.businessCode());
								return null;
							} else {// 查询失败， 放款失败
								jdCallbackRequestView = transJDDate(pactNo, response2102List, response2102List.getDealSts());
								
								// 更新放款步骤表状态
								JdLoanPaymentStepData loanPaymentStep = new JdLoanPaymentStepData();
								loanPaymentStep.setApplicationNo(pactNo);
								loanPaymentStep.setLoanSts(JdResponseStatus.LOAN_STS_FAIL.businessCode());
								loanPaymentStep.setUpDate(DateUtils.nowDateFormat());
								loanPaymentStep.setUpTime(DateUtils.nowTimeFormat());
								jdLoanPaymentStepDataMapper.updatePayment(loanPaymentStep);
							}
						} else {
							//小微查询成功，但是没有查询到数据
							log.info("根据批次号查询记录失败，未查询到数据，{}，申请号：" + pactNo);
							CallbackFailLogData callbackFailLogData = new CallbackFailLogData(pactNo, 
									CmsBusinessStatus.JD.businessCode(), CmsBusinessStatus.WS_2102.businessCode(), 
									DateUtils.nowDateFormat(), DateUtils.nowTimeFormat());
							callbackFailLogDataMapper.insert(callbackFailLogData);
							return null;
						}
					} else {
						//小微查询成功，但是没有查询到数据
						log.info("根据批次号查询记录失败，未查询到数据，{}，申请号：" + pactNo);
						CallbackFailLogData callbackFailLogData = new CallbackFailLogData(pactNo, 
								CmsBusinessStatus.JD.businessCode(), CmsBusinessStatus.WS_2102.businessCode(), 
								DateUtils.nowDateFormat(), DateUtils.nowTimeFormat());
						callbackFailLogDataMapper.insert(callbackFailLogData);
						return null;
					}
				} else {
					// 更新放款步骤表状态
					JdLoanPaymentStepData loanPaymentStep = new JdLoanPaymentStepData();
					loanPaymentStep.setApplicationNo(pactNo);
					loanPaymentStep.setLoanSts(JdResponseStatus.LOAN_STS_FAIL.businessCode());
					loanPaymentStep.setUpDate(DateUtils.nowDateFormat());
					loanPaymentStep.setUpTime(DateUtils.nowTimeFormat());
					jdLoanPaymentStepDataMapper.updatePayment(loanPaymentStep);
					//小微返回失败，直接将此信息回调给京东
					jdCallbackRequestView = transJDDate(pactNo);
				}
			} else {
				// 小微响应失败，需要重新放回MQ
				jdCallbackHttpClient.callbackFailAndSendMQ(pactNo, delayLevel, CmsBusinessStatus.WS_2102.businessCode(), CmsBusinessStatus.CALLBACK_LOAN_PAYMENTS_RESULT.businessCode());
				return null;
			}
		} catch (IOException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		return jdCallbackRequestView;
	
	}

	/**  
	* @Description
	* 			拼小微返回失败。京东响应数据
	*/  
	private JdCallbackRequestView transJDDate(String pactNo) {
		// 查询放款步骤编号
		JdLoanPaymentStepData jdLoanPaymentStepData = jdLoanPaymentStepDataMapper.getByApplicationNo1(pactNo);
		/**
		 * 拼京东所需放款步骤信息
		 */
		JDLoanStepExecuteResult jDLoanStepExecuteResult = new JDLoanStepExecuteResult();
		jDLoanStepExecuteResult.setApplicationNo(pactNo);
		jDLoanStepExecuteResult.setPaymentNo(jdLoanPaymentStepData.getPaymentNo());
		jDLoanStepExecuteResult.setPaymentAmount(jdLoanPaymentStepData.getPaymentAmount());
		jDLoanStepExecuteResult.setPaymentStatus(JdResponseStatus.CALLBACK_PAY_UNSUCCESS.businessCode());
		jDLoanStepExecuteResult.setPaymentTime("");
		jDLoanStepExecuteResult.setPaymentTradeNo("");
		jDLoanStepExecuteResult.setMessage("");
		
		List<JDLoanStepExecuteResult> list = new ArrayList<JDLoanStepExecuteResult>();
		list.add(jDLoanStepExecuteResult);
		
		JDCallbackResponse2102 jDCallbackResponse2102 = new JDCallbackResponse2102();
		jDCallbackResponse2102.setApplicationNo(pactNo);
		jDCallbackResponse2102.setLoanStepExecuteResults(list);
		
		JdCallbackRequestView jdCallbackRequestView = null;
		try {
			// 拼京东回调报文
			jdCallbackRequestView = new JdCallbackRequestView(objectMapper.writeValueAsString(jDCallbackResponse2102));
		} catch (IOException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		return jdCallbackRequestView;
	}

	/**  
	* @Description
	* 			拼小微查询成功，放款成功或失败，京东回调数据
	*/  
	private JdCallbackRequestView transJDDate(String pactNo, Response2102ListDto response2102List, String dealSts) {
		// 查询放款步骤编号
		JdLoanPaymentStepData jdLoanPaymentStepData = jdLoanPaymentStepDataMapper.getByApplicationNo1(pactNo);
		// 小微返回的放款账号信息
		Response2102AcListDto Response2102Ac = response2102List.getListAc().get(0);
		
		/**
		 * 拼京东所需放款步骤信息
		 */
		JDLoanStepExecuteResult jDLoanStepExecuteResult = new JDLoanStepExecuteResult();
		jDLoanStepExecuteResult.setApplicationNo(pactNo);
		jDLoanStepExecuteResult.setPaymentNo(jdLoanPaymentStepData.getPaymentNo());
		jDLoanStepExecuteResult.setPaymentAmount(Response2102Ac.getAcAmt());
		if(CmsBusinessStatus.PAYMENTS_SUCCESS.businessCode().equals(dealSts)) {// 如果成功
			jDLoanStepExecuteResult.setPaymentStatus(JdResponseStatus.CALLBACK_PAY_SUCCESS.businessCode());
			jDLoanStepExecuteResult.setPaymentTime(response2102List.getPayDate());
			jDLoanStepExecuteResult.setPaymentTradeNo(CmsBusinessStatus.PAY_SERIAL_NO_PREFIX.businessCode() + Response2102Ac.getSerialNo());
		} else {
			jDLoanStepExecuteResult.setPaymentStatus(JdResponseStatus.CALLBACK_PAY_UNSUCCESS.businessCode());
			jDLoanStepExecuteResult.setPaymentTime("");
			jDLoanStepExecuteResult.setPaymentTradeNo("");
		}
		jDLoanStepExecuteResult.setMessage("");
		
		List<JDLoanStepExecuteResult> list = new ArrayList<JDLoanStepExecuteResult>();
		list.add(jDLoanStepExecuteResult);
		
		JDCallbackResponse2102 jDCallbackResponse2102 = new JDCallbackResponse2102();
		jDCallbackResponse2102.setApplicationNo(pactNo);
		jDCallbackResponse2102.setLoanStepExecuteResults(list);
		
		JdCallbackRequestView jdCallbackRequestView = null;
		try {
			// 拼京东回调报文
			jdCallbackRequestView = new JdCallbackRequestView(objectMapper.writeValueAsString(jDCallbackResponse2102));
		} catch (IOException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		return jdCallbackRequestView;
	}

	/**  
	* @Description
	* 			放款结果查询接口
	*/  
	public JdResponseView request2102(String brNo, String batchNo, String tradeNo, String applicationNo) {
		// 拼发送小微业务数据
		Request request2102 = getRequest2102(batchNo, brNo, tradeNo, applicationNo);  
		// 发送小微系统
		Response response2102 = searchService.search(CmsBusinessStatus.MFS.businessCode(), request2102); 
		// 转换小微的响应为京东所需响应数据
		return transQuery2102Response(brNo, response2102, tradeNo, applicationNo);
	}

	/**  
	* @Description
	* 			放款结果查询接口
	* 				将小微2102的响应转换成京东放款结果查询接口所需响应
	*/  
	private JdResponseView transQuery2102Response(String brNo, Response response2102, String tradeNo, String pactNo) {
		JdResponseView jdResponseView = null;
		try {
			if(response2102 != null) {
				// 如果小微响应成功
				if(CmsBusinessStatus.MFS_SUCCESS.businessCode().equals(response2102.getRespCode())) {
					String mfsContent = response2102.getContent();
					if(mfsContent != null || !"".equals(mfsContent)) {
						List<Response2102ListDto> responseDto = objectMapper.readValue(response2102.getContent(), new TypeReference<List<Response2102ListDto>>() {});
						//查询成功， 小微正常返回数据
						if(responseDto != null && responseDto.size() > 0) {
							Response2102ListDto response2102List = responseDto.get(0);
							// 查询成功，放款成功
							if(CmsBusinessStatus.PAYMENTS_SUCCESS.businessCode().equals(response2102List.getDealSts())) {
								// 更新放款步骤表状态
								JdLoanPaymentStepData loanPaymentStep = new JdLoanPaymentStepData();
								loanPaymentStep.setApplicationNo(pactNo);
								loanPaymentStep.setLoanSts(JdResponseStatus.LOAN_STS_SUCCESS.businessCode());
								loanPaymentStep.setUpDate(DateUtils.nowDateFormat());
								loanPaymentStep.setUpTime(DateUtils.nowTimeFormat());
								jdLoanPaymentStepDataMapper.updatePayment(loanPaymentStep);
								
								// 查询成功，放款成功，拼京东响应数据
								jdResponseView = trans2102JDDate(pactNo, response2102List, response2102List.getDealSts(), tradeNo);
							
							} else if(CmsBusinessStatus.PAYMENTS_WORKING.businessCode().equals(response2102List.getDealSts())
									|| CmsBusinessStatus.PAYMENTS_READY.businessCode().equals(response2102List.getDealSts())) {
								// 查询成功，处理中
								jdResponseView = trans2102JDDate(pactNo, response2102List, response2102List.getDealSts(), tradeNo);
							} else {// 查询失败， 放款失败
								jdResponseView = trans2102JDDate(pactNo, response2102List, response2102List.getDealSts(), tradeNo);
								
								// 更新放款步骤表状态
								JdLoanPaymentStepData loanPaymentStep = new JdLoanPaymentStepData();
								loanPaymentStep.setApplicationNo(pactNo);
								loanPaymentStep.setLoanSts(JdResponseStatus.LOAN_STS_FAIL.businessCode());
								loanPaymentStep.setUpDate(DateUtils.nowDateFormat());
								loanPaymentStep.setUpTime(DateUtils.nowTimeFormat());
								jdLoanPaymentStepDataMapper.updatePayment(loanPaymentStep);
							}
						} else {
							// 小微响应成功，但是没有查询到数据
							jdResponseView = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(),
									JdResponseStatus.DATA_ERROR.businessMessage(), tradeNo, "{}");
						}
					} else {
						// 小微响应成功，但是没有查询到数据
						jdResponseView = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(),
								JdResponseStatus.DATA_ERROR.businessMessage(), tradeNo, "{}");
					}
				} else {
					// 更新放款步骤表状态
					JdLoanPaymentStepData loanPaymentStep = new JdLoanPaymentStepData();
					loanPaymentStep.setApplicationNo(pactNo);
					loanPaymentStep.setLoanSts(JdResponseStatus.LOAN_STS_FAIL.businessCode());
					loanPaymentStep.setUpDate(DateUtils.nowDateFormat());
					loanPaymentStep.setUpTime(DateUtils.nowTimeFormat());
					jdLoanPaymentStepDataMapper.updatePayment(loanPaymentStep);
					//小微返回失败，直接将此信息回调给京东
					jdResponseView = transJDRejectDate(pactNo, tradeNo);
				}
			} else {
				// 小微响应失败
				jdResponseView = new JdResponseView(JdResponseStatus.UNKNOWN_ERROR.businessCode(), JdResponseStatus.UNKNOWN_ERROR.businessMessage(), tradeNo,
						"{}");
			}
		} catch (IOException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		return jdResponseView;
	}

	/**  
	* @Description
	* 		小微响应为放款成功、放款失败、处理中的响应
	*/  
	private JdResponseView trans2102JDDate(String pactNo, Response2102ListDto response2102List, String dealSts, String tradeNo) {
		// 查询放款步骤编号
		JdLoanPaymentStepData jdLoanPaymentStepData = jdLoanPaymentStepDataMapper.getByApplicationNo1(pactNo);
		// 小微返回的放款账号信息
		Response2102AcListDto Response2102Ac = response2102List.getListAc().get(0);
		
		/**
		 * 拼京东所需放款步骤信息
		 */
		JDLoanStepExecuteResult jDLoanStepExecuteResult = new JDLoanStepExecuteResult();
		jDLoanStepExecuteResult.setApplicationNo(pactNo);
		jDLoanStepExecuteResult.setPaymentNo(jdLoanPaymentStepData.getPaymentNo());
		jDLoanStepExecuteResult.setPaymentAmount(Response2102Ac.getAcAmt());
		if(CmsBusinessStatus.PAYMENTS_SUCCESS.businessCode().equals(dealSts)) {// 如果成功
			jDLoanStepExecuteResult.setPaymentStatus(JdResponseStatus.CALLBACK_PAY_SUCCESS.businessCode());
			jDLoanStepExecuteResult.setPaymentTime(response2102List.getPayDate());
			jDLoanStepExecuteResult.setPaymentTradeNo(CmsBusinessStatus.PAY_SERIAL_NO_PREFIX.businessCode() + Response2102Ac.getSerialNo());
		} else if(CmsBusinessStatus.PAYMENTS_WORKING.businessCode().equals(dealSts)
				|| CmsBusinessStatus.PAYMENTS_READY.businessCode().equals(dealSts)){
			jDLoanStepExecuteResult.setPaymentStatus(JdResponseStatus.CALLBACK_PAY_DEALING.businessCode());
			jDLoanStepExecuteResult.setPaymentTime("");
			jDLoanStepExecuteResult.setPaymentTradeNo("");
		} else{
			jDLoanStepExecuteResult.setPaymentStatus(JdResponseStatus.CALLBACK_PAY_UNSUCCESS.businessCode());
			jDLoanStepExecuteResult.setPaymentTime("");
			jDLoanStepExecuteResult.setPaymentTradeNo("");
		}
		jDLoanStepExecuteResult.setMessage("");
		
		List<JDLoanStepExecuteResult> list = new ArrayList<JDLoanStepExecuteResult>();
		list.add(jDLoanStepExecuteResult);
		
		JdResponse2102 jdResponse2102 = new JdResponse2102();
		jdResponse2102.setErrorMessages(new ArrayList<String>());
		jdResponse2102.setLoanStepExecuteResults(list);
		
		JdResponseView jdResponseView = null;
		try {
			// 拼京东回调报文
			jdResponseView = new JdResponseView(JdResponseStatus.SUCCESS.businessCode(),
					JdResponseStatus.SUCCESS.businessMessage(), tradeNo, objectMapper.writeValueAsString(jdResponse2102));
		} catch (IOException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		return jdResponseView;
	}

	/**  
	* @Description
	* 			小微放款失败，将小微响应转换成京东所需响应
	*/  
	private JdResponseView transJDRejectDate(String pactNo, String tradeNo) {
		// 查询放款步骤编号
		JdLoanPaymentStepData jdLoanPaymentStepData = jdLoanPaymentStepDataMapper.getByApplicationNo1(pactNo);
		/**
		 * 拼京东所需放款步骤信息
		 */
		JDLoanStepExecuteResult jDLoanStepExecuteResult = new JDLoanStepExecuteResult();
		jDLoanStepExecuteResult.setApplicationNo(pactNo);
		jDLoanStepExecuteResult.setPaymentNo(jdLoanPaymentStepData.getPaymentNo());
		jDLoanStepExecuteResult.setPaymentAmount(jdLoanPaymentStepData.getPaymentAmount());
		jDLoanStepExecuteResult.setPaymentStatus(JdResponseStatus.CALLBACK_PAY_UNSUCCESS.businessCode());
		jDLoanStepExecuteResult.setPaymentTime("");
		jDLoanStepExecuteResult.setPaymentTradeNo("");
		jDLoanStepExecuteResult.setMessage("");
		
		List<JDLoanStepExecuteResult> list = new ArrayList<JDLoanStepExecuteResult>();
		list.add(jDLoanStepExecuteResult);
		
		JdResponse2102 jdResponse2102 = new JdResponse2102();
		jdResponse2102.setErrorMessages(new ArrayList<String>());
		jdResponse2102.setLoanStepExecuteResults(list);
		
		JdResponseView jdResponseView = null;
		try {
			// 拼京东回调报文
			jdResponseView = new JdResponseView(JdResponseStatus.SUCCESS.businessCode(),
					JdResponseStatus.SUCCESS.businessMessage(), tradeNo, objectMapper.writeValueAsString(jdResponse2102));
		} catch (IOException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		return jdResponseView;
	}

}
