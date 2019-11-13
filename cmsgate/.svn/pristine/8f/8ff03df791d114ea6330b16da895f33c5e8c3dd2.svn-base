package com.uranus.platform.utils.exception;

import com.uranus.tools.psm.status.BusinessCodeDefine;

import java.util.List;

/**
 * 参数校验失败异常
 * 当参数校验失败时，建议抛出此异常。
 */
public class ParamInValidateException extends BusinessServiceException{

	public ParamInValidateException(BusinessCodeDefine businessCode, String message) {
		super(businessCode, message);
	}

	public ParamInValidateException(BusinessCodeDefine businessCode, String message, Throwable cause) {
		super(businessCode, message, cause);
	}

	public ParamInValidateException(BusinessCodeDefine businessCode,List<String> errorMessList) {
		this(businessCode,String.join(",", errorMessList));
	}
}
