package com.uranus.platform.business.jd.callback;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.business.jd.entity.status.JdResponseStatus;
import com.uranus.platform.business.jd.entity.vo.JdCallbackRequestView;
import com.uranus.platform.business.jd.entity.vo.JdCallbackResponseView;
import com.uranus.platform.business.pub.dao.CallbackFailLogDataMapper;
import com.uranus.platform.business.pub.dao.CallbackLogDataMapper;
import com.uranus.platform.business.pub.entity.po.CallbackFailLogData;
import com.uranus.platform.business.pub.entity.po.CallbackLogData;
import com.uranus.platform.business.pub.mq.domain.MQParmsDomain;
import com.uranus.platform.business.pub.mq.producer.JDMQProducerManager;
import com.uranus.platform.utils.jd.security.SignEnvelopServiceKey;
import com.uranus.platform.utils.status.CmsBusinessStatus;
import com.uranus.tools.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 京东回调HTTPClient客户端
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年8月20日下午3:35:49
 */
@Configuration
@Slf4j
public class JdCallbackHttpClient {
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private CallbackLogDataMapper callbackLogDataMapper;
	@Autowired
    private SignEnvelopServiceKey signEnvelopServiceKey;
	@Autowired
	private CallbackFailLogDataMapper callbackFailLogDataMapper;
	@Autowired
	private JDMQProducerManager jDMQProducerManager;
	
	/**  
	* 京东回调地址
	*/  
	@Value("${jd.callBackURI}")
	private String jdCallbackURI;
	/**
	 * 安全秘钥
	 */
	@Value("${jd.secretKey}")
	private String secretKey;

	/**
	 * 加密算法
	 */
	@Value("${jd.encryptAlg}")
	private String encryptAlg;

	/**
	 * 签名算法
	 */
	@Value("${jd.signAlg}")
	private String signAlg;

	/**
	 * 时间戳格式
	 */
	@Value("${jd.timestamp}")
	private String timestamp;

	/**
	 * 版本
	 */
	@Value("${jd.version}")
	private String version;
	
	public JdCallbackResponseView callbackJD(JdCallbackRequestView jdCallbackRequestView, String transNo) {
		JdCallbackResponseView jdCallbackResponseView = new JdCallbackResponseView();
		String requestDTO = "";//将请求对象转换成String
		String result = "";//京东返回
		String sysDate = DateUtils.nowDateFormat();//开始日期
		String begTime = DateUtils.nowTimeFormat();//开始时间
		String callbackSts = "02";
		String txCode = "";
		String requestLogInfo = "";
		try {
			if(JdResponseStatus.CALLBACK_CREDIT.businessCode().equals(jdCallbackRequestView.getMessageType())) {
				txCode = CmsBusinessStatus.WS_2002.businessCode();
			} else if(JdResponseStatus.CALLBACK_LOAN.businessCode().equals(jdCallbackRequestView.getMessageType())) {
				txCode = CmsBusinessStatus.WS_2102.businessCode();
			} else if(JdResponseStatus.CALLBACK_TRANSFER_PLANS.businessCode().equals(jdCallbackRequestView.getMessageType())) {
				txCode = CmsBusinessStatus.WS_2311.businessCode();
			}
			/**
			 * 设置报文头
			 */
			HttpHeaders headers = new HttpHeaders();
	        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
	        headers.setContentType(type);
	        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
	        headers.add("secret-key", secretKey);//安全秘钥
	        headers.add("encrypt-alg", encryptAlg);//加密算法
	        headers.add("sign-alg", signAlg);//签名算法
	        headers.add("timestamp", new SimpleDateFormat(timestamp).format(new Date()));//时间戳
	        /**
	         * 设置报文体
	         */
	        requestLogInfo = objectMapper.writeValueAsString(jdCallbackRequestView);
	        //加密报文体
	        String encodeData = signEnvelopServiceKey.signEnvelop(jdCallbackRequestView.getBizContent());
	        jdCallbackRequestView.setBizContent(encodeData);
	        
	        requestDTO = objectMapper.writeValueAsString(jdCallbackRequestView);
	       
	        HttpEntity<String> formEntity = new HttpEntity<String>(requestDTO, headers);
	        /**
			 * 回调京东，并接收京东响应
			 */
	        log.info(">>>>>>>>>>>>>>>>回调JD开始>>>>>>>>>>>>>>>>");
	        result = restTemplate.postForObject(jdCallbackURI, formEntity, String.class);
	        log.info(">>>>>>>>>>>>>>>>回调JD结束,响应结果>>>>>>>>>>>>>>>>" + result);
	        /**
			 * 将京东响应转换成对象
			 */
	        if(result != null && !"".equals(result)) {
	        	jdCallbackResponseView = objectMapper.readValue(result, JdCallbackResponseView.class);
	        	if(JdResponseStatus.SUCCESS.businessCode().equals(jdCallbackResponseView.getCode())) {
	        		callbackSts = "01";
	        	}
	        }
		
	        //插入日志表
	        CallbackLogData CallbackLogData = new CallbackLogData(transNo, CmsBusinessStatus.JD.businessCode(), 
	        		txCode, callbackSts, sysDate, begTime, 
	        		DateUtils.nowTimeFormat(), requestLogInfo, result);
    		callbackLogDataMapper.insert(CallbackLogData);
		} catch (IOException e) {
			log.error(">>>>>>>>>>>>>>>>>>>回调JD异常，异常信息>>>>>>>>>>>>>>>>>>>" + e.getMessage());
			//插入日志表
			CallbackLogData CallbackLogData = new CallbackLogData(transNo, CmsBusinessStatus.JD.businessCode(), 
	        		txCode, callbackSts, sysDate, begTime, 
	        		DateUtils.nowTimeFormat(), requestLogInfo, e.getMessage());
    		callbackLogDataMapper.insert(CallbackLogData);
			e.printStackTrace();
		}
		return jdCallbackResponseView;
	}
	
