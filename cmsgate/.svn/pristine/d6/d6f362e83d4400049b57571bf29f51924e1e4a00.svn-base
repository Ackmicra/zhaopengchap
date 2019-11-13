package com.uranus.platform.business.jd.service.trans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.business.jd.entity.pojo.JDFile;
import com.uranus.platform.business.jd.entity.pojo.JDLoanfileUpload;
import com.uranus.platform.business.jd.entity.pojo.JDResponse2110;
import com.uranus.platform.business.jd.entity.status.JdResponseStatus;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;
import com.uranus.platform.business.pub.entity.dto.Request2108Dto;
import com.uranus.platform.business.pub.entity.dto.Request2110Dto;
import com.uranus.platform.business.pub.entity.dto.Response2108ListDto;
import com.uranus.platform.business.pub.entity.dto.Response2110Dto;
import com.uranus.platform.business.pub.entity.po.ParmDicData;
import com.uranus.platform.business.pub.service.CreateKeyService;
import com.uranus.platform.business.pub.service.ParmDicService;
import com.uranus.platform.business.ws.process.Request;
import com.uranus.platform.business.ws.process.Response;
import com.uranus.platform.business.ws.service.ProcessService;
import com.uranus.platform.business.ws.service.SearchService;
import com.uranus.platform.utils.exception.PlatformExceptionFactory;
import com.uranus.platform.utils.status.CmsBusinessStatus;
import com.uranus.tools.utils.DateUtils;

/**
 * @Describe 向小微2108贷款申请接口发送请求，并且向京东返回响应数据
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月20日 下午2:05:09
 * 
 */
@Service
public class LnfileUploadTrans2108Service {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProcessService processService;
	@Autowired
	private ParmDicService parmDicService;
	@Autowired
	private CreateKeyService createKeyService;
	@Autowired
	private SearchService searchService;
	/**
	 * @Description 拼装报文、请求小微、转换响应
	 * @param tradeNo 交易流水号
	 * @param brNo    合作机构编号
	 * @param projNo  信托项目编号
	 * @param Data    京东数据
	 * @return 京东响应数据、京东业务数据
	 */
	public JdResponseView request2108(String tradeNo, String brNo, String projNo, JDLoanfileUpload jDRequest) {
		// 拼发送小微业务数据
		Request request = getRequest2108(tradeNo, brNo, projNo, jDRequest);
		// 发送小微系统
		Response response = processService.wsProcess(CmsBusinessStatus.MFS.businessCode(), request); 
		// 转换小微的响应为京东所需响应数据
		JdResponseView jdResponseView = transLnfileUploadResponse(tradeNo, response);
		
		return jdResponseView;

	}
	
