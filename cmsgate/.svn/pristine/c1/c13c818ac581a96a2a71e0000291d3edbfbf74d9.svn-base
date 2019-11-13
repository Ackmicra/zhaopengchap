package com.uranus.platform.business.jd.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class JdRepayPlanServiceTest {

	@Autowired
	MockMvc mvc;

	/**
	 * 
	* @Title：syncRepayPlanTest 
	* @Description：还款计划同步
	* @param ： 
	* @return ：void 
	* @throws
	 */
	@Test
	void syncRepayPlanTest() {
		
		RequestBuilder request;

		try {
			
		request= post("http://192.168.1.178:10002/loan/syncRepayPlan")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Content-Type", "application/json")
				.header("secret-key", "frYNAkpksD9QwyrZTGr7CXNBT2rgiEsm")
				.header("encrypt-alg", "aks-3des-cbc")
				.header("sign-alg", "aks-sha1")
				.header("timestamp", "20190813162314339")
				.header("version", "1.0.0")
				.content("{\"channelId\":\"2004\",\"channelProdNo\":\"2004101\",\"outTradeNo\":\"1111111\",\"shanyin_001\":\"001\",\"bizContent\":\"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\"\r\n" + 
						"}");
			
			mvc.perform(request).andDo(print());
			   
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	* @Title：changeRepayPlanTest 
	* @Description 还款计划变更
	* @param ： 
	* @return ：void 
	* @throws
	 */
	@Test
	void changeRepayPlanTest() {
		
		RequestBuilder request;
		
		try {
			
		request= post("http://192.168.1.178:10002/loan/changeRepayPlan")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Content-Type", "application/json")
				.header("secret-key", "frYNAkpksD9QwyrZTGr7CXNBT2rgiEsm")
				.header("encrypt-alg", "aks-3des-cbc")
				.header("sign-alg", "aks-sha1")
				.header("timestamp", "20190813162314339")
				.header("version", "1.0.0")
				.content("{\"channelId\":\"2004\",\"channelProdNo\":\"2004101\",\"outTradeNo\":\"1111111\",\"shanyin_001\":\"001\",\"bizContent\":\"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\"\r\n" + 
						"}");
			
			mvc.perform(request).andDo(print());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
