package com.uranus.platform.business.ws.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.uranus.platform.business.ws.login.LoginRequest;
import com.uranus.platform.business.ws.login.Response;

@SpringBootTest
class LoginServiceTest {

	@Autowired
	private LoginService loginService;
	@Test
	void wsLogin() {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setTxCode("0001");		//请求接口编号，这里去掉了ws 直接写编号名称 例如 请求 ws3101  设置txcode 为3101
		loginRequest.setBrNo("2001");		//新的接口 增加了在请求中添加 brNo合作机构号
		loginRequest.setReqDate("12:30");		//设置请求时间
		loginRequest.setReqTime("20160302");		//设置请求时间
		loginRequest.setReqSerial("321654");		//设置请求流水号
		//将请求数据打包为JSON发到 服务器
		String ss = "{\"brNo\":\"2001\",\"userNo\":\"20010001\",\"passWd\":\"20010001\"}";
		loginRequest.setContent(ss);
		Response response = loginService.wsLogin("JD", loginRequest);
		System.out.println(response.getRespCode());
		System.out.println(response.getRespDesc());
		System.out.println(response.getContent());
	}

}
