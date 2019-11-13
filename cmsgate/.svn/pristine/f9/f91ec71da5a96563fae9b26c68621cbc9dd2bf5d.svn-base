package com.uranus.platform.business.jd.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uranus.platform.business.jd.entity.vo.JdRequestView;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;
import com.uranus.platform.business.jd.service.JdLoanfileUploadService;
import com.uranus.platform.business.jd.util.JdBaseUtil;

/**
 * @Describe HTTP文件上传接口
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月20日 上午11:02:41
 * 
 */
@RestController
@RequestMapping("/loan")
public class JdLoanfileUploadController {

	@Autowired
	private JdLoanfileUploadService jdLoanfileUploadService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private JdBaseUtil jdBaseUtil;
	
	/**  
	* @Description: HTTP文件上传接口  
	* @param request 请求报文
	* @return 接口返回
	* @throws  
	*/  
	@PostMapping("/fileUpload")
	public JdResponseView loanApply(@RequestBody@NotNull JdRequestView requestView) {
		/**
		 * 校验公共请求头
		 */
		JdResponseView jdResponseView  = jdBaseUtil.validateHttpHeader(request);
		if(jdResponseView == null) {
			//提取京东发送的业务数据
			jdResponseView = jdLoanfileUploadService.requestFor2108(requestView.getChannelProdNo(), requestView.getChannelId(), requestView.getBizContent());
		}
		/**
		 * 设置公共响应报文头
		 */
		jdBaseUtil.setResponseHeader(response);
		return jdResponseView;
	}
	/**  
	* @Description: HTTP文件上传接口  
	* @param request 请求报文
	* @return 接口返回
	* @throws  
	*/  
	@PostMapping("/queryFundingFiles")
	public JdResponseView queryFundingFiles(@RequestBody@NotNull JdRequestView requestView) {
		/**
		 * 校验公共请求头
		 */
		JdResponseView jdResponseView  = jdBaseUtil.validateHttpHeader(request);
		if(jdResponseView == null) {
			//提取京东发送的业务数据
			jdResponseView = jdLoanfileUploadService.requestFor2110(requestView.getChannelProdNo(), requestView.getChannelId(), requestView.getBizContent());
		}
		/**
		 * 设置公共响应报文头
		 */
		jdBaseUtil.setResponseHeader(response);
		return jdResponseView;
	}
}
