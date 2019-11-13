package com.uranus.platform.business.ws.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.business.pub.entity.po.WsBaseData;
import com.uranus.platform.business.pub.service.WsBaseService;
import com.uranus.platform.business.ws.login.InfWsLogin;
import com.uranus.platform.business.ws.login.LoginRequest;
import com.uranus.platform.business.ws.login.Response;
import com.uranus.platform.business.ws.service.LoginService;
import com.uranus.platform.utils.status.CmsBusinessStatus;
import com.uranus.tools.utils.DateUtils;

/**
 * @Description: 调用小微登陆接口
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年8月3日下午6:35:39
 */
@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	private InfWsLogin infWsLogin;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private WsBaseService wsBaseService;
	
	/**	请求小微登陆接口
	 * @param loginResponse 请求报文
	 * @param  busSide 交易方
	 * @return 小微登陆返回结果
	 */
	@Override
	public Response wsLogin(String busSide, LoginRequest loginRequest) {
		Response response = null;
		WsBaseData wsBase = new WsBaseData();
		try {
			wsBase.setBrNo(loginRequest.getBrNo());
			wsBase.setBusSide(busSide);
			wsBase.setTxCode(busSide+loginRequest.getTxCode());
			wsBase.setReqContent(objectMapper.writeValueAsString(loginRequest));
			wsBase.setWsDate(DateUtils.nowDateFormat());
			wsBase.setWsSerial(loginRequest.getReqSerial());
			wsBase.setWsTime(DateUtils.nowTimeFormat());
			//调用登陆接口
			response = infWsLogin.login(loginRequest);
			
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
