package com.uranus.platform.business.jd.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uranus.platform.business.jd.entity.vo.JdRequestView;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;
import com.uranus.platform.business.jd.service.JdTransferPlanService;
import com.uranus.platform.business.jd.util.JdBaseUtil;
import com.uranus.platform.utils.jd.security.SignEnvelopServiceKey;

/**
 * 
* @ClassName:：JdTransferPlansControllor 
* @Description： 扣款
* @author ：chenwendong
* @date ：2019年8月5日 下午3:35:59 
*
 */
@RestController
@RequestMapping("/loan")
public class JdTransferPlansControllor {

	@Autowired
	JdTransferPlanService jdTransferPlanService;
	@Autowired
    private SignEnvelopServiceKey signEnvelopServiceKey;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private JdBaseUtil jdBaseUtil;
	
	/**
	 * 
	* @Title：getTransferPlans 
	* @Description：扣款计划上送
	* @param ：@param requestView 
	* @return ：void 
	* @throws
	 */
	@PostMapping(value = "/transferPlans")
	public JdResponseView transferPlans(@RequestBody @NotNull JdRequestView requestView) {
		/**
		 * 校验公共请求头
		 */
		JdResponseView jdResponseView  = jdBaseUtil.validateHttpHeader(request);
		if(jdResponseView == null) {
			String decryptDate = signEnvelopServiceKey.verifyEnvelop(requestView.getBizContent());
			jdResponseView = jdTransferPlanService.syncTransferPlanNew(requestView.getChannelProdNo(),
					requestView.getChannelId(), decryptDate);
			// 如果返回不等于空则将业务数据加密返回
			if (jdResponseView != null) {
				String encrypt = jdResponseView.getBizContent();
				if (encrypt != null && !"".equals(encrypt)) {
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
	 * 
	* @Title：repurchaseApply 
	* @Description：回购申请
	* @param ：@param requestView
	* @param ：@return 
	* @return ：JdResponseView 
	* @throws
	 */
	@GetMapping("/repurchaseApply")
	public JdResponseView repurchaseApply(@RequestBody @NotNull JdRequestView requestView) {
		/**
		 * 校验公共请求头
		 */
		JdResponseView jdResponseView  = jdBaseUtil.validateHttpHeader(request);
		if(jdResponseView == null) {
			String decryptDate = signEnvelopServiceKey.verifyEnvelop(requestView.getBizContent());
			jdResponseView = jdTransferPlanService.repurchaseApply(requestView.getChannelProdNo(),
					requestView.getChannelId(), decryptDate);
			// 如果返回不等于空则将业务数据加密返回
			if (jdResponseView != null) {
				String encrypt = jdResponseView.getBizContent();
				if (encrypt != null && !"".equals(encrypt)) {
					jdResponseView.setBizContent(signEnvelopServiceKey.signEnvelop(encrypt));
				}
			}
		}
		/**
		 * 设置公共响应报文头
		 */
		jdBaseUtil.setResponseHeader(response);
		return jdResponseView;	}
	
	/**
	 * 
	* @Title：getPaymentOrder 
	* @Description：支付订单
	* @param ：@param requestView
	* @param ：@return 
	* @return ：ResponseEntity<String> 
	* @throws
	 */
	@PostMapping("/paymentOrder")
	public JdResponseView paymentOrder(@RequestBody @NotNull JdRequestView requestView) {
		/**
		 * 校验公共请求头
		 */
		JdResponseView jdResponseView  = jdBaseUtil.validateHttpHeader(request);
		if(jdResponseView == null) {
			String decryptDate = signEnvelopServiceKey.verifyEnvelop(requestView.getBizContent());
			jdResponseView = jdTransferPlanService.paymentOrder(requestView.getChannelProdNo(),
					requestView.getChannelId(), decryptDate);
			// 如果返回不等于空则将业务数据加密返回
			if (jdResponseView != null) {
				String encrypt = jdResponseView.getBizContent();
				if (encrypt != null && !"".equals(encrypt)) {
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
	 * 
	* transferPlanResultQuery 
	* @Description：扣款计划处理结果查询
	* @param ：@param requestView
	* @param ：@return 
	* @return ：JdResponseView
	* @throws
	 */
	@PostMapping("/transferPlanResultQuery")
	public JdResponseView transferPlanResultQuery(@RequestBody @NotNull JdRequestView requestView) {
		/**
		 * 校验公共请求头
		 */
		JdResponseView jdResponseView  = jdBaseUtil.validateHttpHeader(request);
		if(jdResponseView == null) {
			String decryptDate = signEnvelopServiceKey.verifyEnvelop(requestView.getBizContent());
			jdResponseView = jdTransferPlanService.queryForTransferPlans(requestView.getChannelProdNo(),
					requestView.getChannelId(), decryptDate);
			// 如果返回不等于空则将业务数据加密返回
			if (jdResponseView != null) {
				String encrypt = jdResponseView.getBizContent();
				if (encrypt != null && !"".equals(encrypt)) {
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
	 * 
	* @Title：getQueryPaymentOrder 
	* @Description：支付订单查询
	* @param ：@param requestView
	* @param ：@return 
	* @return ：ResponseEntity<String> 
	* @throws
	 */
	@PostMapping("/queryPaymentOrder")
	public JdResponseView queryPaymentOrder(@RequestBody @NotNull JdRequestView requestView) {
		/**
		 * 校验公共请求头
		 */
		JdResponseView jdResponseView  = jdBaseUtil.validateHttpHeader(request);
		if(jdResponseView == null) {
			String decryptDate = signEnvelopServiceKey.verifyEnvelop(requestView.getBizContent());
			jdResponseView = jdTransferPlanService.queryPaymentOrder(requestView.getChannelProdNo(),
					requestView.getChannelId(), decryptDate);
			// 如果返回不等于空则将业务数据加密返回
			if (jdResponseView != null) {
				String encrypt = jdResponseView.getBizContent();
				if (encrypt != null && !"".equals(encrypt)) {
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
