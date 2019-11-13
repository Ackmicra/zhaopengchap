package com.uranus.platform.business.ws.service;

import com.uranus.platform.business.ws.login.LoginRequest;
import com.uranus.platform.business.ws.login.Response;

/**
 * @Description: 调用小微登陆接口
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年8月3日下午6:35:39
 */
public interface LoginService {

	/**	请求小微登陆接口
	 * @param loginResponse 请求报文
	 * @param  busSide 交易方
	 * @return 小微登陆返回结果
	 */
	public Response wsLogin(String busSide, LoginRequest loginRequest);
}
