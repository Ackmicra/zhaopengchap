package com.uranus.platform.utils.exception;

import com.uranus.tools.psm.status.BusinessCodeDefine;
import com.uranus.tools.utils.DateUtils;
import lombok.Getter;

@Getter
public class BusinessServiceException extends RuntimeException{
	private BusinessCodeDefine businessCode;
	
	public BusinessServiceException(BusinessCodeDefine businessCode, String message) {
		super(message);
		this.businessCode = businessCode;
	}

	public BusinessServiceException(BusinessCodeDefine businessCode,String message, Throwable cause) {
		super(message, cause);
		this.businessCode = businessCode;
	}

	public String printLog(boolean printStackTrace) {
		String say = String.format("%s 发生错误异常，错误码为：%s,错误信息为：%s",
				DateUtils.nowTimeFormat()
		,businessCode.businessCode(),this.getMessage());
		if(printStackTrace){
			this.printStackTrace();
		}
		return say;
	}
}
