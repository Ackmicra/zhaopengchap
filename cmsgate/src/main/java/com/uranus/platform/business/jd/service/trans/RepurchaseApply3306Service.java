package com.uranus.platform.business.jd.service.trans;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.business.jd.entity.po.JdRepurchaseApplyData;
import com.uranus.platform.business.jd.entity.pojo.JdResponse3306;
import com.uranus.platform.business.jd.entity.status.JdResponseStatus;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;
import com.uranus.platform.business.pub.entity.dto.Request3306Dto;
import com.uranus.platform.business.ws.search.Request;
import com.uranus.platform.business.ws.search.Response;
import com.uranus.platform.business.ws.service.SearchService;
import com.uranus.platform.utils.exception.PlatformExceptionFactory;
import com.uranus.platform.utils.status.CmsBusinessStatus;
import com.uranus.tools.utils.DateUtils;

/**
 * 
* @ClassName:：RepurchaseApply3306Service 
* @Description： 回购申请
* @author ：chenwendong
* @date ：2019年8月20日 下午2:39:38 
*
 */
@Service
public class RepurchaseApply3306Service {
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private SearchService searchService;


	/**
	 * 
	* @Title：request3306 
	* @Description：发送小微3306
	* @param ：@param brNo
	* @param ：@param jdRepurchaseApplyData
	* @param ：@param tradeNo
	* @param ：@param projNo
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	public JdResponseView request3306(String brNo,JdRepurchaseApplyData jdRepurchaseApplyData,String tradeNo,String projNo) {
		// 拼发送小微业务数据
		Request request = getRequest3306(brNo, tradeNo, jdRepurchaseApplyData,projNo);

		// 发送小微系统
		Response response = searchService.search(CmsBusinessStatus.MFS.businessCode(), request);

		// 转换小微的响应为京东所需响应数据
		JdResponseView jdResponseDto = repurchaseApplyResponse(tradeNo, response);

		return jdResponseDto;
	}
	/**
	 * 
	* @Title：getRequest3306 
	* @Description：拼接3306报文
	* @param ：@param brNo
	* @param ：@param tradeNo
	* @param ：@param jdRepurchaseApplyData
	* @param ：@param projNo
	* @param ：@return 
	* @return ：Request 
	* @throws
	 */
	private Request getRequest3306(String brNo, String tradeNo, JdRepurchaseApplyData jdRepurchaseApplyData,String projNo) {


		Request3306Dto request3306Dto = new Request3306Dto();
		request3306Dto.setBrNo(brNo);
		request3306Dto.setPactNo(jdRepurchaseApplyData.getApplicationNo());
		request3306Dto.setProjNo(projNo);

		// 拼发送小微报文
		Request request = new Request();
		request.setTxCode(CmsBusinessStatus.WS_3306.businessCode()); // 接口编号
		request.setBrNo(brNo); // 机构号
		request.setReqDate(DateUtils.nowDateFormat()); // 设置请求日期
		request.setToken("test"); // 设置token
		request.setReqTime(DateUtils.nowTimeFormat()); // 设置请求时间
		request.setReqSerial(tradeNo); // 设置请求流水号
		try {
			request.setContent(objectMapper.writeValueAsString(request3306Dto));
		} catch (JsonProcessingException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		return request;
	}
	/**
	 * 
	* @Title：repurchaseApplyResponse 
	* @Description：转换小微的响应为京东所需响应数据
	* @param ：@param tradeNo
	* @param ：@param response
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	private JdResponseView repurchaseApplyResponse(String tradeNo, Response response) {
		JdResponseView jdResponseDto = null;
		if (response != null) {
			String responseStr = response.getContent();
			// 判断业务是否成功
			if ("0000".equals(response.getRespCode()) || "0001".equals(response.getRespCode())) {
				try {
					List<JdResponse3306> list = objectMapper.readValue(responseStr,
							new TypeReference<List<JdResponse3306>>() {
							});
					if (list.isEmpty()) {
						jdResponseDto = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(),
								JdResponseStatus.DATA_ERROR.businessMessage(), tradeNo, responseStr);
					} else {
						jdResponseDto = new JdResponseView(JdResponseStatus.SUCCESS.businessCode(),
								JdResponseStatus.SUCCESS.businessMessage(), tradeNo, responseStr);
					}
				} catch (IOException e) {
					throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
				}

			} else {
				jdResponseDto = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(), response.getRespDesc(),
						tradeNo, responseStr);
			}
		} else {
			jdResponseDto = new JdResponseView(JdResponseStatus.UNKNOWN_ERROR.businessCode(), "响应数据为空", tradeNo, "{}");
		}
		return jdResponseDto;
	}
}
