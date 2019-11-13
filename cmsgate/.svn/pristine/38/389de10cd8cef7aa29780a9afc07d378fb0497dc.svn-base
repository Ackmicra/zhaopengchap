package com.uranus.platform.business.ws.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.business.pub.entity.po.WsBaseData;
import com.uranus.platform.business.pub.service.WsBaseService;
import com.uranus.platform.business.ws.search.InfWsSearch;
import com.uranus.platform.business.ws.search.Request;
import com.uranus.platform.business.ws.search.Response;
import com.uranus.platform.business.ws.service.SearchService;
import com.uranus.platform.utils.status.CmsBusinessStatus;
import com.uranus.tools.utils.DateUtils;

/**
 * @Description: 调用小微业务查询接口
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年8月3日下午6:36:21
 */
@Service
public class SearchServiceImpl implements SearchService{
	
	@Autowired
	private InfWsSearch infWsSearch;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private WsBaseService wsBaseService;
	
	/**	请求小微业务查询接口
	 * @param request 请求报文
	 * @return 小微接口查询结果
	 */
	@Override
	public Response search(String busSide, Request request) {
		Response response = null;
		WsBaseData wsBase = new WsBaseData();
		try {
			wsBase.setBrNo(request.getBrNo());
			wsBase.setBusSide(busSide);
			wsBase.setTxCode(request.getTxCode());
			wsBase.setReqContent(objectMapper.writeValueAsString(request));
			wsBase.setWsDate(DateUtils.nowDateFormat());
			wsBase.setWsSerial(request.getReqSerial());
			wsBase.setWsTime(DateUtils.nowTimeFormat());
			
			//调用业务查询接口
			response = infWsSearch.search(request);
			
			wsBase.setRespContent(objectMapper.writeValueAsString(response));
			wsBase.setRespCode(response.getRespCode());
			wsBase.setRespDesc(response.getRespDesc());
			wsBase.setRespTime(DateUtils.nowTimeFormat());
			wsBase.setWsSts(CmsBusinessStatus.PROCESS.businessCode());
		} catch (JsonProcessingException e1) {
			wsBase.setRespContent(e1.getMessage());
			wsBase.setRespCode("9999");
			wsBase.setRespDesc("前置系统Json转换异常！");
			wsBase.setRespTime(DateUtils.nowTimeFormat());
		} catch (Exception e) {
			wsBase.setRespContent(e.getMessage());
			wsBase.setRespCode("9999");
			wsBase.setRespDesc("前置系统服务异常！");
			wsBase.setRespTime(DateUtils.nowTimeFormat());
		}
		//插入接口通讯日志
		wsBaseService.insert(wsBase);
		return response;
	}
}
