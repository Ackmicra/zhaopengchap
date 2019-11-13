package com.uranus.platform.business.jd.service.trans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.business.jd.dao.JdAccountDataMapper;
import com.uranus.platform.business.jd.entity.po.JdTransferPlansData;
import com.uranus.platform.business.jd.entity.pojo.JdErrorMessage;
import com.uranus.platform.business.jd.entity.status.JdResponseStatus;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;
import com.uranus.platform.business.pub.entity.dto.PaymentOrderList;
import com.uranus.platform.business.pub.entity.dto.PaymentOrderListSubj;
import com.uranus.platform.business.pub.entity.dto.RepayPlanList;
import com.uranus.platform.business.pub.entity.dto.Request2312Dto;
import com.uranus.platform.business.pub.entity.dto.Response2312Dto;
import com.uranus.platform.business.pub.entity.dto.Response2312ListDto;
import com.uranus.platform.business.pub.mq.domain.MQParmsDomain;
import com.uranus.platform.business.pub.mq.producer.JDMQProducerManager;
import com.uranus.platform.business.ws.process.Request;
import com.uranus.platform.business.ws.process.Response;
import com.uranus.platform.business.ws.service.ProcessService;
import com.uranus.platform.utils.exception.PlatformExceptionFactory;
import com.uranus.platform.utils.status.CmsBusinessStatus;
import com.uranus.tools.utils.DateUtils;

/**
 * 
* @ClassName:：PaymentOrder2312Service 
* @Description：支付订单
* @author ：chenwendong
* @date ：2019年8月19日 下午3:21:49 
*
 */
@Service
public class PaymentOrder2312Service {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProcessService processService;
	@Autowired
	JdAccountDataMapper jdAccountDataMapper;
	@Autowired
	private JDMQProducerManager jDMQProducerManager;
	