	/**  
	* @Description 拼装小微报文
	* @return 小微的请求报文
	*/  
	private Request getRequest2108(String tradeNo, String brNo, String projNo, JDLoanfileUpload jDRequest) {
		Request2108Dto requestDto = new Request2108Dto();
		List<Response2108ListDto> requestListDto = new ArrayList<Response2108ListDto>();
		List<JDFile> jdfiles = jDRequest.getFiles();
		Response2108ListDto Dto = new Response2108ListDto();
		//拼发业务数据
		for(JDFile file: jdfiles) {
			
			//截取文档类型
			String str=file.getFileName();
			int s = str.indexOf("_");
			String fileType = str.substring(s+1, s+7);//(待处理)
			
			//转换文件类型字典项
			ParmDicData fileTypeDic = parmDicService.getParmDic("DOC_TYPE", fileType);
			if (fileTypeDic != null) {
				Dto.setDocType(fileTypeDic.getMateCode());
			} else {
				Dto.setDocType(fileType);
			}
			
			//文档名称
			Dto.setDocName(file.getFileName());
			//Base64码
			Dto.setBaseCode(file.getContent());
			//业务场景类型
			Dto.setBusinessType("10");
			//业务号码
			Dto.setTransNo(jDRequest.getApplicationNo());
			
			requestListDto.add(Dto);
		}
		//创建批次号
		String batchNo = CmsBusinessStatus.JD.businessCode()+createKeyService.getBatchNoNo();
	    
	    //request请求赋值
 		requestDto.setBatNo(batchNo);
 		requestDto.setBrNo(brNo);
 		requestDto.setDataCnt(1);
 		requestDto.setList(requestListDto);
        //发送request请求给小微
 		Request request = new Request();
 		request.setTxCode(CmsBusinessStatus.WS_2108.businessCode()); // 接口编号
 		request.setBrNo(brNo); // 机构号
 		request.setReqDate(DateUtils.nowDateFormat()); // 设置请求日期
 		request.setToken("test"); // 设置token
 		request.setReqTime(DateUtils.nowTimeFormat()); // 设置请求时间
 		request.setReqSerial(tradeNo); // 设置请求流水号
 		try {
 			request.setContent(objectMapper.writeValueAsString(requestDto));
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
	private JdResponseView transLnfileUploadResponse(String tradeNo, Response response) {
		JdResponseView jdResponseView = null;
//		jdResponseView = new JdResponseView(JdResponseStatus.SUCCESS_FOR_DEAL.businessCode(),
//				JdResponseStatus.SUCCESS_FOR_DEAL.businessMessage(), tradeNo, "{}");
		if (response != null) {
			// 判断业务是否成功
			if (CmsBusinessStatus.MFS_SUCCESS.businessCode().equals(response.getRespCode()) || CmsBusinessStatus.MFS_HALF_SUCCESS.businessCode().equals(response.getRespCode())) {
				jdResponseView = new JdResponseView(JdResponseStatus.SUCCESS_FOR_DEAL.businessCode(),
						JdResponseStatus.SUCCESS_FOR_DEAL.businessMessage(), tradeNo, "{}");
			} else if (CmsBusinessStatus.INVALIDATE_PARAM.businessCode().equals(response.getRespCode())) {
				// 如果业务不成功
				// 传给JD具体错误信息(非必填)
				jdResponseView = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(),
						JdResponseStatus.DATA_ERROR.businessMessage(), tradeNo, "{}");
				//待接口完成后删除“9000”
			} else if(CmsBusinessStatus.BUSINESS_FAILURE.businessCode().equals(response.getRespCode())) {
				jdResponseView = new JdResponseView(CmsBusinessStatus.BUSINESS_FAILURE.businessCode(),
						CmsBusinessStatus.BUSINESS_FAILURE.businessMessage(), tradeNo, "{}");
			} 
			else if("9000".equals(response.getRespCode())) {
				jdResponseView = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(),
						JdResponseStatus.DATA_ERROR.businessMessage(), tradeNo, "{}");
			}
		} else {
			jdResponseView = new JdResponseView(JdResponseStatus.UNKNOWN_ERROR.businessCode(), CmsBusinessStatus.NO_RESPONSE.businessMessage(), tradeNo,
					null);
			   }
		return jdResponseView;
	}

	/**  
	* @Description
	* 			请求小微2110影像文件获取接口
	*/  
	public JdResponseView request2110(String tradeNo, String brNo, String applicationNo, String fileCode) {
		// 拼发送小微业务数据
		com.uranus.platform.business.ws.search.Request request = getRequest2110(tradeNo, brNo, applicationNo, fileCode);
		// 发送小微系统
		com.uranus.platform.business.ws.search.Response response = searchService.search(CmsBusinessStatus.MFS.businessCode(), request); 
		// 转换小微的响应为京东所需响应数据
		JdResponseView jdResponseView = transFindFilesResponse(tradeNo, response);
		
		return jdResponseView;
	}

