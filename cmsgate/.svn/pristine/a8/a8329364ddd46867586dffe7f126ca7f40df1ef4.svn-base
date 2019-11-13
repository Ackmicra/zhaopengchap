package com.uranus.platform.business.jd.service.trans;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.business.jd.entity.po.JdSigningData;
import com.uranus.platform.business.jd.entity.pojo.JdResponse2011;
import com.uranus.platform.business.jd.entity.status.JdResponseStatus;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;
import com.uranus.platform.business.pub.entity.dto.Request2011Dto;
import com.uranus.platform.business.pub.entity.dto.Response2011Dto;
import com.uranus.platform.business.pub.entity.po.ParmDicData;
import com.uranus.platform.business.pub.service.ParmDicService;
import com.uranus.platform.business.ws.process.Request;
import com.uranus.platform.business.ws.process.Response;
import com.uranus.platform.business.ws.service.ProcessService;
import com.uranus.platform.utils.exception.PlatformExceptionFactory;
import com.uranus.platform.utils.status.CmsBusinessStatus;
import com.uranus.tools.utils.DateUtils;

/**
 * @Description: 将小微接口响应转换为京东接口响应
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年8月8日下午4:11:58
 */
@Service
public class SignTrans2011Service {
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProcessService processService;
	@Autowired
	private ParmDicService parmDicService;

	
	/**  
	* @Description 拼装报文、请求小微、转换响应
	* @param tradeNo 交易流水号
	* @param brNo 合作机构编号
	* @param projNo 信托项目编号
	* @param jdSigningData 京东数据
	* @return 京东响应数据、京东业务数据
	*/  
	public Map<String, Object> request2011(String tradeNo, String brNo, String projNo, JdSigningData jdSigningData) {
		//拼发送小微业务数据
		Request request = getRequest2011(tradeNo, brNo, projNo, jdSigningData);
		
		//发送小微系统
		Response response = processService.wsProcess(CmsBusinessStatus.MFS.businessCode(), request);
		
		//转换小微的响应为京东所需响应数据
		Map<String, Object> map = transSignResponse(tradeNo, response);
		
		return map;
	}
	
	/**  
	* @Description: 拼发送小微请求报文
	* @param brNo
	* @param projNo
	* @param jdSigningData
	* @return 小微请求报文
	*/  
	public Request getRequest2011(String tradeNo, String brNo, String projNo, JdSigningData jdSigningData) {
		
		//转换支付渠道字典项
		ParmDicData cardChnDic = parmDicService.getParmDic("PAYMENT_CHANNEL", jdSigningData.getPaymentChannel().toString());
		//转换支付渠道字典项
		ParmDicData idTypeDic = parmDicService.getParmDic("SIGN_ID_TYPE", jdSigningData.getPayeeCeritificateType());
		
		//转换支付渠道字典项
		ParmDicData bankCodeDic = parmDicService.getParmDic("BANK_CODE", jdSigningData.getPayeeBankCode());
		Request2011Dto requestdto = new Request2011Dto();
		requestdto.setBrNo(brNo);
		requestdto.setProjNo(projNo);
		requestdto.setCustName(jdSigningData.getPayeeAccountName());
		requestdto.setIdNo(jdSigningData.getPayeeCeritificateNo());
		requestdto.setPhoneNo(jdSigningData.getPayeeMobileNo());
		requestdto.setAcno(jdSigningData.getPayeeAccountNo());
		requestdto.setAcType("1");
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
		//拼发送小微报文
		Request request = new Request();
		request.setTxCode(CmsBusinessStatus.WS_2011.businessCode());								//接口编号
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
	* @Description 转换签约申请接口响应
	* @param tradeNo 交易流水号
	* @param response 小微响应
	* @return 京东响应
	*/  
	public Map<String, Object> transSignResponse(String tradeNo, Response response) {
		Map<String, Object> map = new HashMap<String, Object>();
		JdResponseView jdResponseDto = null;
		try {
			if(response != null) {
				//判断业务是否成功
				if(CmsBusinessStatus.MFS_SUCCESS.businessCode().equals(response.getRespCode())) {
					JdResponse2011 jdResponse2011Dto = new JdResponse2011();
					Response2011Dto response2011Dto = objectMapper.readValue(response.getContent(), Response2011Dto.class);
					String dealNo = response2011Dto.getDealNo();
					String serialNo = response2011Dto.getSerialNo();
					String dealDesc = response2011Dto.getDealDesc();
					if(CmsBusinessStatus.SIGN_SUCCESS.businessCode().equals(dealNo)) {
						jdResponse2011Dto.setSignTransactionNo(serialNo);
						jdResponse2011Dto.setIsSigned(1);//首次签约
						jdResponseDto = new JdResponseView(JdResponseStatus.SUCCESS.businessCode(), 
								JdResponseStatus.SUCCESS.businessMessage(), 
								tradeNo, objectMapper.writeValueAsString(jdResponse2011Dto));
						map.put("jdResponseDto", jdResponseDto);
						map.put("TransactionNo", serialNo);
						
					}else if(CmsBusinessStatus.SIGN_FAIL.businessCode().equals(dealNo)){
						if(dealDesc.contains("已鉴权成功,签约协议号")) {
							jdResponse2011Dto.setSignTransactionNo(serialNo);
							jdResponse2011Dto.setIsSigned(2);//重复签约
							jdResponse2011Dto.setSignNo("签约协议号");
							jdResponseDto = new JdResponseView(JdResponseStatus.SUCCESS.businessCode(), 
									JdResponseStatus.SUCCESS.businessMessage(), 
									tradeNo, objectMapper.writeValueAsString(jdResponse2011Dto));
							map.put("jdResponseDto", jdResponseDto);
						}else {
							jdResponseDto = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(), 
									dealDesc, tradeNo, "{}");
							map.put("jdResponseDto", jdResponseDto);
						}
					}else {
						jdResponseDto = new JdResponseView(JdResponseStatus.UNKNOWN_ERROR.businessCode(), 
								JdResponseStatus.UNKNOWN_ERROR.businessMessage(), tradeNo, "{}");
						map.put("jdResponseDto", jdResponseDto);
					}
				}else {
					//如果业务不成功
					//传给JD——业务返回码、业务返回描述、交易流水号
					jdResponseDto = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(), 
							response.getRespDesc(), tradeNo, "{}");
					map.put("jdResponseDto", jdResponseDto);
				}
			} else {
				jdResponseDto = new JdResponseView(JdResponseStatus.UNKNOWN_ERROR.businessCode(), 
						"响应数据为空", tradeNo, "{}");
				map.put("jdResponseDto", jdResponseDto);
			}
		} catch (IOException e) {
			throw PlatformExceptionFactory.jsonParseException(JdResponseStatus.UNKNOWN_ERROR).build(e);
		}
		return map;
	}
	
	
}
