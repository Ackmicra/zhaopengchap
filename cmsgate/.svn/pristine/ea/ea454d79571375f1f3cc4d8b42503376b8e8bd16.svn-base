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
import com.uranus.platform.business.jd.entity.po.JdAccountData;
import com.uranus.platform.business.jd.entity.po.JdTransferPlansData;
import com.uranus.platform.business.jd.entity.pojo.JdErrorMessage;
import com.uranus.platform.business.jd.entity.status.JdResponseStatus;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;
import com.uranus.platform.business.pub.entity.dto.RepayPlanList;
import com.uranus.platform.business.pub.entity.dto.RepayPlanListSubj;
import com.uranus.platform.business.pub.entity.dto.Request2311Dto;
import com.uranus.platform.business.pub.entity.dto.Response2312Dto;
import com.uranus.platform.business.pub.entity.dto.Response2312ListDto;
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
public class TransferPlan2311Service {
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProcessService processService;
	@Autowired
	JdAccountDataMapper jdAccountDataMapper;
	

	/**
	 * 
	* @Title：request2311
	* @Description：TODO
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	public JdResponseView request2311(String brNo, String tradeNo, List<JdTransferPlansData> jdTransferPlansDataList,
			String batchNo) {
		// 拼发送小微业务数据
		Request request = getRequest2311(brNo, tradeNo, jdTransferPlansDataList, batchNo);

		// 发送小微系统
		Response response = processService.wsProcess(CmsBusinessStatus.MFS.businessCode(), request);

		// 转换小微的响应为京东所需响s应数据
		JdResponseView jdResponseDto = transferPlanResponse(tradeNo, response);

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
	private Request getRequest2311(String brNo, String tradeNo, List<JdTransferPlansData> jdTransferPlansDataList,
			String batchNo) {

		List<RepayPlanList> list = new ArrayList<RepayPlanList>();
		Map<String, RepayPlanList> repayMap = new HashMap<String, RepayPlanList>();
		
		for (JdTransferPlansData pojo : jdTransferPlansDataList) {
			RepayPlanList repayPlanList = repayMap.get(pojo.getApplicationNo());
			if(repayPlanList == null) {
				RepayPlanList repayPlanList1 = new RepayPlanList();
				
				List<RepayPlanListSubj> repayPlanListSubjList = new ArrayList<RepayPlanListSubj>();
				// 获取扣款账户信息
				JdAccountData jdAccountData = jdAccountDataMapper.selectByApplicationNo(pojo.getApplicationNo());

				if (jdAccountData == null) {
					jdAccountData = new JdAccountData();
				}
				repayPlanList1.setPactNo(pojo.getApplicationNo());
				repayPlanList1.setSerialNo(tradeNo);
				repayPlanList1.setAcNo(jdAccountData.getAccountNo());

				if ("true".equals(pojo.getSettledEarly())) {
					repayPlanList1.setRepayType(JdResponseStatus.REPAY_TYPE_CLEAR.businessCode());
				} else {
					repayPlanList1.setRepayType(JdResponseStatus.REPAY_TYPE_ONLINE.businessCode());
				}
				repayPlanList1.setRepayAmt(pojo.getChargeAmount());
				
				RepayPlanListSubj repayPlanListSubj1 = new RepayPlanListSubj();
				repayPlanListSubj1.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj1.setSubjType(JdResponseStatus.CHARGE_PRINCIPLE.businessCode());
				repayPlanListSubj1.setSubjAmt(pojo.getChargePrincipal());
				repayPlanListSubjList.add(repayPlanListSubj1);

				RepayPlanListSubj repayPlanListSubj2 = new RepayPlanListSubj();
				repayPlanListSubj2.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj2.setSubjType(JdResponseStatus.CHARGED_PRINCIPLE.businessCode());
				repayPlanListSubj2.setSubjAmt(pojo.getChargePrincipal());
				repayPlanListSubjList.add(repayPlanListSubj2);

				RepayPlanListSubj repayPlanListSubj3 = new RepayPlanListSubj();
				repayPlanListSubj3.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj3.setSubjType(JdResponseStatus.CHARGE_RATE.businessCode());
				repayPlanListSubj3.setSubjAmt(pojo.getChargeRate());
				repayPlanListSubjList.add(repayPlanListSubj3);

				RepayPlanListSubj repayPlanListSubj4 = new RepayPlanListSubj();
				repayPlanListSubj4.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj4.setSubjType(JdResponseStatus.CHARGED_RATE.businessCode());
				repayPlanListSubj4.setSubjAmt(pojo.getChargeRate());
				repayPlanListSubjList.add(repayPlanListSubj4);

				RepayPlanListSubj repayPlanListSubj5 = new RepayPlanListSubj();
				repayPlanListSubj5.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj5.setSubjType(JdResponseStatus.PENALTY_RATE.businessCode());
				repayPlanListSubj5.setSubjAmt(pojo.getPenaltyRate());
				repayPlanListSubjList.add(repayPlanListSubj5);

				RepayPlanListSubj repayPlanListSubj6 = new RepayPlanListSubj();
				repayPlanListSubj6.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj6.setSubjType(JdResponseStatus.PENALTYED_RATE.businessCode());
				repayPlanListSubj6.setSubjAmt(pojo.getPenaltyRate());
				repayPlanListSubjList.add(repayPlanListSubj6);

				RepayPlanListSubj repayPlanListSubj7 = new RepayPlanListSubj();
				repayPlanListSubj7.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj7.setSubjType(JdResponseStatus.PENALTY_AMOUNT.businessCode());
				repayPlanListSubj7.setSubjAmt(pojo.getPenaltyAmount());
				repayPlanListSubjList.add(repayPlanListSubj7);

				RepayPlanListSubj repayPlanListSubj8 = new RepayPlanListSubj();
				repayPlanListSubj8.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj8.setSubjType(JdResponseStatus.CHARGE_MOENY.businessCode());
				repayPlanListSubj8.setSubjAmt(pojo.getChargeMoney());
				repayPlanListSubjList.add(repayPlanListSubj8);

				RepayPlanListSubj repayPlanListSubj9 = new RepayPlanListSubj();
				repayPlanListSubj9.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj9.setSubjType(JdResponseStatus.REFUND_SECURE_CHARGE.businessCode());
				repayPlanListSubj9.setSubjAmt(pojo.getRefundSecureCharge());
				repayPlanListSubjList.add(repayPlanListSubj9);

				RepayPlanListSubj repayPlanListSubj10 = new RepayPlanListSubj();
				repayPlanListSubj10.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj10.setSubjType(JdResponseStatus.REFUND_SERVICE_CHARGE.businessCode());
				repayPlanListSubj10.setSubjAmt(pojo.getRefundServiceCharge());
				repayPlanListSubjList.add(repayPlanListSubj10);
				
				repayPlanList1.setListSubj(repayPlanListSubjList);
				
				repayMap.put(pojo.getApplicationNo(), repayPlanList1);
				
				list.add(repayPlanList1);
				
			} else {
				
				double repayAmt = repayPlanList.getRepayAmt() + pojo.getChargeAmount();
				repayPlanList.setRepayAmt(repayAmt);
				
				RepayPlanListSubj repayPlanListSubj1 = new RepayPlanListSubj();
				repayPlanListSubj1.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj1.setSubjType(JdResponseStatus.CHARGE_PRINCIPLE.businessCode());
				repayPlanListSubj1.setSubjAmt(pojo.getChargePrincipal());
				repayPlanList.getListSubj().add(repayPlanListSubj1);

				RepayPlanListSubj repayPlanListSubj2 = new RepayPlanListSubj();
				repayPlanListSubj2.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj2.setSubjType(JdResponseStatus.CHARGED_PRINCIPLE.businessCode());
				repayPlanListSubj2.setSubjAmt(pojo.getChargePrincipal());
				repayPlanList.getListSubj().add(repayPlanListSubj2);

				RepayPlanListSubj repayPlanListSubj3 = new RepayPlanListSubj();
				repayPlanListSubj3.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj3.setSubjType(JdResponseStatus.CHARGE_RATE.businessCode());
				repayPlanListSubj3.setSubjAmt(pojo.getChargeRate());
				repayPlanList.getListSubj().add(repayPlanListSubj3);

				RepayPlanListSubj repayPlanListSubj4 = new RepayPlanListSubj();
				repayPlanListSubj4.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj4.setSubjType(JdResponseStatus.CHARGED_RATE.businessCode());
				repayPlanListSubj4.setSubjAmt(pojo.getChargeRate());
				repayPlanList.getListSubj().add(repayPlanListSubj4);

				RepayPlanListSubj repayPlanListSubj5 = new RepayPlanListSubj();
				repayPlanListSubj5.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj5.setSubjType(JdResponseStatus.PENALTY_RATE.businessCode());
				repayPlanListSubj5.setSubjAmt(pojo.getPenaltyRate());
				repayPlanList.getListSubj().add(repayPlanListSubj5);

				RepayPlanListSubj repayPlanListSubj6 = new RepayPlanListSubj();
				repayPlanListSubj6.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj6.setSubjType(JdResponseStatus.PENALTYED_RATE.businessCode());
				repayPlanListSubj6.setSubjAmt(pojo.getPenaltyRate());
				repayPlanList.getListSubj().add(repayPlanListSubj6);

				RepayPlanListSubj repayPlanListSubj7 = new RepayPlanListSubj();
				repayPlanListSubj7.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj7.setSubjType(JdResponseStatus.PENALTY_AMOUNT.businessCode());
				repayPlanListSubj7.setSubjAmt(pojo.getPenaltyAmount());
				repayPlanList.getListSubj().add(repayPlanListSubj7);

				RepayPlanListSubj repayPlanListSubj8 = new RepayPlanListSubj();
				repayPlanListSubj8.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj8.setSubjType(JdResponseStatus.CHARGE_MOENY.businessCode());
				repayPlanListSubj8.setSubjAmt(pojo.getChargeMoney());
				repayPlanList.getListSubj().add(repayPlanListSubj8);

				RepayPlanListSubj repayPlanListSubj9 = new RepayPlanListSubj();
				repayPlanListSubj9.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj9.setSubjType(JdResponseStatus.REFUND_SECURE_CHARGE.businessCode());
				repayPlanListSubj9.setSubjAmt(pojo.getRefundSecureCharge());
				repayPlanList.getListSubj().add(repayPlanListSubj9);

				RepayPlanListSubj repayPlanListSubj10 = new RepayPlanListSubj();
				repayPlanListSubj10.setCnt(pojo.getCurrentIssue());
				repayPlanListSubj10.setSubjType(JdResponseStatus.REFUND_SERVICE_CHARGE.businessCode());
				repayPlanListSubj10.setSubjAmt(pojo.getRefundServiceCharge());
				repayPlanList.getListSubj().add(repayPlanListSubj10);
				
			}
		}
		Request2311Dto request2311Dto = new Request2311Dto();
		request2311Dto.setBatNo(batchNo);
		request2311Dto.setBrNo(brNo);
		request2311Dto.setDataCnt(jdTransferPlansDataList.size());
		request2311Dto.setList(list);
		

		// 拼发送小微报文
		Request request = new Request();
		request.setTxCode(CmsBusinessStatus.WS_2311.businessCode()); // 接口编号
		request.setBrNo(brNo); // 机构号
		request.setReqDate(DateUtils.nowDateFormat()); // 设置请求日期
		request.setToken("test"); // 设置token
		request.setReqTime(DateUtils.nowTimeFormat()); // 设置请求时间
		request.setReqSerial(tradeNo); // 设置请求流水号
		try {
			request.setContent(objectMapper.writeValueAsString(request2311Dto));
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
	private JdResponseView transferPlanResponse(String tradeNo, Response response) {
		JdResponseView jdResponseDto = null;
		try {
			if (response != null) {
				// 判断业务是否成功
				if (CmsBusinessStatus.MFS_SUCCESS.businessCode().equals(response.getRespCode())) {
					
					jdResponseDto = new JdResponseView(JdResponseStatus.SUCCESS_FOR_DEAL.businessCode(),
							JdResponseStatus.SUCCESS_FOR_DEAL.businessMessage(), tradeNo, null);
				
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
				jdResponseDto = new JdResponseView(JdResponseStatus.UNKNOWN_ERROR.businessCode(), "响应数据为空", tradeNo, null);
			}
		} catch (IOException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		return jdResponseDto;
	}
}
