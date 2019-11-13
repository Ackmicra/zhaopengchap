package com.uranus.platform.business.jd.service.trans;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.business.jd.dao.JdPaymentOrderDataMapper;
import com.uranus.platform.business.jd.dao.JdTransferPlansDataMapper;
import com.uranus.platform.business.jd.entity.po.JdPaymentOrderData;
import com.uranus.platform.business.jd.entity.po.JdTransferPlansData;
import com.uranus.platform.business.jd.entity.pojo.JDPaymentOrderResponse;
import com.uranus.platform.business.jd.entity.pojo.JDPaymentOrders;
import com.uranus.platform.business.jd.entity.pojo.JdPayments;
import com.uranus.platform.business.jd.entity.pojo.JdResponse2313;
import com.uranus.platform.business.jd.entity.status.JdResponseStatus;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;
import com.uranus.platform.business.pub.entity.dto.Request2313Dto;
import com.uranus.platform.business.pub.entity.dto.Response2313Dto;
import com.uranus.platform.business.pub.entity.dto.Response2313List;
import com.uranus.platform.business.pub.entity.po.ParmDicData;
import com.uranus.platform.business.pub.service.ParmDicService;
import com.uranus.platform.business.ws.search.Request;
import com.uranus.platform.business.ws.search.Response;
import com.uranus.platform.business.ws.service.SearchService;
import com.uranus.platform.utils.exception.PlatformExceptionFactory;
import com.uranus.platform.utils.status.CmsBusinessStatus;
import com.uranus.tools.beans.converter.FormatConverter;
import com.uranus.tools.utils.DateUtils;

/**
 * @Description: 扣款查询相关接口
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年9月14日下午12:32:34
 */
@Service
public class TransferPlans2313Service {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private SearchService searchService;
	@Autowired
	private JdTransferPlansDataMapper jdTransferPlansDataMapper;
	@Autowired
	private ParmDicService parmDicService;
	@Autowired
	JdPaymentOrderDataMapper jdPaymentOrderDataMapper;
	
	/**  
	* @Description
	* 			扣款计划结果查询接口
	*/  
	public JdResponseView queryFor2313(String batchNum, String brNo, String transferType, 
			String tradeNo, String checkSts, String checkDesc) {
		// 如果是扣款计划校验成功，并且是线上扣款
		if(JdResponseStatus.CHECK_STS_SUCCESS.businessCode().equals(checkSts) && 
				JdResponseStatus.TRANSFER_TYPE_ONLINE.businessCode().equals(transferType)) {
			return transOnLine(batchNum, brNo, tradeNo, transferType);
		} else {
			// 线上扣款扣款计划校验失败，或者线下扣款
			return transOutLine(batchNum, tradeNo, checkSts, checkDesc,brNo,transferType);
		}
	}

