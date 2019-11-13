package com.uranus.platform.business.pub.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.uranus.platform.business.ModelTest;
import com.uranus.platform.business.pub.entity.po.ParmDicData;

class ParmDicDataServiceTest extends ModelTest {

	@Autowired
	private ParmDicService parmDicDataService;
	
	@Test
	void getParmDictest() throws InterruptedException {
		ParmDicData parmDicData = new ParmDicData();
		parmDicData.setKeyName("BANK_CODE");
		parmDicData.setOptCode("UNKNOWN");
		parmDicData.setOptName("未知银行");
		parmDicData.setMateCode("000");
		parmDicData.setMateName("未知机构");
		parmDicData.setOptSts("01");
		parmDicData = parmDicDataService.update(parmDicData);
//		ParmDicData parmDicData = parmDicDataService.selectByPrimaryKey("1000000000001");
		System.out.println(parmDicData.getKeyId()+"---"+parmDicData.getMateCode()+"--"+parmDicData.getMateName());
		
		Thread.sleep(5000);
		
		ParmDicData parmDic = parmDicDataService.getParmDic("BANK_CODE", "UNKNOWN");
//		ParmDicData parmDic = parmDicDataService.selectByPrimaryKey("1000000000001");
		System.out.println(parmDic.getMateCode()+"--"+parmDic.getMateName());
		
	}

}
