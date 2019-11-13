package com.uranus.platform.business.ws.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.uranus.platform.business.ws.process.Request;
import com.uranus.platform.business.ws.process.Response;
import com.uranus.platform.business.ws.utils.DecryptUtil;
import com.uranus.platform.business.ws.utils.EncrypUtil;

@SpringBootTest
class ProcessServiceTest {

	@Autowired
	private ProcessService processService;
	@Test
	void wsProcess() {
		long b = System.currentTimeMillis();
		SimpleDateFormat dfDate = new SimpleDateFormat("HH:mm");
		SimpleDateFormat dfTime = new SimpleDateFormat("yyyyMMdd");
		Request request = new Request();
		request.setTxCode("2101");								//接口编号
		request.setBrNo("2003");							//机构号
		request.setReqDate(dfDate.format(new Date()));			//设置请求日期
		request.setToken("CXOBtpMwPl803Z5WmsRIaCCM6yBTcfG0");	//设置token
		request.setReqTime(dfTime.format(new Date()));			//设置请求时间
		request.setReqSerial("00000000003");						//设置请求流水号
	    
		for (int i = 22; i < 23; i++) {
			String date = "20190613";
	//		int i = 65;
		    String batchNo  = "CS"+date+"0000"+i;	//批次号
		    String pactNo1 = "CS"+date+"0000"+i;	//合同号
		    String gcustPactNo1 = "P2016M11A-YGXD-003"+date+i;//实际抵押合同号 
		    String factPactNo1 = "P2016M11A-YGXD-002"+date+i; //实际合同号P2017M11A-XXXH-002SH-
		    String gageBrId = date+"00000"+i;	//合作机构押品编号
		    String projNo = "7667548";	//项目号3166044 5276598  7113480   3894248  7787276  7136300
		    String prdtNo = "200301";	//产品号
		    
		    String idNo = "310115199007129776";	//身份证号
		    String custName = "何豪三";		//贷款人姓名
		    String acNo = "6212262309006279756";	//扣放款账号
		    String bankCode = "002";	//银行代码   002工行 019广发 007招行 005建行
		    String pactAmt = "20000.00";	//合同金额
		    String lnRate = "0.6";		//合同月利率
		    String endDate = "20190611";//合同结束日期
		    String cardChn = "";		//支付渠道
		  //将全部的信息打包成Json串
		    String ss= "{\"brNo\":\"2003\",\"batNo\":\""+batchNo+"\",\"dataCnt\":\"2\"," +
	        "\"list\":[{\"pactNo\":\""+pactNo1+"\",\"projNo\":\""+projNo+"\",\"prdtNo\":\""+prdtNo+"\",\"pactAmt\":\""+pactAmt+"\"," +
	        		"\"birth\":\"19791221\",\"custName\":\""+custName+"\",\"termMon\":\"6\",\"termDay\":\"0\",\"vouType\":\"4\"," +
	        		"\"idType\":\"0\",\"idNo\":\""+idNo+"\",\"custType\":\"99\",\"sex\":\"1\",\"marriage\":\"10\",\"children\":\"0\"," +
	        		"\"edu\":\"10\",\"degree\":\"4\",\"telNo\":\"15823781632\",\"phoneNo\":\"15823781632\",\"postCode\":100001," +
	        		"\"postAddr\":\"五矿广场五矿广场五矿广场通讯地址222\",\"homeArea\":\"110000\",\"homeTel\":null,\"homeCode\":100001,\"homeAddr\":null,\"homeSts\":null," +
	        		"\"income\":\"333\",\"mateName\":\"\",\"mateIdtype\":\"0\",\"mateIdno\":\"\",\"mateWork\":null,\"mateTel\":null," +
	        		"\"feeTotal\":\"0\",\"lnRate\":\""+lnRate+"\",\"appArea\":\"110000\",\"appUse\":\"01\",\"endDate\":\""+endDate+"\"," +
	        		"\"payType\":\"02\",\"payDay\":\"\",\"vouAmt\":\"500.00\",\"cardChn\":\""+cardChn+"\"," +
	            "\"listAc\":[{\"acUse\":\"2\",\"acAmt\":\""+pactAmt+"\",\"acType\":\"11\",\"acno\":\""+acNo+"\"," +
	            				"\"acName\":\""+custName+"\",\"bankCode\":\""+bankCode+"\",\"bankProv\":\"11\",\"bankCity\":\"1101\",\"bankSite\":\"工商银行东城支行\"," +
	            				"\"idType\":\"0\",\"idNo\":\""+idNo+"\",\"phoneNo\":15823781632,\"validDate\":null,\"cvvNo\":null}," +
	//            			"{\"acUse\":\"2\",\"acAmt\":10000,\"acType\":\"11\",\"acno\":\"6225880132183000\"," +
	//            				"\"acName\":\""+custName+"\",\"bankCode\":\""+bankCode+"\",\"bankProv\":\"11\",\"bankCity\":\"1101\",\"bankSite\":\"工商银行东城支行\"," +
	//            				"\"idType\":\"0\",\"idNo\":\""+idNo+"\",\"phoneNo\":19902345678,\"validDate\":null,\"cvvNo\":null}," +
	            			"{\"acUse\":\"1\",\"acAmt\":null,\"acType\":\"11\",\"acno\":\""+acNo+"\",\"acName\":\""+custName+"\"," +
	            				"\"bankCode\":\""+bankCode+"\",\"bankProv\":null,\"bankCity\":null,\"bankSite\":\"中国农业银行东城支行\",\"idType\":\"0\"," +
	            				"\"idNo\":\""+idNo+"\",\"phoneNo\":15823781632,\"validDate\":null,\"cvvNo\":null}]," +
	            "\"listGage\":[{\"gcustPactNo\":\""+gcustPactNo1+"\",\"gLicType\":\"01\",\"gType\":\"213\",\"gLicno\":\"999999\"," +
	            				"\"gValue\":\"99999900000\",\"gBegBal\":\"650000000\",\"gSmType\":\"03\",\"gWorkType\":\"1\",\"gArea\":\"000000\"," +
	            				"\"gSpace\":\"104.44\",\"gageAddr\":\"五矿广场C座10层\",\"gageBrId\":\""+gageBrId+"\",\"gDesc\":\"押品描述\"," +
	            				"\"gYearCom\":\"2010\",\"gAreaType\":\"01\",\"gageRate\":\"1.7\",\"position\":\"01\",\"duoziduan\":\"0\"}]," +
	            "\"workType\":2,\"idEndDate\":\"20000101\",\"idPreDate\":\"20121010\",\"workSts\":null,\"workWay\":null,\"workCode\":null," +
	            "\"workAddr\":null,\"workDuty\":null,\"workTitle\":null,\"workYear\":null," +
	            "\"listCom\":[]," +
	            "\"listRel\":[]," +
	            "\"ifCar\":\"2\",\"ifCarCred\":\"2\",\"ifRoom\":\"2\",\"ifMort\":\"0\",\"ifCard\":\"2\",\"cardAmt\":\"0\",\"ifApp\":\"1\"," +
	            "\"ifId\":\"1\",\"ifPact\":\"1\",\"prePactNo\":null,\"czPactNo\":null,\"factPactNo\":\""+factPactNo1+"\"," +
	            "\"ifAgent\":\"02\",\"sales\":\"01\",\"ifLaunder\":\"02\",\"Launder\":\"03\",\"trade\":\"02\",\"country\":\"CHN\"," +
	            "\"homeIncome\":\"3333\",\"profession\":\"01\",\"repaySource\":\"01\",\"sigDate\":\"20180906\"," +
	            "\"imei\":\"222222\",\"tdid\":\"222222\",\"idfa\":\"222222\"}" +
	            "]}";
	            
		    
		    System.out.println("--------------------------------------------------------");
			System.out.println(request.getTxCode());
			System.out.println(ss);
			System.out.println("--------------------------------------------------------");
			EncrypUtil eu = new EncrypUtil();
			String cont = eu.encrypt("2003sat", request.getTxCode(), ss, "LosSuDVBbmjxHptaYGZr3g0JOJbTyJgt");
			request.setContent(cont);
			//调用服务端服务  
			Response response = processService.wsProcess("JD", request);
			DecryptUtil util = new DecryptUtil();
			String content = util.decrypt("2003sat", request.getTxCode(), response.getContent(), "LosSuDVBbmjxHptaYGZr3g0JOJbTyJgt");
			response.setContent(content);
			System.out.println(response.getRespCode());
			System.out.println(response.getRespDesc());
			System.out.println(response.getContent());
			long e = System.currentTimeMillis();
			System.out.println("时间："+((e-b)/1000));
		}
	
	}

}
