package com.uranus.platform.business.jd.util;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.uranus.platform.business.jd.entity.status.JdResponseStatus;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;
import com.uranus.platform.business.pub.service.CreateKeyService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class JdBaseUtil {
	@Autowired
	private CreateKeyService createKeyService;
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
	
	/**
	 * 请求http header 校验
	 *
	 * @param request
	 *            HttpServletRequest
	 */
	public JdResponseView validateHttpHeader(HttpServletRequest request) {
		JdResponseView jdResponseView = null;
		// 安全秘钥与约定的不一致
		if (!secretKey.equals(request.getHeader("secret-key"))) {
			return jdResponseView = new JdResponseView(JdResponseStatus.AUTH_FAIL.businessCode(), JdResponseStatus.AUTH_FAIL.businessMessage(), createKeyService.getJdTradeNo(), "{}");
		}

		// 加密算法与约定的不一致
		if (!encryptAlg.equals(request.getHeader("encrypt-alg"))) {
			return jdResponseView = new JdResponseView(JdResponseStatus.DENCRYPY_ERROR.businessCode(), JdResponseStatus.DENCRYPY_ERROR.businessMessage(), createKeyService.getJdTradeNo(), "{}");
		}

		// 签名算法与约定的不一致
		if (!signAlg.equals(request.getHeader("sign-alg"))) {
			return jdResponseView = new JdResponseView(JdResponseStatus.DENCRYPY_ERROR.businessCode(), JdResponseStatus.DENCRYPY_ERROR.businessMessage(), createKeyService.getJdTradeNo(), "{}");
		}

		// 时间戳格式与约定的不一致
		String timestampInReq = request.getHeader("timestamp");
		SimpleDateFormat dateFormat = new SimpleDateFormat(timestamp);
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(timestampInReq);
		} catch (Exception e) {
			log.error("", e);
			return jdResponseView = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(), JdResponseStatus.DATA_ERROR.businessMessage(), createKeyService.getJdTradeNo(), "{}");
		}
		// 版本与约定的不一致
		if (!version.equals(request.getHeader("version"))) {
			return jdResponseView = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(), JdResponseStatus.DATA_ERROR.businessMessage(), createKeyService.getJdTradeNo(), "{}");
		}
		return jdResponseView;
	}
	
	/**  
	* 设置公共响应头
	* 
	* HttpServletResponse
	* 			response
	* 
	*/  
	public HttpServletResponse setResponseHeader(HttpServletResponse response) {
		response.setHeader("encrypt-alg", encryptAlg);
		response.setHeader("sign-alg", signAlg);
		response.setHeader("timestamp", new SimpleDateFormat(timestamp).format(System.currentTimeMillis()));
		return response;
	}
}
