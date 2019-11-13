package com.uranus.platform.business.jd.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.business.jd.dao.JdLoanApplyInfoDataMapper;
import com.uranus.platform.business.jd.entity.po.JdLoanApplyInfoData;
import com.uranus.platform.business.jd.entity.pojo.JDLoanfileUpload;
import com.uranus.platform.business.jd.entity.pojo.JDRequest2110;
import com.uranus.platform.business.jd.entity.status.JdResponseStatus;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;
import com.uranus.platform.business.jd.service.JdLoanfileUploadService;
import com.uranus.platform.business.jd.service.trans.LnfileUploadTrans2108Service;
import com.uranus.platform.business.pub.entity.po.ProjBaseData;
import com.uranus.platform.business.pub.entity.po.WsBaseData;
import com.uranus.platform.business.pub.service.CreateKeyService;
import com.uranus.platform.business.pub.service.ProjBaseService;
import com.uranus.platform.business.pub.service.WsBaseService;
import com.uranus.platform.utils.exception.PlatformExceptionFactory;
import com.uranus.platform.utils.status.CmsBusinessStatus;
import com.uranus.tools.utils.DateUtils;
import com.uranus.tools.validator.UranusValidator;

/**
 * @Describe HTTP文件上传接口调小微2108影响信息上传接口
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月20日 上午11:13:49
 * 
 */
@Service
public class JdLoanfileUploadServiceImpl implements JdLoanfileUploadService {
	@Autowired
	private JdLoanApplyInfoDataMapper jdLoanApplyInfoDataMapper;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProjBaseService projBaseService;
	@Autowired
	private UranusValidator uranusValidator;
	@Autowired
	private LnfileUploadTrans2108Service lnfileUpoadTrans2108Service;
	@Autowired
	private CreateKeyService createKeyService;
	@Autowired
	private WsBaseService wsBaseService;
	/**  
	* @Description HTTP文件上传接口传入数据校验
	* @param projNo 信托项目编号
	* @param channelId 京东机构编号，AOP插入ws_base记录表会使用
	* @param bizContent 解密后的京东业务数据
	* @return
	*/  
	@Override
	public JdResponseView requestFor2108(String projNo, String channelId, String bizContent) {
		JdResponseView jdResponseView = null;
		String begTime = DateUtils.nowTimeFormat();
		//通过项目号查询机构信息
		ProjBaseData projBaseData = projBaseService.getByProjNo(projNo);
		if(projBaseData != null && !StringUtils.isEmpty(projBaseData.getBrNo())) {
			try {
				//将解密后的数据转换成pojo对象
				JDLoanfileUpload jDRequest = objectMapper.readValue(bizContent,JDLoanfileUpload.class);
				//校验View层对象是否为空属性以及长度
				List<String> errorMessList = uranusValidator.validatorAll(bizContent);
				if(errorMessList.size() == 0 ) {
					String brNo = projBaseData.getBrNo();
					//此处返回正确结果，否则一律抛出异常，由异常拦截进行处理
					jdResponseView = fileUpload(brNo, projNo, jDRequest);
					// 插入日志表
					WsBaseData wsBase = new WsBaseData(DateUtils.nowDateFormat(), begTime, "", CmsBusinessStatus.JD.businessCode(), 
							jdResponseView.getTradeNo(), DateUtils.nowTimeFormat(), jdResponseView.getCode(), jdResponseView.getMsg(), CmsBusinessStatus.PROCESS.businessCode(), 
							channelId, (String) "", jdResponseView.getBizContent());
					wsBaseService.insert(wsBase);
					return jdResponseView;
				}else {
					throw PlatformExceptionFactory.paramInValidateException(JdResponseStatus.DATA_ERROR).build(errorMessList);
				}
			} catch (IOException e) {
				throw PlatformExceptionFactory.jsonParseException(JdResponseStatus.DATA_ERROR).build(e);
			}
		}else {
			throw PlatformExceptionFactory.exception(JdResponseStatus.PROJ_UNEXISTS).build(JdResponseStatus.PROJ_UNEXISTS.businessMessage());
		}
		
	}
	