	/**
	 * 
	* @Title：request2311
	* @Description：线下扣款支付订单调用2312
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	public JdResponseView request2312(String brNo, String tradeNo, List<JdTransferPlansData> jdTransferPlansDataList,
			String batchNo) {
		// 拼发送小微业务数据
		Request request = getRequest2312(brNo, tradeNo, jdTransferPlansDataList, batchNo);

		// 发送小微系统
		Response response = processService.wsProcess(CmsBusinessStatus.MFS.businessCode(), request);

		// 转换小微的响应为京东所需响s应数据
		JdResponseView jdResponseDto = transferPlanResponse(tradeNo, response, batchNo);

		return jdResponseDto;
	}
	/**  
	* @Description 拼装扣款计划上传接口请求报文
	* @param tradeNo 交易流水号
	* @param brNo 合作机构编号
	* @param JdRepayPlanDataList 京东数据
	* @return 扣款计划上传请求报文
	* @throws  
	*/  
	private Request getRequest2312(String brNo, String tradeNo, List<JdTransferPlansData> jdTransferPlansDataList,
			String batchNo) {

		List<PaymentOrderList> list = new ArrayList<PaymentOrderList>();
		Map<String, PaymentOrderList> repayMap = new HashMap<String, PaymentOrderList>();
		
		for (JdTransferPlansData pojo : jdTransferPlansDataList) {
			PaymentOrderList paymentOrderList1 = repayMap.get(pojo.getApplicationNo());
			if (paymentOrderList1 == null) {
				PaymentOrderList paymentOrderList = new PaymentOrderList();
				paymentOrderList.setPactNo(pojo.getApplicationNo());
				if ("true".equals(pojo.getSettledEarly())) {
					paymentOrderList.setRepayType(JdResponseStatus.REPAY_TYPE_CLEAR.businessCode());
				} else {
					paymentOrderList.setRepayType(JdResponseStatus.REPAY_TYPE_NORMAL.businessCode());
				}
				paymentOrderList.setRepayAmt(pojo.getChargeAmount());

				List<PaymentOrderListSubj> paymentOrderListSubjList = new ArrayList<PaymentOrderListSubj>();

				PaymentOrderListSubj paymentOrderListSubj1 = new PaymentOrderListSubj();
				paymentOrderListSubj1.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj1.setSubjType(JdResponseStatus.CHARGE_PRINCIPLE.businessCode());
				paymentOrderListSubj1.setSubjAmt(pojo.getChargePrincipal());
				paymentOrderListSubjList.add(paymentOrderListSubj1);

				PaymentOrderListSubj paymentOrderListSubj2 = new PaymentOrderListSubj();
				paymentOrderListSubj2.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj2.setSubjType(JdResponseStatus.CHARGED_PRINCIPLE.businessCode());
				paymentOrderListSubj2.setSubjAmt(pojo.getChargePrincipal());
				paymentOrderListSubjList.add(paymentOrderListSubj2);

				PaymentOrderListSubj paymentOrderListSubj3 = new PaymentOrderListSubj();
				paymentOrderListSubj3.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj3.setSubjType(JdResponseStatus.CHARGE_RATE.businessCode());
				paymentOrderListSubj3.setSubjAmt(pojo.getChargeRate());
				paymentOrderListSubjList.add(paymentOrderListSubj3);

				PaymentOrderListSubj paymentOrderListSubj4 = new PaymentOrderListSubj();
				paymentOrderListSubj4.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj4.setSubjType(JdResponseStatus.CHARGED_RATE.businessCode());
				paymentOrderListSubj4.setSubjAmt(pojo.getChargeRate());
				paymentOrderListSubjList.add(paymentOrderListSubj4);

				PaymentOrderListSubj paymentOrderListSubj5 = new PaymentOrderListSubj();
				paymentOrderListSubj5.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj5.setSubjType(JdResponseStatus.PENALTY_RATE.businessCode());
				paymentOrderListSubj5.setSubjAmt(pojo.getPenaltyRate());
				paymentOrderListSubjList.add(paymentOrderListSubj5);

				PaymentOrderListSubj paymentOrderListSubj6 = new PaymentOrderListSubj();
				paymentOrderListSubj6.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj6.setSubjType(JdResponseStatus.PENALTYED_RATE.businessCode());
				paymentOrderListSubj6.setSubjAmt(pojo.getPenaltyRate());
				paymentOrderListSubjList.add(paymentOrderListSubj6);

				PaymentOrderListSubj paymentOrderListSubj7 = new PaymentOrderListSubj();
				paymentOrderListSubj7.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj7.setSubjType(JdResponseStatus.PENALTY_AMOUNT.businessCode());
				paymentOrderListSubj7.setSubjAmt(pojo.getPenaltyAmount());
				paymentOrderListSubjList.add(paymentOrderListSubj7);

				PaymentOrderListSubj paymentOrderListSubj8 = new PaymentOrderListSubj();
				paymentOrderListSubj8.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj8.setSubjType(JdResponseStatus.CHARGE_MOENY.businessCode());
				paymentOrderListSubj8.setSubjAmt(pojo.getChargeMoney());
				paymentOrderListSubjList.add(paymentOrderListSubj8);

				PaymentOrderListSubj paymentOrderListSubj9 = new PaymentOrderListSubj();
				paymentOrderListSubj9.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj9.setSubjType(JdResponseStatus.REFUND_SECURE_CHARGE.businessCode());
				paymentOrderListSubj9.setSubjAmt(pojo.getRefundSecureCharge());
				paymentOrderListSubjList.add(paymentOrderListSubj9);

				PaymentOrderListSubj paymentOrderListSubj10 = new PaymentOrderListSubj();
				paymentOrderListSubj10.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj10.setSubjType(JdResponseStatus.REFUND_SERVICE_CHARGE.businessCode());
				paymentOrderListSubj10.setSubjAmt(pojo.getRefundServiceCharge());
				paymentOrderListSubjList.add(paymentOrderListSubj10);

				paymentOrderList.setListSubj(paymentOrderListSubjList);
				
				repayMap.put(pojo.getApplicationNo(), paymentOrderList);
				
				list.add(paymentOrderList);

			} else {
				double repayAmt = paymentOrderList1.getRepayAmt() + pojo.getChargeAmount();
				paymentOrderList1.setRepayAmt(repayAmt);

				PaymentOrderListSubj paymentOrderListSubj1 = new PaymentOrderListSubj();
				paymentOrderListSubj1.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj1.setSubjType(JdResponseStatus.CHARGE_PRINCIPLE.businessCode());
				paymentOrderListSubj1.setSubjAmt(pojo.getChargePrincipal());
				paymentOrderList1.getListSubj().add(paymentOrderListSubj1);

				PaymentOrderListSubj paymentOrderListSubj2 = new PaymentOrderListSubj();
				paymentOrderListSubj2.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj2.setSubjType(JdResponseStatus.CHARGED_PRINCIPLE.businessCode());
				paymentOrderListSubj2.setSubjAmt(pojo.getChargePrincipal());
				paymentOrderList1.getListSubj().add(paymentOrderListSubj2);

				PaymentOrderListSubj paymentOrderListSubj3 = new PaymentOrderListSubj();
				paymentOrderListSubj3.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj3.setSubjType(JdResponseStatus.CHARGE_RATE.businessCode());
				paymentOrderListSubj3.setSubjAmt(pojo.getChargeRate());
				paymentOrderList1.getListSubj().add(paymentOrderListSubj3);

				PaymentOrderListSubj paymentOrderListSubj4 = new PaymentOrderListSubj();
				paymentOrderListSubj4.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj4.setSubjType(JdResponseStatus.CHARGED_RATE.businessCode());
				paymentOrderListSubj4.setSubjAmt(pojo.getChargeRate());
				paymentOrderList1.getListSubj().add(paymentOrderListSubj4);

				PaymentOrderListSubj paymentOrderListSubj5 = new PaymentOrderListSubj();
				paymentOrderListSubj5.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj5.setSubjType(JdResponseStatus.PENALTY_RATE.businessCode());
				paymentOrderListSubj5.setSubjAmt(pojo.getPenaltyRate());
				paymentOrderList1.getListSubj().add(paymentOrderListSubj5);

				PaymentOrderListSubj paymentOrderListSubj6 = new PaymentOrderListSubj();
				paymentOrderListSubj6.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj6.setSubjType(JdResponseStatus.PENALTYED_RATE.businessCode());
				paymentOrderListSubj6.setSubjAmt(pojo.getPenaltyRate());
				paymentOrderList1.getListSubj().add(paymentOrderListSubj6);

				PaymentOrderListSubj paymentOrderListSubj7 = new PaymentOrderListSubj();
				paymentOrderListSubj7.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj7.setSubjType(JdResponseStatus.PENALTY_AMOUNT.businessCode());
				paymentOrderListSubj7.setSubjAmt(pojo.getPenaltyAmount());
				paymentOrderList1.getListSubj().add(paymentOrderListSubj7);

				PaymentOrderListSubj paymentOrderListSubj8 = new PaymentOrderListSubj();
				paymentOrderListSubj8.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj8.setSubjType(JdResponseStatus.CHARGE_MOENY.businessCode());
				paymentOrderListSubj8.setSubjAmt(pojo.getChargeMoney());
				paymentOrderList1.getListSubj().add(paymentOrderListSubj8);

				PaymentOrderListSubj paymentOrderListSubj9 = new PaymentOrderListSubj();
				paymentOrderListSubj9.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj9.setSubjType(JdResponseStatus.REFUND_SECURE_CHARGE.businessCode());
				paymentOrderListSubj9.setSubjAmt(pojo.getRefundSecureCharge());
				paymentOrderList1.getListSubj().add(paymentOrderListSubj9);

				PaymentOrderListSubj paymentOrderListSubj10 = new PaymentOrderListSubj();
				paymentOrderListSubj10.setCnt(pojo.getCurrentIssue());
				paymentOrderListSubj10.setSubjType(JdResponseStatus.REFUND_SERVICE_CHARGE.businessCode());
				paymentOrderListSubj10.setSubjAmt(pojo.getRefundServiceCharge());
				paymentOrderList1.getListSubj().add(paymentOrderListSubj10);
			}
		}

		Request2312Dto request2312Dto = new Request2312Dto();
		request2312Dto.setBatNo(batchNo);
		request2312Dto.setBrNo(brNo);
		request2312Dto.setDataCnt(jdTransferPlansDataList.size());
		request2312Dto.setList(list);

		// 拼发送小微报文
		Request request = new Request();
		request.setTxCode(CmsBusinessStatus.WS_2312.businessCode()); // 接口编号
		request.setBrNo(brNo); // 机构号
		request.setReqDate(DateUtils.nowDateFormat()); // 设置请求日期
		request.setToken("test"); // 设置token
		request.setReqTime(DateUtils.nowTimeFormat()); // 设置请求时间
		request.setReqSerial(tradeNo); // 设置请求流水号
		try {
			request.setContent(objectMapper.writeValueAsString(request2312Dto));
		} catch (JsonProcessingException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		return request;
	}
	/**  
	* @Description 将小微响应报文转换成京东所需响应
	* @param tradeNo 业务流水号
	* @param response 小微响应
	* @return 京东响应
	* @throws  
	*/  
	private JdResponseView transferPlanResponse(String tradeNo, Response response, String batchNo) {
		JdResponseView jdResponseDto = null;
		try {
			if (response != null) {
				// 判断业务是否成功
				if (CmsBusinessStatus.MFS_SUCCESS.businessCode().equals(response.getRespCode())) {
					jdResponseDto = new JdResponseView(JdResponseStatus.SUCCESS_FOR_DEAL.businessCode(),
							JdResponseStatus.SUCCESS_FOR_DEAL.businessMessage(), tradeNo, "{}") ;
					
					/**
					 * 处理成功后发送，将任务放到MQ消息队列中
					 * 
					 * 	1. 创建MQ消息参数对象
					 * 	2. 将消息发送到MQ队列
					 */
					MQParmsDomain message = new MQParmsDomain();
					message.setTaskData(batchNo);
					message.setTaskType(CmsBusinessStatus.CALLBACK_PAYMENT_ORDER_RESULT.businessCode());
					message.setDelayLevel(Integer.parseInt(CmsBusinessStatus.MQ_DELAY_LEVEL_TWO.businessCode()));
					jDMQProducerManager.send(message);
				} else if(CmsBusinessStatus.MFS_HALF_SUCCESS.businessCode().equals(response.getRespCode())){
					Response2312Dto response2312 = objectMapper.readValue(response.getContent(), Response2312Dto.class);
					if(response2312 != null) {
						Response2312ListDto response2312List = response2312.getList().get(0);
						if(response2312List != null) {
							List<String> list = new ArrayList<String>();
							list.add(response2312List.getDealDesc());
							JdErrorMessage jdErrorMessage = new JdErrorMessage();
							jdErrorMessage.setErrorMessages(list);
							jdResponseDto = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(), JdResponseStatus.DATA_ERROR.businessMessage(),
									tradeNo, objectMapper.writeValueAsString(jdErrorMessage));
						}else {
							jdResponseDto = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(), JdResponseStatus.DATA_ERROR.businessMessage(),
									tradeNo, "{}");
						}
					} else {
						jdResponseDto = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(), JdResponseStatus.DATA_ERROR.businessMessage(),
								tradeNo, "{}");
					}
				}else {
					List<String> list = new ArrayList<String>();
					list.add(response.getRespDesc());
					JdErrorMessage jdErrorMessage = new JdErrorMessage();
					jdErrorMessage.setErrorMessages(list);
					jdResponseDto = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(), JdResponseStatus.DATA_ERROR.businessMessage(),
							tradeNo, objectMapper.writeValueAsString(jdErrorMessage));
				}
			} else {
				jdResponseDto = new JdResponseView(JdResponseStatus.UNKNOWN_ERROR.businessCode(), JdResponseStatus.UNKNOWN_ERROR.businessCode(), tradeNo, "{}");
			}
		} catch (IOException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		return jdResponseDto;
	}
}
