package com.uranus.platform.business.jd.service.trans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.business.jd.dao.JdAccountDataMapper;
import com.uranus.platform.business.jd.dao.JdTransferBatchDataMapper;
import com.uranus.platform.business.jd.dao.JdTransferPlansDataMapper;
import com.uranus.platform.business.jd.entity.po.JdTransferBatchData;
import com.uranus.platform.business.jd.entity.po.JdTransferPlansData;
import com.uranus.platform.business.jd.entity.pojo.JdErrorMessage;
import com.uranus.platform.business.jd.entity.status.JdResponseStatus;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;
import com.uranus.platform.business.pub.entity.dto.Request2318Dto;
import com.uranus.platform.business.pub.entity.dto.Response2318Dto;
import com.uranus.platform.business.pub.entity.dto.TransferPlanList;
import com.uranus.platform.business.pub.entity.dto.TransferPlanListSubj;
import com.uranus.platform.business.ws.process.Request;
import com.uranus.platform.business.ws.process.Response;
import com.uranus.platform.business.ws.service.ProcessService;
import com.uranus.platform.utils.exception.PlatformExceptionFactory;
import com.uranus.platform.utils.status.CmsBusinessStatus;
import com.uranus.tools.utils.DateUtils;

/**
 * 扣款计划上送
* @ClassName:：TransferPlan2311Service 
* @Description： TODO
* @author ：chenwendong
* @date ：2019年8月15日 下午2:20:35 
*
 */
@Service
public class TransferPlan2318Service {
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProcessService processService;
	@Autowired
	JdAccountDataMapper jdAccountDataMapper;
	@Autowired
	JdTransferPlansDataMapper jdTransferPlansDataMapper;
	@Autowired
	JdTransferBatchDataMapper jdTransferBatchDataMapper;