	/**
	  * 校验京东业务数据中申请号是否已存在
	 * @param jDLoanApply 京东业务数据
	 * @return checkView 校验结果
	 */
	public JdResponseView CheckNo(JDLoanfileUpload jDRequest) {
		JdResponseView checkView = null;
		//校验申请号是否存在
		String applicationNo = jDRequest.getApplicationNo();
		JdLoanApplyInfoData checkApplicationNo = jdLoanApplyInfoDataMapper.getByApplicationNo(applicationNo);
		if(checkApplicationNo == null){
			checkView = new JdResponseView(JdResponseStatus.NO_APPLICATION.businessCode(), JdResponseStatus.NO_APPLICATION.businessMessage(),
					createKeyService.getJdTradeNo(), "{}");
			return checkView;
		}
		return null;
	}
	
	/**
	 *	HTTP文件上传接口【请求小微、转换小微响应为京东响应】
	 * @param brNo 合作机构编号
	 * @param projNo 信托项目编号
	 * @param jDRequest 京东pojo对象
	 */
	public JdResponseView fileUpload(String brNo, String projNo, JDLoanfileUpload jDRequest) {
		JdResponseView jdResponseView = null;
		//处理校验结果
		JdResponseView checkView = CheckNo(jDRequest);
		if(checkView != null) {
			return checkView;
		}else {
			String tradeNo = createKeyService.getJdTradeNo();
			//请求小微2108签约申请接口，返回京东响应数据
			jdResponseView = lnfileUpoadTrans2108Service.request2108(tradeNo, brNo, projNo, jDRequest);
		}
		return jdResponseView;
	}

	/**  
	* @Description 
	* 		京东获取文件接口
	*/  
	@Override
	public JdResponseView requestFor2110(String projNo, String channelId, String bizContent) {
		JdResponseView jdResponseView = null;
		String begTime = DateUtils.nowTimeFormat();
		//通过项目号查询机构信息
		ProjBaseData projBaseData = projBaseService.getByProjNo(projNo);
		if(projBaseData != null && !StringUtils.isEmpty(projBaseData.getBrNo())) {
			try {
				//将解密后的数据转换成pojo对象
				JDRequest2110 request2110 = objectMapper.readValue(bizContent,JDRequest2110.class);
				//校验View层对象是否为空属性以及长度
				List<String> errorMessList = uranusValidator.validatorAll(bizContent);
				if(errorMessList.size() == 0 ) {
					String brNo = projBaseData.getBrNo();
					//此处返回正确结果，否则一律抛出异常，由异常拦截进行处理
					jdResponseView = findingFiles(brNo, request2110);
					// 插入日志表
					WsBaseData wsBase = new WsBaseData(DateUtils.nowDateFormat(), begTime, "", CmsBusinessStatus.JD.businessCode(), 
							jdResponseView.getTradeNo(), DateUtils.nowTimeFormat(), jdResponseView.getCode(), jdResponseView.getMsg(), CmsBusinessStatus.PROCESS.businessCode(), 
							channelId, (String) "", jdResponseView.getBizContent());
					wsBaseService.insert(wsBase);
					return jdResponseView;
				}else {
					throw PlatformExceptionFactory.paramInValidateException(JdResponseStatus.DATA_ERROR).build(errorMessList);
				}
			} catch (IOException e) {
				throw PlatformExceptionFactory.jsonParseException(JdResponseStatus.DATA_ERROR).build(e);
			}
		}else {
			throw PlatformExceptionFactory.exception(JdResponseStatus.PROJ_UNEXISTS).build(JdResponseStatus.PROJ_UNEXISTS.businessMessage());
		}
	}

	/**  
	* @Description
	* @param
	* @return
	* @throws  
	*/  
	private JdResponseView findingFiles(String brNo, JDRequest2110 request2110) {
		JdResponseView jdResponseView = null;
		String tradeNo = createKeyService.getJdTradeNo();
		String applicationNo = request2110.getApplicationNo();
		JdLoanApplyInfoData checkApplicationNo = jdLoanApplyInfoDataMapper.getByApplicationNo(applicationNo);
		if(checkApplicationNo == null) {
			jdResponseView = new JdResponseView(JdResponseStatus.NO_LOAN_NO.businessCode(), 
					JdResponseStatus.NO_LOAN_NO.businessMessage(), tradeNo, "{}");
		} else {
			//请求小微2108签约申请接口，返回京东响应数据
			jdResponseView = lnfileUpoadTrans2108Service.request2110(tradeNo, brNo, applicationNo, request2110.getFileCode());
		}
		return jdResponseView;
	}
}
