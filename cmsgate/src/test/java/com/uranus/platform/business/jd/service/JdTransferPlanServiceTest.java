package com.uranus.platform.business.jd.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
class JdTransferPlanServiceTest {

	@Autowired
	MockMvc mvc;

	/**
	 * 
	* @Title：transferPlansTest 
	* @Description：扣款计划上送测试
	* @param ： 
	* @return ：void 
	* @throws
	 */
	@Test
	void transferPlansTest() {

		RequestBuilder request;

		try {
			
		request = post("http://192.168.1.178:10002/loan/transferPlans")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Content-Type", "application/json")
				.header("secret-key", "P72MS3yGxmDdRuVyFG4ivQDUsp2U7rnk")
				.header("encrypt-alg", "aks-3des-cbc")
				.header("sign-alg", "aks-sha1")
				.header("timestamp", "20190813162314339")
				.header("version", "1.0.0")
				.content("{\"channelId\":\"2004\",\"channelProdNo\":\"2004101\",\"outTradeNo\":\"1111111\",\"shanyin_001\":\"001\",\"bizContent\":\"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\"\r\n"
								+ "}");

			mvc.perform(request).andDo(print());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @Title：repurchaseApplyTest 
	* @Description：回购申请测试
	* @param ： 
	* @return ：void 
	* @throws
	 */
	@Test
	void repurchaseApplyTest() {
		
		RequestBuilder request;
		
		try {
			
		request = get("http://192.168.1.178:10002/loan/repurchaseApply")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Content-Type", "application/json")
				.header("secret-key", "P72MS3yGxmDdRuVyFG4ivQDUsp2U7rnk")
				.header("encrypt-alg", "aks-3des-cbc")
				.header("sign-alg", "aks-sha1")
				.header("timestamp", "20190813162314339")
				.header("version", "1.0.0").content(
						"{\"channelId\":\"2004\",\"channelProdNo\":\"2004101\",\"outTradeNo\":\"1111111\",\"shanyin_001\":\"001\",\"bizContent\":\"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\"\r\n"
								+ "}");
			
			mvc.perform(request).andDo(print());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @Title：paymentOrdertest 
	* @Description：支付订单测试
	* @param ： 
	* @return ：void 
	* @throws
	 */
	@Test
	void paymentOrdertest() {

		RequestBuilder request;

		try {
			
		request= post("http://192.168.1.178:10002/loan/paymentOrder")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Content-Type", "application/json")
				.header("secret-key", "P72MS3yGxmDdRuVyFG4ivQDUsp2U7rnk")
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