	/**  
	* @Description
	* 			扣款计划结果查询【线上扣款扣款计划校验失败，或者线下扣款】处理
	*/  
	private JdResponseView transOutLine(String batchNum, String tradeNo, 
			String checkSts, String checkDesc,String brNo,String transferType) {
		JdResponse2313 jdResponse2313 = new JdResponse2313();
		JdResponseView jdResponseView = null;
		try {
			JdTransferPlansData jdTransferPlansData = jdTransferPlansDataMapper.getByBatchNo(batchNum).get(0);
			
			if (JdResponseStatus.LOAN_SUCCESS.businessCode().equals(jdTransferPlansData.getLoanSts())
					|| JdResponseStatus.LOAN_FAIL.businessCode().equals(jdTransferPlansData.getLoanSts())) {
					//贷款状态为扣款失败或者成功返回支付信息
				jdResponseView = transOnLine(batchNum, brNo, tradeNo, transferType);
			} else {

				//初始化处理中返回校验结果 校验成功返回检验结果—校验成功，扣款计划执行结果-未执行
				jdResponse2313.setBatchNum(batchNum);
				if (JdResponseStatus.CHECK_STS_SUCCESS.businessCode().equals(checkSts)) {

					jdResponse2313.setValidateStatus(JdResponseStatus.CHECK_SUCCESS.businessCode()); // 为校验成功
					jdResponse2313.setDealStatus(JdResponseStatus.DEAL_STATUS_SUCCESS.businessCode()); // 未执行

				} else if (JdResponseStatus.CHECK_STS_FAIL.businessCode().equals(checkSts)) {

					jdResponse2313.setValidateStatus(JdResponseStatus.CHECK_FAILED.businessCode()); // 为校验失败
					jdResponse2313.setDealStatus(JdResponseStatus.DEAL_STATUS_FAILED.businessCode()); // 未执行

				} else {
					jdResponse2313.setValidateStatus(checkSts); //
				}
				jdResponse2313.setMessage(checkDesc);
				jdResponse2313.setChargeDate(0L);
				jdResponse2313.setChargeAmount(0.0);
				jdResponse2313.setPayments(new ArrayList<>());
				
				jdResponseView = new JdResponseView(JdResponseStatus.SUCCESS.businessCode(),
					JdResponseStatus.SUCCESS.businessMessage(), tradeNo, objectMapper.writeValueAsString(jdResponse2313));
			}	
			
		} catch (IOException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		return jdResponseView;
	}

	/**  
	* @Description
	* 			扣款计划结果查询【如果是扣款计划校验成功，并且是线上扣款】处理
	*/  
	public JdResponseView transOnLine(String batchNum, String brNo, String tradeNo, String transferType) {
		// 拼发送小微业务数据
		Request request = request2313(brNo, batchNum, tradeNo, transferType );

		// 发送小微系统
		Response response = searchService.search(CmsBusinessStatus.MFS.businessCode(), request);

		// 转换小微的响应为京东所需响应数据
		return transferPlansResponse(batchNum, response, tradeNo,transferType);
	}

	/**  
	* @Description
	* 			扣款计划处理结果查询接口转换京东响应
	*/  
	private JdResponseView transferPlansResponse(String batchNum, Response response, String tradeNo,String transferType) {
		JdResponseView jdResponseView = null;
		try {
			if (response != null) {
				// 判断业务是否成功
				if (CmsBusinessStatus.MFS_SUCCESS.businessCode().equals(response.getRespCode())) {
					/**
					 * 何为成功
					 * 	1. 返回码response.getRespCode()为0000
					 * 	2. 总记录数pageCounts大于0
					 * 	3. 批次里的处理状态dealSts为02-处理成功或04-处理失败
					 */
					String mfsContent = response.getContent();
					if(mfsContent != null || !"".equals(mfsContent)) {
						Response2313Dto response2313Dto = objectMapper.readValue(response.getContent(), Response2313Dto.class);
						if(response2313Dto.getPageCounts() > 0) {
							/**
							 * 处理小微响应
							 */
							jdResponseView = responseTrans(batchNum, response2313Dto, tradeNo,transferType);
							
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
					/**
					 *  小微响应为失败
					 */
					JdResponse2313 jdResponse2313 = new JdResponse2313();
					jdResponse2313.setBatchNum(batchNum);
					jdResponse2313.setValidateStatus(JdResponseStatus.CHECK_FAILED.businessCode());// 校验失败
					jdResponse2313.setValidateMsg(response.getRespDesc());
					jdResponse2313.setChargeAmount(0.0);
					jdResponse2313.setDealStatus(JdResponseStatus.DEAL_STATUS_FAILED.businessCode()); //成功
					jdResponse2313.setMessage("");
					
					jdResponseView = new JdResponseView(JdResponseStatus.SUCCESS.businessCode(),
							JdResponseStatus.SUCCESS.businessMessage(), tradeNo, objectMapper.writeValueAsString(jdResponse2313));
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
	* 		转换小微2313接口返回响应
	*/  
	private JdResponseView responseTrans(String batchNum, Response2313Dto response2313Dto, String tradeNo,String transferType) {
		JdResponseView jdResponseView = null;
		JdPayments jdPayments = new JdPayments();
		JdResponse2313 jdResponse2313 = new JdResponse2313();
		
		List<JdPayments> JdPaymentList = new ArrayList<JdPayments>();
		Response2313List response2313 = response2313Dto.getList().get(0);
		
		double paymentAmt = 0.00;
		for(Response2313List response2313_1 : response2313Dto.getList()) {
			paymentAmt += response2313_1.getPaymentAmount();
		}
		
		try {
			// 如果查询结果为扣款成功
			if (JdResponseStatus.DEAL_STS_SUCCESS.businessCode().equals(response2313.getDealSts())) {
				
				// 扣款成功后更新扣款表状态为扣款成功
				JdTransferPlansData jdTransferPlansData = new JdTransferPlansData();
				
				jdTransferPlansData.setBatchNumber(batchNum);
				jdTransferPlansData.setUpDate(DateUtils.nowDateFormat());
				jdTransferPlansData.setUpTime(DateUtils.nowTimeFormat());
				jdTransferPlansData.setLoanSts(JdResponseStatus.LOAN_SUCCESS.businessCode());
				jdTransferPlansDataMapper.updateLoanResultByBatchNo(jdTransferPlansData);
				
				long paymentTime; 
				if (JdResponseStatus.TRANSFER_TYPE_ONLINE.businessCode().equals(transferType)) {
					// 线上扣款支付信息从2313返回取

					jdPayments.setPaymentTradeNo(response2313.getPaymentTradeNo());
					jdPayments.setPaymentAmount(paymentAmt);
					LocalDateTime time = FormatConverter.dateTimeFormat().convertValue("yyyyMMdd HH:mm:ss",response2313.getPaymentTime());
					 paymentTime = FormatConverter.dateTime2Stamp().convertValue(null,time);
					
					jdPayments.setPaymentTime(paymentTime);
					jdPayments.setAccountNo(response2313.getAccountNo());
					jdPayments.setAccountName(response2313.getAccountName());
					ParmDicData parmData= parmDicService.getParmDicByMateCode("BANK_CODE", response2313.getBankName());
					if(parmData == null) {
						
					jdPayments.setBankName(response2313.getBankName());
					}else {
						jdPayments.setBankName(parmData.getOptName());
					}

				} else {
					// 线下支付信息从支付订单取
					JdPaymentOrderData jdPaymentOrderData1 = new JdPaymentOrderData();
					jdPaymentOrderData1.setBatchNumber(batchNum);
					jdPaymentOrderData1.setTransferType(transferType);
					JdPaymentOrderData jdPaymentOrderData =  jdPaymentOrderDataMapper.getByJDBatchNo(jdPaymentOrderData1);
//					jdPayments.setPaymentChannel(Integer.valueOf(jdPaymentOrderData.getPaymentChannel()));
					jdPayments.setPaymentTradeNo(jdPaymentOrderData.getTradeNo());
					jdPayments.setPaymentAmount(jdPaymentOrderData.getAmount());
					paymentTime= jdPaymentOrderData.getTradeTime();
					jdPayments.setPaymentTime(jdPaymentOrderData.getTradeTime());
					jdPayments.setAccountNo(jdPaymentOrderData.getPayeeAccountNo());
					jdPayments.setAccountName(jdPaymentOrderData.getPayeeAccountName());
					ParmDicData parmData= parmDicService.getParmDic("BANK_CODE", jdPaymentOrderData.getPayerBankCode());
					if(parmData == null) {
						
					jdPayments.setBankName(jdPaymentOrderData.getPayerBankCode());
					}else {
						jdPayments.setBankName(parmData.getOptName());
					}

				}

				JdPaymentList.add(jdPayments);
				// 处理成功返回校验状态-支付成功，扣款计划执行结果-成功
				jdResponse2313.setBatchNum(batchNum);
				jdResponse2313.setValidateStatus(JdResponseStatus.PAY_SUCCESS.businessCode());// 校验成功
				jdResponse2313.setValidateMsg(JdResponseStatus.PAY_SUCCESS.businessMessage());
				
//				LocalDateTime time = FormatConverter.dateTimeFormat().convertValue("yyyyMMdd HH:mm:ss",response2313.getPaymentTime());
//				paymentTime = FormatConverter.dateTime2Stamp().convertValue(null,time);
				jdResponse2313.setChargeDate(paymentTime);
				
				jdResponse2313.setChargeAmount(paymentAmt);
				jdResponse2313.setDealStatus(JdResponseStatus.DEAL_STATUS_SUCCESS.businessCode()); //成功
				jdResponse2313.setMessage("");
				jdResponse2313.setPayments(JdPaymentList);
				
				/**
				 *  京东响应
				 */
				jdResponseView = new JdResponseView(JdResponseStatus.SUCCESS.businessCode(),
						JdResponseStatus.SUCCESS.businessMessage(), tradeNo, objectMapper.writeValueAsString(jdResponse2313));
			} else if (JdResponseStatus.DEAL_STS_FAILED.businessCode().equals(response2313.getDealSts()) 
					|| JdResponseStatus.SERVER_EXCEPTION.businessCode().equals(response2313.getDealSts())) {
				
				// 扣款失败后更新扣款表状态为扣款失败
				JdTransferPlansData jdTransferPlansData = new JdTransferPlansData();
				
				jdTransferPlansData.setBatchNumber(batchNum);
				jdTransferPlansData.setUpDate(DateUtils.nowDateFormat());
				jdTransferPlansData.setUpTime(DateUtils.nowTimeFormat());
				jdTransferPlansData.setLoanSts(JdResponseStatus.LOAN_FAIL.businessCode());
				jdTransferPlansDataMapper.updateLoanResultByBatchNo(jdTransferPlansData);
				
				// 其他状态无支付信息
				// 处理失败返回校验状态-支付失败，扣款计划执行结果-失败
				jdResponse2313.setBatchNum(batchNum);
				jdResponse2313.setValidateStatus(JdResponseStatus.PAY_FAILED.businessCode());// 支付失败
				jdResponse2313.setValidateMsg(JdResponseStatus.PAY_FAILED.businessMessage());
				jdResponse2313.setChargeAmount(paymentAmt);
				jdResponse2313.setDealStatus(JdResponseStatus.DEAL_STATUS_FAILED.businessCode()); // 失败
				jdResponse2313.setMessage(response2313.getDealDesc());
				jdResponse2313.setPayments(JdPaymentList);
				
				/**
				 *  京东响应
				 */
				jdResponseView = new JdResponseView(JdResponseStatus.SUCCESS.businessCode(),
						JdResponseStatus.SUCCESS.businessMessage(), tradeNo, objectMapper.writeValueAsString(jdResponse2313));
			} else {
				/**
				 * 处理中，
				 */
				jdResponse2313.setBatchNum(batchNum);
				jdResponse2313.setValidateStatus(JdResponseStatus.CHECK_SUCCESS.businessCode());// 校验成功
				jdResponse2313.setValidateMsg(JdResponseStatus.CHECK_SUCCESS.businessMessage());
				jdResponse2313.setChargeAmount(paymentAmt);
				jdResponse2313.setDealStatus(JdResponseStatus.DEAL_STATUS_DEALING.businessCode()); // 处理中
				jdResponse2313.setMessage(response2313.getDealDesc());
				jdResponse2313.setPayments(JdPaymentList);
				
				/**
				 *  京东响应
				 */
				jdResponseView = new JdResponseView(JdResponseStatus.SUCCESS.businessCode(),
						JdResponseStatus.SUCCESS.businessMessage(), tradeNo, objectMapper.writeValueAsString(jdResponse2313));
			}
		} catch (IOException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		return jdResponseView;
	}

	/**  
	* @Description 拼装扣款计划回调接口请求报文
	* @param tradeNo 交易流水号
	* @param brNo 合作机构编号
	* @return 扣款计划上传请求报文
	* @throws  
	*/  
	private Request request2313(String brNo, String batchNum, String tradeNo, String transferType) {
		Request2313Dto request2313Dto = new Request2313Dto();
		request2313Dto.setBrNo(brNo);
		// 线下转换批次号
		JdPaymentOrderData jdPaymentOrderData1 = new JdPaymentOrderData();
		jdPaymentOrderData1.setBatchNumber(batchNum);
		jdPaymentOrderData1.setTransferType(transferType);
		JdPaymentOrderData jdPaymentOrderData =  jdPaymentOrderDataMapper.getByJDBatchNo(jdPaymentOrderData1);
		
		if (JdResponseStatus.TRANSFER_TYPE_ONLINE.businessCode().equals(transferType)) {
			request2313Dto.setOnLine(JdResponseStatus.REPAY_TYPE_ONLINE.businessCode());
			request2313Dto.setBatNo(batchNum);
		}else {
			request2313Dto.setOnLine(JdResponseStatus.REPAY_TYPE_OUTLINE.businessCode());
			request2313Dto.setBatNo(jdPaymentOrderData.getMfsBatchNo());
		}
		request2313Dto.setPageNo(1);
		request2313Dto.setPageSize(9999);

		// 拼发送小微报文
		Request request = new Request();
		request.setTxCode(CmsBusinessStatus.WS_2313.businessCode()); // 接口编号
		request.setBrNo(brNo); // 机构号
		request.setReqDate(DateUtils.nowDateFormat()); // 设置请求日期
		request.setToken("test"); // 设置token
		request.setReqTime(DateUtils.nowTimeFormat()); // 设置请求时间
		request.setReqSerial(tradeNo); // 设置请求流水号
		try {
			request.setContent(objectMapper.writeValueAsString(request2313Dto));
		} catch (JsonProcessingException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		return request;
	}

	/**  
	* @Description
	* 			支付订单查询接口请求小微系统
	*/  
	public JdResponseView transPaymentOrder(String batchNumber, String brNo, String tradeNo, String transferType) {
		// 拼发送小微业务数据
		Request request = request2313(brNo, batchNumber, tradeNo, transferType );

		// 发送小微系统
		Response response = searchService.search(CmsBusinessStatus.MFS.businessCode(), request);

		// 转换小微的响应为京东所需响应数据
		return transPaymentOrder(batchNumber, response, tradeNo, transferType);
	}

	/**  
	* @Description
	* 			支付订单查询接口转换京东响应
	*/  
	private JdResponseView transPaymentOrder(String batchNumber, Response response, String tradeNo, String transferType) {
		JdResponseView jdResponseView = null;
		try {
			if (response != null) {
				// 判断业务是否成功
				if (CmsBusinessStatus.MFS_SUCCESS.businessCode().equals(response.getRespCode())) {
					/**
					 * 何为成功
					 * 	1. 返回码response.getRespCode()为0000
					 * 	2. 总记录数pageCounts大于0
					 * 	3. 批次里的处理状态dealSts为02-处理成功或04-处理失败
					 */
					String mfsContent = response.getContent();
					if(mfsContent != null || !"".equals(mfsContent)) {
						Response2313Dto response2313Dto = objectMapper.readValue(response.getContent(), Response2313Dto.class);
						if(response2313Dto.getPageCounts() > 0) {
							/**
							 * 处理小微响应
							 */
							jdResponseView = responsePaymentOrder(batchNumber, response2313Dto, tradeNo, transferType);
							
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
					/**
					 *  小微响应为失败
					 */
					JDPaymentOrderResponse paymentOrderResponse = new JDPaymentOrderResponse();
					List<String> errorMessages = new ArrayList<String>();
					errorMessages.add(response.getRespDesc());
					paymentOrderResponse.setErrorMessages(errorMessages);
					paymentOrderResponse.setTransferType(transferType);
					paymentOrderResponse.setBatchNumber(batchNumber);
					paymentOrderResponse.setPaymentOrders(new ArrayList<JDPaymentOrders>());
					
					/**
					 * 拼京东报文
					 */
					jdResponseView = new JdResponseView(JdResponseStatus.SUCCESS.businessCode(),
							JdResponseStatus.SUCCESS.businessMessage(), tradeNo, objectMapper.writeValueAsString(paymentOrderResponse));
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
	* 			转换支付订单查询响应
	*/  
	private JdResponseView responsePaymentOrder(String batchNumber, Response2313Dto response2313Dto, String tradeNo,
			String transferType) {
		JdResponseView jdResponseView = null;
		Response2313List response2313 = response2313Dto.getList().get(0);
		try {
			// 如果查询结果为扣款成功
			if (JdResponseStatus.DEAL_STS_SUCCESS.businessCode().equals(response2313.getDealSts())) {
				/**
				 *  小微响应为失败
				 */
				JDPaymentOrders jDPaymentOrders = new JDPaymentOrders();
				jDPaymentOrders.setPaymentNo("");
				jDPaymentOrders.setTradeNo(response2313.getPaymentTradeNo());
				jDPaymentOrders.setPaymentStatus(JdResponseStatus.PAYMENT_SUCCESS.businessCode());
				jDPaymentOrders.setPaymentTime(response2313.getPaymentTime());
				jDPaymentOrders.setAmount(response2313.getPaymentAmount());
				
				JDPaymentOrderResponse paymentOrderResponse = new JDPaymentOrderResponse();
				paymentOrderResponse.setErrorMessages(new ArrayList<String>());
				paymentOrderResponse.setTransferType(transferType);
				paymentOrderResponse.setBatchNumber(batchNumber);
				List<JDPaymentOrders> list = new ArrayList<JDPaymentOrders>();
				list.add(jDPaymentOrders);
				paymentOrderResponse.setPaymentOrders(list);
				
				/**
				 * 拼京东报文
				 */
				jdResponseView = new JdResponseView(JdResponseStatus.SUCCESS.businessCode(),
						JdResponseStatus.SUCCESS.businessMessage(), tradeNo, objectMapper.writeValueAsString(paymentOrderResponse));
				
			} else if (JdResponseStatus.DEAL_STS_FAILED.businessCode().equals(response2313.getDealSts()) 
					|| JdResponseStatus.SERVER_EXCEPTION.businessCode().equals(response2313.getDealSts())) {
				
				/**
				 *  小微响应为失败
				 */
				JDPaymentOrderResponse paymentOrderResponse = new JDPaymentOrderResponse();
				List<String> errorMessages = new ArrayList<String>();
				errorMessages.add(response2313.getDealDesc());
				paymentOrderResponse.setErrorMessages(errorMessages);
				paymentOrderResponse.setTransferType(transferType);
				paymentOrderResponse.setBatchNumber(batchNumber);
				paymentOrderResponse.setPaymentOrders(new ArrayList<JDPaymentOrders>());
				
				/**
				 * 拼京东报文
				 */
				jdResponseView = new JdResponseView(JdResponseStatus.SUCCESS.businessCode(),
						JdResponseStatus.SUCCESS.businessMessage(), tradeNo, objectMapper.writeValueAsString(paymentOrderResponse));
			} else {
				/**
				 * 处理中，
				 */
				/**
				 *  小微响应为失败
				 */
				JDPaymentOrderResponse paymentOrderResponse = new JDPaymentOrderResponse();
				List<String> errorMessages = new ArrayList<String>();
				errorMessages.add(JdResponseStatus.DEAL_STS_DEALING.businessMessage());
				paymentOrderResponse.setErrorMessages(errorMessages);
				paymentOrderResponse.setTransferType(transferType);
				paymentOrderResponse.setBatchNumber(batchNumber);
				paymentOrderResponse.setPaymentOrders(new ArrayList<JDPaymentOrders>());
				
				/**
				 * 拼京东报文
				 */
				jdResponseView = new JdResponseView(JdResponseStatus.SUCCESS.businessCode(),
						JdResponseStatus.SUCCESS.businessMessage(), tradeNo, objectMapper.writeValueAsString(paymentOrderResponse));
			}
		} catch (IOException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		return jdResponseView;
	}

}
