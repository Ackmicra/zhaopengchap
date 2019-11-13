package com.uranus.platform.utils.exception;

import com.uranus.tools.psm.status.BusinessCodeDefine;

/**
 * Json转换异常
 * 项目中Json转换过程中出现错误，则建议抛出此异常
 */
public class JsonParseException extends BusinessServiceException{
    public JsonParseException(BusinessCodeDefine businessCode, String message) {
        super(businessCode, message);
    }

    public JsonParseException(BusinessCodeDefine businessCode, String message, Throwable cause) {
        super(businessCode, message, cause);
    }
}