	/**  
	* @Description
	* 			转换小微响应报文为京东响应报文
	*/  
	private JdResponseView transFindFilesResponse(String tradeNo,
			com.uranus.platform.business.ws.search.Response response) {
		JdResponseView jdResponseView = null;
		try {
			if (response != null) {
				// 判断业务是否成功
				if (CmsBusinessStatus.MFS_SUCCESS.businessCode().equals(response.getRespCode())) {
					if(response.getContent() == null) {
						jdResponseView = new JdResponseView(JdResponseStatus.UNKNOWN_ERROR.businessCode(), CmsBusinessStatus.NO_RESPONSE.businessMessage(), tradeNo,
								"");
					} else {
						// 查询成功
						List<Response2110Dto> list = objectMapper.readValue(response.getContent(),
								new TypeReference<List<Response2110Dto>>() {
						});
						if(list.size() > 0) {
							Response2110Dto response2110 = list.get(0);
							JDResponse2110 JDResponse2110 = new JDResponse2110();
							JDResponse2110.setErrorMessages(new ArrayList<String>());
							JDResponse2110.setApplicationNo(response2110.getTransNo());
							JDResponse2110.setFileType(response2110.getFileType().toUpperCase());
							JDResponse2110.setTransferType(JdResponseStatus.FINDING_FILE_SFPT.businessCode());
							JDResponse2110.setContractFile("");
							JDResponse2110.setContractContent(response2110.getBaseCode());
							JDResponse2110.setSignStatus(JdResponseStatus.SIGN_UNKNOWN_STATUS.businessCode());// 签章状态 
							/**
							 * 拼京东响应
							 */
							jdResponseView = new JdResponseView(JdResponseStatus.SUCCESS.businessCode(),
									JdResponseStatus.SUCCESS.businessMessage(), tradeNo, objectMapper.writeValueAsString(JDResponse2110));
						} else {
							jdResponseView = new JdResponseView(JdResponseStatus.UNKNOWN_ERROR.businessCode(), CmsBusinessStatus.NO_RESPONSE.businessMessage(), tradeNo,
									"");
						}
					}
			
				} else if (CmsBusinessStatus.INVALIDATE_PARAM.businessCode().equals(response.getRespCode())) {
					// 查询不到数据
					jdResponseView = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(),
							JdResponseStatus.DATA_ERROR.businessMessage(), tradeNo, "");
				} else {
					jdResponseView = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(),
							JdResponseStatus.DATA_ERROR.businessMessage(), tradeNo, "");
				}
			} else {
				jdResponseView = new JdResponseView(JdResponseStatus.UNKNOWN_ERROR.businessCode(), JdResponseStatus.UNKNOWN_ERROR.businessMessage(), tradeNo,
						"");
			}
		} catch (IOException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		return jdResponseView;
	}

	/**  
	* @Description
	* 			拼请求京东2110获取文件接口报文
	*/  
	private com.uranus.platform.business.ws.search.Request getRequest2110(String tradeNo, String brNo, String applicationNo, String fileCode) {
		Request2110Dto request2110 = new Request2110Dto();
		request2110.setBrNo(brNo);
		request2110.setTransNo(applicationNo);
		
		ParmDicData fileTypeDic = parmDicService.getParmDic("DOC_TYPE", fileCode);
		if (fileTypeDic != null) {
			request2110.setDocType(fileTypeDic.getMateCode());
		} else {
			request2110.setDocType(fileCode);
		}
        //发送request请求给小微
		com.uranus.platform.business.ws.search.Request request = new com.uranus.platform.business.ws.search.Request();
 		request.setTxCode(CmsBusinessStatus.WS_2110.businessCode()); // 接口编号
 		request.setBrNo(brNo); // 机构号
 		request.setReqDate(DateUtils.nowDateFormat()); // 设置请求日期
 		request.setToken("test"); // 设置token
 		request.setReqTime(DateUtils.nowTimeFormat()); // 设置请求时间
 		request.setReqSerial(tradeNo); // 设置请求流水号
 		try {
 			request.setContent(objectMapper.writeValueAsString(request2110));
 		} catch (JsonProcessingException e) {
 			throw PlatformExceptionFactory.jsonParseException(JdResponseStatus.UNKNOWN_ERROR).build(e);
 		}
 		return request;
	}
	
}
