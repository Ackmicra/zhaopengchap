package com.uranus.platform.business.jd.service.trans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.business.jd.entity.pojo.JdRespRepayPlan;
import com.uranus.platform.business.jd.entity.pojo.JdResponse2202;
import com.uranus.platform.business.jd.entity.status.JdResponseStatus;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;
import com.uranus.platform.business.pub.entity.dto.Request2202Dto;
import com.uranus.platform.business.pub.entity.dto.Response2202Dto;
import com.uranus.platform.business.ws.search.Request;
import com.uranus.platform.business.ws.search.Response;
import com.uranus.platform.business.ws.service.SearchService;
import com.uranus.platform.utils.exception.PlatformExceptionFactory;
import com.uranus.platform.utils.status.CmsBusinessStatus;
import com.uranus.tools.utils.DateUtils;


/**
 * 还款计划查询
* @ClassName:：RepayPlanQuery2202 
* @Description： TODO
* @author ：chenwendong
* @date ：2019年8月13日 上午11:26:32 
*
 */
@Service
public class RepayPlanQuery2202Service {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private SearchService searchService;
	
	/**
	 * 
	* @Title：request2202 
	* @Description：TODO
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	public JdResponseView request2202(String brNo, String tradeNo, String aplicationNo) {
		// 拼发送小微业务数据
		Request request = getRequest2202(brNo, tradeNo, aplicationNo);

		// 发送小微系统
		Response response = searchService.search(CmsBusinessStatus.MFS.businessCode(), request);

		// 转换小微的响应为京东所需响s应数据
		JdResponseView jdResponseDto = queryPlanResponse(tradeNo, response);

		return jdResponseDto;
	}
	/**
	 * 
	* @Title：getRequest2202 
	* @Description：拼接小薇2202请求报文
	* @param ：@param brNo
	* @param ：@param tradeNo
	* @param ：@param aplicationNo
	* @param ：@return 
	* @return ：Request 
	* @throws
	 */
	public Request getRequest2202( String brNo, String tradeNo,String aplicationNo) {
		Request2202Dto request2202Dto = new  Request2202Dto();
		request2202Dto.setBrNo(brNo);
		request2202Dto.setPactNo(aplicationNo);
		//拼发送小微报文
		Request request = new Request();
		request.setTxCode(CmsBusinessStatus.WS_2202.businessCode());								//接口编号
		request.setBrNo(brNo);							//机构号
		request.setReqDate(DateUtils.nowDateFormat());			//设置请求日期
		request.setToken("test");	//设置token
		request.setReqTime(DateUtils.nowTimeFormat());			//设置请求时间
		request.setReqSerial(tradeNo);						//设置请求流水号
		try {
			request.setContent(objectMapper.writeValueAsString(request2202Dto));
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
	private JdResponseView queryPlanResponse(String tradeNo, Response response) {
		JdResponseView jdResponseView = null;
		try {
			if (response != null) {
				// 判断业务是否成功
				if (CmsBusinessStatus.MFS_SUCCESS.businessCode().equals(response.getRespCode())) {
					String returnRepayPlan = response.getContent();
					if(!"".equals(returnRepayPlan) && returnRepayPlan != null) {
						List<Response2202Dto> response2202DtoList = objectMapper.readValue(returnRepayPlan,
								new TypeReference<List<Response2202Dto>>() {
								});
						List<JdRespRepayPlan> jdRespRepayPlanList = response2202DtoList.stream().map(V -> {
							JdRespRepayPlan JdRespRepayPlan = new JdRespRepayPlan();
							JdRespRepayPlan.setIssue(V.getCnt());
							JdRespRepayPlan.setStartDate(""); //开始时间待定
							JdRespRepayPlan.setStartRateDate("");  //起息日待定
							JdRespRepayPlan.setEndDate(V.getEndDate());
							JdRespRepayPlan.setRefundDate(V.getEndDate());
							JdRespRepayPlan.setRefundPrincipal(V.getPrcpAmt());
							JdRespRepayPlan.setRefundInterest(V.getNormInt());
							JdRespRepayPlan.setSurplusPrincipal(0.00);  //本金余额待定
							JdRespRepayPlan.setPenaltyRate(V.getFineInt());
							JdRespRepayPlan.setRefundServiceCharge(0.00);
							JdRespRepayPlan.setRemainInterest(0.00);
							JdRespRepayPlan.setRemainPenaltyRate(0.00);
							JdRespRepayPlan.setRemainPrincipal(0.00);
							JdRespRepayPlan.setRemainServiceCharge(0.00);
							JdRespRepayPlan.setRefundStatus(V.getSts());
							return JdRespRepayPlan;
						}).collect(Collectors.toList());
						
						JdResponse2202 jdResponse2202 =new JdResponse2202();
						jdResponse2202.setRepayPlans(jdRespRepayPlanList);
						jdResponse2202.setTradeNo(tradeNo);
						jdResponse2202.setErrorMessages(new ArrayList<String>());
						
						jdResponseView = new JdResponseView(JdResponseStatus.SUCCESS.businessCode(),
							JdResponseStatus.SUCCESS.businessMessage(), tradeNo, objectMapper.writeValueAsString(jdResponse2202));
					} else {
						//业务失败   还款计划查询接口一般不会出现这种情况
						jdResponseView = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(),
								JdResponseStatus.DATA_ERROR.businessMessage(), tradeNo, "{}");
					}
					
				} else if(CmsBusinessStatus.MFS_NO_PACT.businessCode().equals(response.getRespCode())){
					/**
					 * 两种情况：
					 * 	1. 小微无该合同，故虽然查询成功，但是没有对应的还款计划
					 * 	2. 小微有该合同，但该合同未上传还款计划，故虽然查询成功，但是没有对应的还款计划
					 */
					JdResponse2202 jdResponse2202 =new JdResponse2202();
					jdResponse2202.setRepayPlans(new ArrayList<JdRespRepayPlan>());
					jdResponse2202.setTradeNo(tradeNo);
					List<String> list = new ArrayList<String>();
					list.add(CmsBusinessStatus.MFS_NO_PACT.businessMessage());
					jdResponse2202.setErrorMessages(list);
					//查询成功，但是没有还款计划
					jdResponseView = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(),
							JdResponseStatus.DATA_ERROR.businessMessage(), tradeNo, objectMapper.writeValueAsString(jdResponse2202));
				} else {
					//业务失败
					jdResponseView = new JdResponseView(JdResponseStatus.UNKNOWN_ERROR.businessCode(),
							JdResponseStatus.UNKNOWN_ERROR.businessMessage(), tradeNo, "{}");
				}
			} else {
				jdResponseView = new JdResponseView(JdResponseStatus.UNKNOWN_ERROR.businessCode(),
						JdResponseStatus.UNKNOWN_ERROR.businessMessage(), tradeNo, "{}");
			}
		} catch (IOException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		return jdResponseView;
	}
}
