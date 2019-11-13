package com.uranus.platform.business.ws.service;

import com.uranus.platform.business.ws.process.Request;
import com.uranus.platform.business.ws.process.Response;

/**
 * @Description: 调用小微业务办理接口
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年8月3日下午6:36:05
 */
public interface ProcessService {
	
	/**请求小微业务办理接口
	 * @param request 请求报文
	 * @return 小微业务办理接口返回结果
	 */
	public Response wsProcess(String busSide, Request request);
}
