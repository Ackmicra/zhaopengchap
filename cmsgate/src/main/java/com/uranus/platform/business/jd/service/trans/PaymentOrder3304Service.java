package com.uranus.platform.business.jd.service.trans;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.business.jd.dao.JdRepurchaseApplyDataMapper;
import com.uranus.platform.business.jd.entity.po.JdTransferPlansData;
import com.uranus.platform.business.jd.entity.status.JdResponseStatus;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;
import com.uranus.platform.business.pub.entity.dto.RepurchasePaymentOrder;
import com.uranus.platform.business.pub.entity.dto.Request3304Dto;
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
public class PaymentOrder3304Service {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProcessService processService;
	@Autowired
	JdRepurchaseApplyDataMapper jdRepurchaseApplyDataMapper;
	
	/**
	 * 
	* @Title：request2311
	* @Description：回购支付订单调用3304
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	public JdResponseView request3304(String brNo, String tradeNo, List<JdTransferPlansData> jdTransferPlansDataList,
			String batchNo,String projNo) {
		// 拼发送小微业务数据
		Request request = getRequest3304(brNo, tradeNo, jdTransferPlansDataList, batchNo,projNo);

		// 发送小微系统
		Response response = processService.wsProcess(CmsBusinessStatus.MFS.businessCode(), request);

		// 转换小微的响应为京东所需响s应数据
		JdResponseView jdResponseDto = paymentOrderResponse(tradeNo, response);

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
	private Request getRequest3304(String brNo, String tradeNo, List<JdTransferPlansData> jdTransferPlansDataList,
			String batchNo,String projNo) {
		
		List<RepurchasePaymentOrder> repurchasePaymentOrderList = jdTransferPlansDataList.stream().map(pojo -> {
			//获取回购发生日期
			String repDate = jdRepurchaseApplyDataMapper.getByApplicationNoAndSts01(pojo.getApplicationNo()).getCreateDate();
			
			RepurchasePaymentOrder repurchasePaymentOrder = new RepurchasePaymentOrder();
			repurchasePaymentOrder.setBrNo(brNo);
			repurchasePaymentOrder.setPactNo(pojo.getApplicationNo());
			repurchasePaymentOrder.setProjNo(projNo);
			repurchasePaymentOrder.setRepAmt(pojo.getChargeAmount());
			repurchasePaymentOrder.setRepDate(repDate);
			repurchasePaymentOrder.setRepFine(pojo.getPenaltyRate());
			repurchasePaymentOrder.setRepInte(pojo.getChargeRate());
			repurchasePaymentOrder.setRepPrcp(pojo.getChargePrincipal());
			return repurchasePaymentOrder;
			
		}).collect(Collectors.toList());

		Request3304Dto request3304Dto = new Request3304Dto();
		request3304Dto.setBatchNo(batchNo);
		request3304Dto.setDataCnt(jdTransferPlansDataList.size());
		request3304Dto.setList(repurchasePaymentOrderList);

		// 拼发送小微报文
		Request request = new Request();
		request.setTxCode(CmsBusinessStatus.WS_3304.businessCode()); // 接口编号
		request.setBrNo(brNo); // 机构号
		request.setReqDate(DateUtils.nowDateFormat()); // 设置请求日期
		request.setToken("test"); // 设置token
		request.setReqTime(DateUtils.nowTimeFormat()); // 设置请求时间
		request.setReqSerial(tradeNo); // 设置请求流水号
		try {
			request.setContent(objectMapper.writeValueAsString(request3304Dto));
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
	private JdResponseView paymentOrderResponse(String tradeNo, Response response) {
		JdResponseView jdResponseDto = null;
		if (response != null) {
			// 判断业务是否成功
			if ("0000".equals(response.getRespCode()) || "0001".equals(response.getRespCode())) {
				jdResponseDto = new JdResponseView(JdResponseStatus.SUCCESS_FOR_DEAL.businessCode(),
						JdResponseStatus.SUCCESS_FOR_DEAL.businessMessage(), tradeNo, "{}");
			} else {
				jdResponseDto = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(), response.getRespDesc(),
						tradeNo, "{}");
			}
		} else {
			jdResponseDto = new JdResponseView(JdResponseStatus.UNKNOWN_ERROR.businessCode(), "响应数据为空", tradeNo, "{}");
		}
		return jdResponseDto;
	}
}
