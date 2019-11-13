package com.uranus.platform.business.jd.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uranus.platform.business.jd.entity.vo.JdRequestView;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;
import com.uranus.platform.business.jd.service.JdRepayPlanService;
import com.uranus.platform.business.jd.util.JdBaseUtil;
import com.uranus.platform.utils.jd.security.SignEnvelopServiceKey;

/**
 * 
* @ClassName:：JdRepayPlanControllor 
* @Description： 还款计划接口
* @author ：chenwendong
* @date ：2019年8月5日 下午4:43:17 
*
 */	
@RestController
@RequestMapping("/loan")
public class JdRepayPlanControllor {
	
	@Autowired
	JdRepayPlanService jdRepayPlanService;
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
	* @Title：getTrialRepayments 
	* @Description：还款计划试算
	* @param ：@param request
	* @param ：@return 
	* @return ：ResponseEntity<String> 
	* @throws
	 */
	@PostMapping("/trialRepayments")
	public JdResponseView trialRepayments(@RequestBody @NotNull JdRequestView requestView) {
		return null;
	}
	/**
	 * 
	* @Title：getRepayPlan 
	* @Description：还款计划同步
	* @param ：@return 
	* @return ：ResponseEntity<String> 
	* @throws
	 */
	@PostMapping("/syncRepayPlan")
	public JdResponseView syncRepayPlan(@RequestBody @NotNull JdRequestView requestView){
		/**
		 * 校验公共请求头
		 */
		JdResponseView jdResponseView  = jdBaseUtil.validateHttpHeader(request);
		if(jdResponseView == null) {
			String decryptDate = signEnvelopServiceKey.verifyEnvelop(requestView.getBizContent());
			jdResponseView = jdRepayPlanService.syncRepayPlan(requestView.getChannelProdNo(), requestView.getChannelId(), decryptDate);
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
	 * 
	* @Title：queryRepayPlan 
	* @Description：还款计划查询
	* @param ：@param request
	* @param ：@return 
	* @return ：ResponseEntity<String> 
	* @throws
	 */
	@GetMapping("/queryRepayPlan")
	public JdResponseView queryRepayPlan(@RequestBody @NotNull JdRequestView requestView){
		/**
		 * 校验公共请求头
		 */
		JdResponseView jdResponseView  = jdBaseUtil.validateHttpHeader(request);
		if(jdResponseView == null) {
			String decryptDate = signEnvelopServiceKey.verifyEnvelop(requestView.getBizContent());
			jdResponseView = jdRepayPlanService.queryRepayPlan(requestView.getChannelProdNo(), requestView.getChannelId(), decryptDate);
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
	 * 
	* @Title：changeRepayPlan 
	* @Description：还款计划变更
	* @param ：@param request
	* @param ：@return 
	* @return ：ResponseEntity<String> 
	* @throws
	 */
	@PostMapping("/changeRepayPlan")
	public JdResponseView changeRepayPlan(@RequestBody @NotNull JdRequestView requestView){
		/**
		 * 校验公共请求头
		 */
		JdResponseView jdResponseView  = jdBaseUtil.validateHttpHeader(request);
		if(jdResponseView == null) {
			String decryptDate = signEnvelopServiceKey.verifyEnvelop(requestView.getBizContent());
			jdResponseView = jdRepayPlanService.changeRepayPlan(requestView.getChannelProdNo(), requestView.getChannelId(),decryptDate );
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
	 * 
	* @Title：changeRepayPlanFee 
	* @Description：还款计划费用明细
	* @param ：@param request
	* @param ：@return 
	* @return ：ResponseEntity<String> 
	* @throws
	 */
	@PutMapping("/changeRepayPlanFee")
	public JdResponseView changeRepayPlanFee(@RequestBody @NotNull JdRequestView requestView){
		return null;
	}
	
}