	/**  
	* @Description
	* 		回调京东，并将回调失败的数据重新放到MQ
	*/  
	public void callbackOrSendMQ(JdCallbackRequestView jdCallbackRequestView, String transNo, int delayLevel, String txCode, String transType) {
		//回调京东
		JdCallbackResponseView jdCallbackResponseView = callbackJD(jdCallbackRequestView, transNo);
		if(JdResponseStatus.SUCCESS.businessCode().equals(jdCallbackResponseView.getCode())) {
			//回调成功
			log.info("回调成功:   { }，业务编号【" + transNo + "】");
		}else {//回调京东,若【京东响应失败】，则需要重新回调京东
			if(Integer.parseInt(CmsBusinessStatus.MQ_DELAY_LEVEL_FOUR.businessCode()) <= delayLevel) {
				log.info("消息执行三次未果，京东响应: [{}]，业务编号【" + transNo + "】");
				CallbackFailLogData callbackFailLogData = new CallbackFailLogData(transNo, 
						CmsBusinessStatus.JD.businessCode(), txCode, 
						DateUtils.nowDateFormat(), DateUtils.nowTimeFormat());
				callbackFailLogDataMapper.insert(callbackFailLogData);
			} else {
				log.info("京东响应失败，重复回调，京东响应: [{}]，业务编号【" + transNo + "】\"，回调次数：{}，"+ (delayLevel - 1));
				/**
				 * 处理成功后发送，将任务放到MQ消息队列中
				 * 
				 * 	1. 创建MQ消息参数对象
				 * 	2. 将消息发送到MQ队列
				 */
				MQParmsDomain message = new MQParmsDomain();
				message.setTaskData(transNo);
				message.setTaskType(transType);
				message.setDelayLevel(delayLevel + 1);
				jDMQProducerManager.send(message);
			}
		}
	}

	/**  
	* @Description
	* 			回调失败，将数据重新发送到MQ
	*/  
	public void callbackFailAndSendMQ(String transNo, int delayLevel, String businessCode, String businessType) {
		// 响应失败
		if(Integer.parseInt(CmsBusinessStatus.MQ_DELAY_LEVEL_FOUR.businessCode()) <= delayLevel) {
			log.info("消息执行三次未果，京东响应: [{}]，业务编号【" + transNo + "】");
			CallbackFailLogData callbackFailLogData = new CallbackFailLogData(transNo, 
					CmsBusinessStatus.JD.businessCode(), businessCode, 
					DateUtils.nowDateFormat(), DateUtils.nowTimeFormat());
			callbackFailLogDataMapper.insert(callbackFailLogData);
		} else {
			log.info("京东响应失败，重复回调，京东响应: [{}]，业务编号【" + transNo + "】\"，回调次数：{}，"+ (delayLevel - 1));
			/**
			 * 处理成功后发送，将任务放到MQ消息队列中
			 * 
			 * 	1. 创建MQ消息参数对象
			 * 	2. 将消息发送到MQ队列
			 */
			MQParmsDomain message = new MQParmsDomain();
			message.setTaskData(transNo);
			message.setTaskType(businessType);
			message.setDelayLevel(delayLevel + 1);
			jDMQProducerManager.send(message);
		}
	}
}