	/**
	 * 
	* @Title：request2311
	* @Description：TODO
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	public Map<String , Object> request2318(String brNo, String tradeNo, List<JdTransferPlansData> jdTransferPlansDataList,
			String batchNo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 拼发送小微业务数据
		Request request = getRequest2318(brNo, tradeNo, jdTransferPlansDataList, batchNo);

		// 发送小微系统
		Response response = processService.wsProcess(CmsBusinessStatus.MFS.businessCode(), request);

		// 转换小微的响应为京东所需响s应数据
		JdResponseView jdResponseView = transferPlanResponse(tradeNo, batchNo,response);
		
		resultMap.put("jdResponseView", jdResponseView);

		return resultMap;
	}
	/**  
	* @Description 拼装扣款计划上传接口请求报文
	* @param tradeNo 交易流水号
	* @param brNo 合作机构编号
	* @param JdRepayPlanDataList 京东数据
	* @return 扣款计划上传请求报文
	* @throws  
	*/  
	private Request getRequest2318(String brNo, String tradeNo, List<JdTransferPlansData> jdTransferPlansDataList,
			String batchNo) {

		List<TransferPlanList> list = new ArrayList<TransferPlanList>();
		Map<String, TransferPlanList> repayMap = new HashMap<String, TransferPlanList>();
		
		for (JdTransferPlansData pojo : jdTransferPlansDataList) {
			TransferPlanList transferPlanList = repayMap.get(pojo.getApplicationNo());
			if(transferPlanList == null) {
				TransferPlanList transferPlanList1 = new TransferPlanList();
				
				List<TransferPlanListSubj> transferPlanListSubjList = new ArrayList<TransferPlanListSubj>();

				transferPlanList1.setPactNo(pojo.getApplicationNo());
				transferPlanList1.setRepayAmt(pojo.getChargeAmount());

				if ("true".equals(pojo.getSettledEarly())) {
					transferPlanList1.setRepayType(JdResponseStatus.REPAY_TYPE_CLEAR.businessCode());
				} else {
					transferPlanList1.setRepayType(JdResponseStatus.REPAY_TYPE_ONLINE.businessCode());
				}
				
				TransferPlanListSubj transferPlanListSubj1 = new TransferPlanListSubj();
				transferPlanListSubj1.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj1.setSubjType(JdResponseStatus.CHARGE_PRINCIPLE.businessCode());
				transferPlanListSubj1.setSubjAmt(pojo.getChargePrincipal());
				transferPlanListSubjList.add(transferPlanListSubj1);

				TransferPlanListSubj transferPlanListSubj2 = new TransferPlanListSubj();
				transferPlanListSubj2.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj2.setSubjType(JdResponseStatus.CHARGED_PRINCIPLE.businessCode());
				transferPlanListSubj2.setSubjAmt(pojo.getChargePrincipal());
				transferPlanListSubjList.add(transferPlanListSubj2);

				TransferPlanListSubj transferPlanListSubj3 = new TransferPlanListSubj();			
				transferPlanListSubj3.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj3.setSubjType(JdResponseStatus.CHARGE_RATE.businessCode());
				transferPlanListSubj3.setSubjAmt(pojo.getChargeRate());
				transferPlanListSubjList.add(transferPlanListSubj3);

				TransferPlanListSubj transferPlanListSubj4 = new TransferPlanListSubj();			
				transferPlanListSubj4.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj4.setSubjType(JdResponseStatus.CHARGED_RATE.businessCode());
				transferPlanListSubj4.setSubjAmt(pojo.getChargeRate());
				transferPlanListSubjList.add(transferPlanListSubj4);

				TransferPlanListSubj transferPlanListSubj5 = new TransferPlanListSubj();			
				transferPlanListSubj5.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj5.setSubjType(JdResponseStatus.PENALTY_RATE.businessCode());
				transferPlanListSubj5.setSubjAmt(pojo.getPenaltyRate());
				transferPlanListSubjList.add(transferPlanListSubj5);

				TransferPlanListSubj transferPlanListSubj6 = new TransferPlanListSubj();			
				transferPlanListSubj6.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj6.setSubjType(JdResponseStatus.PENALTYED_RATE.businessCode());
				transferPlanListSubj6.setSubjAmt(pojo.getPenaltyRate());
				transferPlanListSubjList.add(transferPlanListSubj6);

				TransferPlanListSubj transferPlanListSubj7 = new TransferPlanListSubj();				
				transferPlanListSubj7.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj7.setSubjType(JdResponseStatus.PENALTY_AMOUNT.businessCode());
				transferPlanListSubj7.setSubjAmt(pojo.getPenaltyAmount());
				transferPlanListSubjList.add(transferPlanListSubj7);

				TransferPlanListSubj transferPlanListSubj8 = new TransferPlanListSubj();				
				transferPlanListSubj8.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj8.setSubjType(JdResponseStatus.CHARGE_MOENY.businessCode());
				transferPlanListSubj8.setSubjAmt(pojo.getChargeMoney());
				transferPlanListSubjList.add(transferPlanListSubj8);

				TransferPlanListSubj transferPlanListSubj9 = new TransferPlanListSubj();			
				transferPlanListSubj9.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj9.setSubjType(JdResponseStatus.REFUND_SECURE_CHARGE.businessCode());
				transferPlanListSubj9.setSubjAmt(pojo.getRefundSecureCharge());
				transferPlanListSubjList.add(transferPlanListSubj9);

				TransferPlanListSubj transferPlanListSubj10 = new TransferPlanListSubj();				
				transferPlanListSubj10.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj10.setSubjType(JdResponseStatus.REFUND_SERVICE_CHARGE.businessCode());
				transferPlanListSubj10.setSubjAmt(pojo.getRefundServiceCharge());
				transferPlanListSubjList.add(transferPlanListSubj10);
				
				transferPlanList1.setListSubj(transferPlanListSubjList);
				
				repayMap.put(pojo.getApplicationNo(), transferPlanList1);
				
				list.add(transferPlanList1);
				
			} else {
				
				double repayAmt = transferPlanList.getRepayAmt() + pojo.getChargeAmount();
				transferPlanList.setRepayAmt(repayAmt);
				
				TransferPlanListSubj transferPlanListSubj1 = new TransferPlanListSubj();
				transferPlanListSubj1.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj1.setSubjType(JdResponseStatus.CHARGE_PRINCIPLE.businessCode());
				transferPlanListSubj1.setSubjAmt(pojo.getChargePrincipal());
				transferPlanList.getListSubj().add(transferPlanListSubj1);

				TransferPlanListSubj transferPlanListSubj2 = new TransferPlanListSubj();
				transferPlanListSubj2.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj2.setSubjType(JdResponseStatus.CHARGED_PRINCIPLE.businessCode());
				transferPlanListSubj2.setSubjAmt(pojo.getChargePrincipal());
				transferPlanList.getListSubj().add(transferPlanListSubj2);

				TransferPlanListSubj transferPlanListSubj3 = new TransferPlanListSubj();			
				transferPlanListSubj3.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj3.setSubjType(JdResponseStatus.CHARGE_RATE.businessCode());
				transferPlanListSubj3.setSubjAmt(pojo.getChargeRate());
				transferPlanList.getListSubj().add(transferPlanListSubj3);

				TransferPlanListSubj transferPlanListSubj4 = new TransferPlanListSubj();			
				transferPlanListSubj4.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj4.setSubjType(JdResponseStatus.CHARGED_RATE.businessCode());
				transferPlanListSubj4.setSubjAmt(pojo.getChargeRate());
				transferPlanList.getListSubj().add(transferPlanListSubj4);

				TransferPlanListSubj transferPlanListSubj5 = new TransferPlanListSubj();			
				transferPlanListSubj5.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj5.setSubjType(JdResponseStatus.PENALTY_RATE.businessCode());
				transferPlanListSubj5.setSubjAmt(pojo.getPenaltyRate());
				transferPlanList.getListSubj().add(transferPlanListSubj5);

				TransferPlanListSubj transferPlanListSubj6 = new TransferPlanListSubj();			
				transferPlanListSubj6.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj6.setSubjType(JdResponseStatus.PENALTYED_RATE.businessCode());
				transferPlanListSubj6.setSubjAmt(pojo.getPenaltyRate());
				transferPlanList.getListSubj().add(transferPlanListSubj6);

				TransferPlanListSubj transferPlanListSubj7 = new TransferPlanListSubj();				
				transferPlanListSubj7.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj7.setSubjType(JdResponseStatus.PENALTY_AMOUNT.businessCode());
				transferPlanListSubj7.setSubjAmt(pojo.getPenaltyAmount());
				transferPlanList.getListSubj().add(transferPlanListSubj7);

				TransferPlanListSubj transferPlanListSubj8 = new TransferPlanListSubj();				
				transferPlanListSubj8.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj8.setSubjType(JdResponseStatus.CHARGE_MOENY.businessCode());
				transferPlanListSubj8.setSubjAmt(pojo.getChargeMoney());
				transferPlanList.getListSubj().add(transferPlanListSubj8);

				TransferPlanListSubj transferPlanListSubj9 = new TransferPlanListSubj();			
				transferPlanListSubj9.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj9.setSubjType(JdResponseStatus.REFUND_SECURE_CHARGE.businessCode());
				transferPlanListSubj9.setSubjAmt(pojo.getRefundSecureCharge());
				transferPlanList.getListSubj().add(transferPlanListSubj9);

				TransferPlanListSubj transferPlanListSubj10 = new TransferPlanListSubj();				
				transferPlanListSubj10.setCnt(pojo.getCurrentIssue());
				transferPlanListSubj10.setSubjType(JdResponseStatus.REFUND_SERVICE_CHARGE.businessCode());
				transferPlanListSubj10.setSubjAmt(pojo.getRefundServiceCharge());
				transferPlanList.getListSubj().add(transferPlanListSubj10);
				
			}
		}
		Request2318Dto request2318Dto = new Request2318Dto();
		request2318Dto.setBatNo(batchNo);
		request2318Dto.setBrNo(brNo);
		request2318Dto.setBankNo(tradeNo);
		request2318Dto.setDataCnt(jdTransferPlansDataList.size());
		request2318Dto.setList(list);
		

		// 拼发送小微报文
		Request request = new Request();
		request.setTxCode(CmsBusinessStatus.WS_2318.businessCode()); // 接口编号
		request.setBrNo(brNo); // 机构号
		request.setReqDate(DateUtils.nowDateFormat()); // 设置请求日期
		request.setToken("test"); // 设置token
		request.setReqTime(DateUtils.nowTimeFormat()); // 设置请求时间
		request.setReqSerial(tradeNo); // 设置请求流水号
		try {
			request.setContent(objectMapper.writeValueAsString(request2318Dto));
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
	private JdResponseView transferPlanResponse(String tradeNo, String batchNo, Response response) {
		JdResponseView jdResponseDto = null;
		try {
			if (response != null) {
				// 判断业务是否成功
				if (CmsBusinessStatus.MFS_SUCCESS.businessCode().equals(response.getRespCode())) {
					List<Response2318Dto> response2318DtoList = objectMapper.readValue(response.getContent(),
							new TypeReference<List<Response2318Dto>>() {
							});			
					List<String> list = new ArrayList<String>();
					for (Response2318Dto response2318List : response2318DtoList) {
						// 存在校验失败记录则返回批次校验失败
						if (JdResponseStatus.CHECK_STS_FAIL.businessCode().equals(response2318List.getChkRes())) {
							list.add(response2318List.getPactNo() + "：【" + response2318List.getChkDesc() + "】");
							// 更新校验失败
							updateTransfer(batchNo, JdResponseStatus.CHECK_STS_FAIL.businessCode(),
									response2318List.getChkDesc());

						}
					}
					if(list.size() > 0) {
						JdErrorMessage jdErrorMessage = new JdErrorMessage();
						jdErrorMessage.setErrorMessages(list);
						
						return new JdResponseView(JdResponseStatus.REPATPLAN_EXAM_ERROR.businessCode(),
								JdResponseStatus.REPATPLAN_EXAM_ERROR.businessMessage(), tradeNo,
								objectMapper.writeValueAsString(jdErrorMessage));
					}
					// 更新校验成功
					updateTransfer(batchNo, JdResponseStatus.CHECK_STS_SUCCESS.businessCode(),
							response2318DtoList.get(0).getChkDesc());

					jdResponseDto = new JdResponseView(JdResponseStatus.SUCCESS_FOR_DEAL.businessCode(),
							JdResponseStatus.SUCCESS_FOR_DEAL.businessMessage(), tradeNo, "{}");

				} else if (CmsBusinessStatus.INVALIDATE_PARAM.businessCode().equals(response.getRespCode())) {
					// 校验失败
					updateTransfer(batchNo, JdResponseStatus.CHECK_STS_FAIL.businessCode(), response.getRespDesc());
					jdResponseDto = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(),
							JdResponseStatus.DATA_ERROR.businessMessage(), tradeNo, "{}");
				} else {
					jdResponseDto = new JdResponseView(JdResponseStatus.UNKNOWN_ERROR.businessCode(),
							JdResponseStatus.UNKNOWN_ERROR.businessMessage(), tradeNo, "{}");
				}
			} else {
				jdResponseDto = new JdResponseView(JdResponseStatus.UNKNOWN_ERROR.businessCode(), JdResponseStatus.UNKNOWN_ERROR.businessMessage(), tradeNo,
						"{}");
			}
		} catch (IOException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		return jdResponseDto;
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
}
