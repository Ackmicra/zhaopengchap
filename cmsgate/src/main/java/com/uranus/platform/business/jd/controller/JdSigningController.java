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
import com.uranus.platform.business.jd.service.JdSigningService;
import com.uranus.platform.business.jd.util.JdBaseUtil;
import com.uranus.platform.utils.jd.security.SignEnvelopServiceKey;
import com.uranus.platform.utils.status.CmsBusinessStatus;

/**
 * @Description: 签约申请接口
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年8月5日下午3:10:24
 */

@RestController
@RequestMapping("/loan/payment/agreement")
public class JdSigningController {
	
	@Autowired
	private JdSigningService jdSigningService;
	@Autowired
    private SignEnvelopServiceKey signEnvelopServiceKey;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private JdBaseUtil jdBaseUtil;
	
	/**  
	* @Description: 签约申请接口  
	* @param request 请求报文
	* @return 接口返回
	* @throws  
	*/  
	@PostMapping("/request")
	public JdResponseView signRequest(@RequestBody@NotNull JdRequestView requestView) {
		/**
		 * 校验公共请求头
		 */
		JdResponseView jdResponseView  = jdBaseUtil.validateHttpHeader(request);
		if(jdResponseView == null) {
			String decryptDate = signEnvelopServiceKey.verifyEnvelop(requestView.getBizContent());
			jdResponseView = jdSigningService.sign(requestView.getChannelProdNo(), requestView.getChannelId(), decryptDate, CmsBusinessStatus.SIGN_REQUEST.businessCode());
			//如果返回不等于空则将业务数据加密返回
			if(jdResponseView != null) {
				String encrypt = jdResponseView.getBizContent();
				if(encrypt != null && !"".equals(encrypt)) {
					jdResponseView.setBizContent(signEnvelopServiceKey.signEnvelop(encrypt));
				}
			}
		}
		/**
		 * 设置公共响应报文头
		 */
		jdBaseUtil.setResponseHeader(response);
		return jdResponseView;
	}
	/**  
	* @Description: 签约绑定接口  
	* @param request 请求报文
	* @return 接口返回
	* @throws  
	*/  
	@PostMapping("/confirm")
	public JdResponseView confirmRequest(@RequestBody@NotNull JdRequestView requestView) {
		/**
		 * 校验公共请求头
		 */
		JdResponseView jdResponseView  = jdBaseUtil.validateHttpHeader(request);
		if(jdResponseView == null) {
			String decryptDate = signEnvelopServiceKey.verifyEnvelop(requestView.getBizContent());
			jdResponseView = jdSigningService.sign(requestView.getChannelProdNo(), requestView.getChannelId(), decryptDate, CmsBusinessStatus.SIGN_CONFIRM.businessCode());
			//如果返回不等于空则将业务数据加密返回
			if(jdResponseView != null) {
				String encrypt = jdResponseView.getBizContent();
				if(encrypt != null && !"".equals(encrypt)) {
					jdResponseView.setBizContent(signEnvelopServiceKey.signEnvelop(encrypt));
				}
			}
		}
		/**
		 * 设置公共响应报文头
		 */
		jdBaseUtil.setResponseHeader(response);
		return jdResponseView;
	}
		
	/**  
	* @Description: 签约查询接口  
	* @param request 请求报文
	* @return 接口返回
	* @throws  
	*/  
	@PostMapping("/query")
	public JdResponseView queryRequest(@RequestBody@NotNull JdRequestView requestView) {
		/**
		 * 校验公共请求头
		 */
		JdResponseView jdResponseView  = jdBaseUtil.validateHttpHeader(request);
		if(jdResponseView == null) {
			String decryptDate = signEnvelopServiceKey.verifyEnvelop(requestView.getBizContent());
			jdResponseView = jdSigningService.sign(requestView.getChannelProdNo(), requestView.getChannelId(), decryptDate, CmsBusinessStatus.SIGN_QUERY.businessCode());
			//如果返回不等于空则将业务数据加密返回
			if(jdResponseView != null) {
				String encrypt = jdResponseView.getBizContent();
				if(encrypt != null && !"".equals(encrypt)) {
					jdResponseView.setBizContent(signEnvelopServiceKey.signEnvelop(encrypt));
				}
			}
		}
		/**
		 * 设置公共响应报文头
		 */
		jdBaseUtil.setResponseHeader(response);
		return jdResponseView;
	}
	
}
