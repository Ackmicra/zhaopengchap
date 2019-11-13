package com.uranus.platform.business.ws.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.uranus.platform.business.ModelTest;
import com.uranus.platform.business.ws.search.Request;
import com.uranus.platform.business.ws.search.Response;
import com.uranus.platform.business.ws.utils.DecryptUtil;
import com.uranus.platform.business.ws.utils.EncrypUtil;


class SearchServiceTest extends ModelTest{
	@Autowired
	private SearchService searchService;
	@Test
	void testSearch() {
		long b = System.currentTimeMillis();
		SimpleDateFormat dfDate = new SimpleDateFormat("HH:mm");
		SimpleDateFormat dfTime = new SimpleDateFormat("yyyyMMdd");
		
		Request request = new Request();
		request.setTxCode("2023");								//接口编号
		request.setBrNo("2003");							//机构号
		request.setReqDate(dfDate.format(new Date()));			//设置请求日期
		request.setToken("CXOBtpMwPl803Z5WmsRIaCCM6yBTcfG0");	//设置token
		request.setReqTime(dfTime.format(new Date()));			//设置请求时间
		request.setReqSerial("00000000002");						//设置请求流水号
		
		String ss= "{\"brNo\":\"2003\",\"bankAccount\":\"\",\"batNo\":\"REP_1000038\"}";
		
	    System.out.println("--------------------------------------------------------");
		System.out.println(request.getTxCode());
		System.out.println(ss);
		System.out.println("--------------------------------------------------------");
		
		EncrypUtil eu = new EncrypUtil();
		String cont = eu.encrypt("2003sat", request.getTxCode(), ss, "LosSuDVBbmjxHptaYGZr3g0JOJbTyJgt");
		request.setContent(cont);
		
		Response response = searchService.search("JD", request);
		
		DecryptUtil util = new DecryptUtil();
		String conts = util.decrypt("2003sat", request.getTxCode(), response.getContent(), "LosSuDVBbmjxHptaYGZr3g0JOJbTyJgt");
		response.setContent(conts);
		
		System.out.println(response.getRespCode());
		System.out.println(response.getRespDesc());
		System.out.println(response.getContent());
		//打印接口使用时间
		long e = System.currentTimeMillis();
		System.out.println("时间："+((e-b)/1000));
	}

}
