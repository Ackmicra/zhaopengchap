package com.uranus.platform.business.mq.custom;

import javax.annotation.Resource;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;

import com.uranus.platform.business.pub.mq.domain.MQParmsDomain;
import com.uranus.tools.utils.DateUtils;

@SpringBootTest
public class MQProducerTest{
	@Resource
    private RocketMQTemplate rocketMQTemplate;
    
	@Test
    public void test() throws Exception {
        
        System.out.println("放到MQ的时间======" + DateUtils.nowTimeFormat());
        MQParmsDomain mq1 = new MQParmsDomain();
       /**
        * CALLBACK_AUDIT_RESULT("01","审核结果回调"),
		    CALLBACK_LOAN_PAYMENTS_RESULT("02","放款结果回调"),
		    CALLBACK_TRANSFER_PLAN_RESULT("03","扣款计划校验结果回调"),
		    CALLBACK_PAYMENT_ORDER_RESULT("04","支付订单结果回调"),
        */
        mq1.setTaskType("03");
    	mq1.setTaskData("1169195314305146880");
    	mq1.setDelayLevel(2);
    	rocketMQTemplate.syncSend("JD_MFS_MESSAGE", MessageBuilder.withPayload(mq1).build(), 5000, mq1.getDelayLevel());
	}
    
	    
}
