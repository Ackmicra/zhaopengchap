package com.uranus.platform.business.jd.service.trans;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.business.jd.entity.po.JdSigningData;
import com.uranus.platform.business.jd.entity.pojo.JdResponse2012;
import com.uranus.platform.business.jd.entity.status.JdResponseStatus;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;
import com.uranus.platform.business.pub.entity.dto.Request2012Dto;
import com.uranus.platform.business.pub.entity.dto.Response2012Dto;
import com.uranus.platform.business.pub.entity.po.ParmDicData;
import com.uranus.platform.business.pub.service.ParmDicService;
import com.uranus.platform.business.ws.process.Request;
import com.uranus.platform.business.ws.process.Response;
import com.uranus.platform.business.ws.service.ProcessService;
import com.uranus.platform.utils.exception.PlatformExceptionFactory;
import com.uranus.platform.utils.status.CmsBusinessStatus;
import com.uranus.tools.utils.DateUtils;

@Service
public class SignTrans2012Service {
	
	@Autowired
	private ProcessService processService;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ParmDicService parmDicService;
	
	/**  
	* @Description 拼装小微报文、发送小微、转换响应
	* @param tradeNo 交易流水号
	* @param brNo 合作机构编号
	* @param jdSigningData 京东数据
	* @param serialNo 签约流水号
	* @param msgCode 短信验证码
	* @return
	*/  
	public JdResponseView request2012(String tradeNo, String brNo, 
			JdSigningData jdSigningData, String serialNo, String msgCode) {
		//拼发送小微业务数据
		Request request = getRequest2012(tradeNo, brNo, jdSigningData, serialNo, msgCode);
		
		//发送小微系统
		Response response = processService.wsProcess(CmsBusinessStatus.MFS.businessCode(), request);
		
		//转换小微的响应为京东所需响应数据
		JdResponseView jdResponseDto = transSignResponse(tradeNo, response);
		
		return jdResponseDto;
	}

	/**  
	* @Description 拼装小微报文
	* @return 小微的请求报文
	*/  
	private Request getRequest2012(String tradeNo, String brNo, 
			JdSigningData jdSigningData, String serialNo, String msgCode) {
		//转换支付渠道字典项
		ParmDicData cardChnDic = parmDicService.getParmDic("PAYMENT_CHANNEL", jdSigningData.getPaymentChannel().toString());
		//转换支付渠道字典项
		ParmDicData idTypeDic = parmDicService.getParmDic("SIGN_ID_TYPE", jdSigningData.getPayeeCeritificateType());
		
		//转换支付渠道字典项
		ParmDicData bankCodeDic = parmDicService.getParmDic("BANK_CODE", jdSigningData.getPayeeBankCode());
		Request2012Dto requestdto = new Request2012Dto();
		requestdto.setBrNo(brNo);
		requestdto.setProjNo(jdSigningData.getChannelProdNo());
		requestdto.setCustName(jdSigningData.getPayeeAccountName());
		requestdto.setIdNo(jdSigningData.getPayeeCeritificateNo());
		requestdto.setPhoneNo(jdSigningData.getPayeeMobileNo());
		requestdto.setAcno(jdSigningData.getPayeeAccountNo());
		requestdto.setAcType("1");//默认借记卡 1-个人借记卡  3-个人信用卡
		requestdto.setSerialNo(serialNo);
		requestdto.setMsgCode(msgCode);
		if(cardChnDic != null) {
			requestdto.setCardChn(cardChnDic.getMateCode());
		} else {
			requestdto.setCardChn(jdSigningData.getPaymentChannel().toString());
		}
		if(idTypeDic != null) {
			requestdto.setIdType(idTypeDic.getMateCode());
		} else {
			jdSigningData.getPayeeCeritificateType();
		}
		if(bankCodeDic != null) {
			requestdto.setBankCode(bankCodeDic.getMateCode());
		}
		requestdto.setValidDate("");
		requestdto.setCvvNo("");
		Request request = new Request();
		request.setTxCode(CmsBusinessStatus.WS_2012.businessCode());								//接口编号
		request.setBrNo(brNo);							//机构号
		request.setReqDate(DateUtils.nowDateFormat());			//设置请求日期
		request.setToken("test");	//设置token
		request.setReqTime(DateUtils.nowTimeFormat());			//设置请求时间
		request.setReqSerial(tradeNo);						//设置请求流水号
		try {
			request.setContent(objectMapper.writeValueAsString(requestdto));
		} catch (JsonProcessingException e) {
			throw PlatformExceptionFactory.jsonParseException(JdResponseStatus.UNKNOWN_ERROR).build(e);
		}
		return request;
	}
	
