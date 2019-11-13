package com.uranus.platform.business.jd.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.uranus.platform.business.jd.entity.vo.JdCallbackRequestView;

@SpringBootTest
class JdTransferPlanCallbackServiceTest {

	@Autowired
	JdTransferPlanCallbackService jdTransferPlanCallbackService;
	
	@Test
	void test() {
		JdCallbackRequestView jdCallbackRequestView = jdTransferPlanCallbackService.transferPlanCallback("00002", 2,"03");
		System.out.println(jdCallbackRequestView.toString());
	}

}
