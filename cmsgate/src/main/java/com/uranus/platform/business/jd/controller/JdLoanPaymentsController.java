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
import com.uranus.platform.business.jd.service.JdLoanPaymentsService;
import com.uranus.platform.business.jd.util.JdBaseUtil;
import com.uranus.platform.utils.jd.security.SignEnvelopServiceKey;

/**
 * @Description: 放款指令接口  
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月14日 上午9:57:01
 * 
 */
@RestController
@RequestMapping("/loan")
public class JdLoanPaymentsController {
	@Autowired
    private SignEnvelopServiceKey signEnvelopServiceKey;
	@Autowired
    private JdLoanPaymentsService jdLoanPaymentsService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private JdBaseUtil jdBaseUtil;
	
	/**  
	* @Description: 放款指令接口  
	* @param requestView 请求报文
	* @return 接口返回
	* @throws  
	*/  
	@PostMapping("/loanPayments")
	public JdResponseView confirmRequest(@RequestBody@NotNull JdRequestView requestView) {
		/**
		 * 校验公共请求头
		 */
		JdResponseView jdResponseView  = jdBaseUtil.validateHttpHeader(request);
		if(jdResponseView == null) {
			//解密业务数据
			String decryptDate = signEnvelopServiceKey.verifyEnvelop(requestView.getBizContent());
			jdResponseView = jdLoanPaymentsService.requestfor2101(requestView.getChannelProdNo(), requestView.getChannelId(), decryptDate);
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
	* @Description: 放款结果查询接口
	* @param requestView 请求报文
	* @return 接口返回
	* @throws  
	*/  
	@PostMapping("/queryLoanPaymentsResult")
	public JdResponseView queryLoanPaymentsResult(@RequestBody@NotNull JdRequestView requestView) {
		/**
		 * 校验公共请求头
		 */
		JdResponseView jdResponseView  = jdBaseUtil.validateHttpHeader(request);
		if(jdResponseView == null) {
			//解密业务数据
			String decryptDate = signEnvelopServiceKey.verifyEnvelop(requestView.getBizContent());
			jdResponseView = jdLoanPaymentsService.requestfor2102(requestView.getChannelProdNo(), requestView.getChannelId(), decryptDate);
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