	/**  
	* @Description 转换小微的响应为京东所需响应
	* @param tradeNo 交易流水号
	* @param response 响应报文
	* @return 京东响应
	*/  
	private JdResponseView transSignResponse(String tradeNo, Response response) {
		JdResponseView jdResponseDto = null;
		try {
			if(response != null) {
				//判断业务是否成功
				if(CmsBusinessStatus.MFS_SUCCESS.businessCode().equals(response.getRespCode())) {
					Response2012Dto response2012Dto = objectMapper.readValue(response.getContent(), Response2012Dto.class);
					String dealNo = response2012Dto.getDealNo();
//					String dealDesc = response2012Dto.getDealDesc();
					String agreeCode = response2012Dto.getAgreeCode();
					
					String UpTime = response2012Dto.getUpTime();
					if(CmsBusinessStatus.CONFIRM_SIGN_SUCCESS.businessCode().equals(dealNo)) {
						//业务返回码
						JdResponse2012 jdSignConfirmDto = new JdResponse2012();
						jdSignConfirmDto.setSignStatus(JdResponseStatus.SIGN_SUCCESS.businessCode());//已签约
						jdSignConfirmDto.setSignNo(agreeCode);
						//签约时间处理
						String UpTime1	= UpTime.replaceAll(":", ""); 
			            String newUpTime = UpTime1.replaceAll(" ","");
						jdSignConfirmDto.setSignTime(newUpTime);
						
						jdResponseDto = new JdResponseView(JdResponseStatus.SUCCESS.businessCode(), 
								JdResponseStatus.SUCCESS.businessMessage(), 
								tradeNo, objectMapper.writeValueAsString(jdSignConfirmDto));
					} else if(CmsBusinessStatus.CONFIRM_SIGN_WORKING.businessCode().equals(dealNo)) {
						//业务返回码
						JdResponse2012 jdSignConfirmDto = new JdResponse2012();
						jdSignConfirmDto.setSignStatus(JdResponseStatus.SIGN_DEALING.businessCode());//处理中
						
						jdResponseDto = new JdResponseView(JdResponseStatus.SUCCESS_FOR_DEAL.businessCode(), 
								JdResponseStatus.SUCCESS_FOR_DEAL.businessMessage(), 
								tradeNo, objectMapper.writeValueAsString(jdSignConfirmDto));
					} else {
						jdResponseDto = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(), 
								JdResponseStatus.DATA_ERROR.businessMessage(), 
								tradeNo, "{}");
					}
				}else {
					//如果业务不成功
					//传给JD——业务返回码、业务返回描述、交易流水号
					jdResponseDto = new JdResponseView(JdResponseStatus.UNKNOWN_ERROR.businessCode(), 
							JdResponseStatus.UNKNOWN_ERROR.businessMessage(), 
							tradeNo, "{}");
				}
			} else {
				jdResponseDto = new JdResponseView(JdResponseStatus.UNKNOWN_ERROR.businessCode(), 
						JdResponseStatus.UNKNOWN_ERROR.businessMessage(), 
						tradeNo, "{}");
			}
		} catch (IOException e) {
			throw PlatformExceptionFactory.jsonParseException(JdResponseStatus.UNKNOWN_ERROR).build(e);
		}
		return jdResponseDto;
	}
}
