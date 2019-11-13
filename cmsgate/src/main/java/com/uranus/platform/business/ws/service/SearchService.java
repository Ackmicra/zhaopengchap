package com.uranus.platform.business.ws.service;

import com.uranus.platform.business.ws.search.Request;
import com.uranus.platform.business.ws.search.Response;

/**
 * @Description: 调用小微业务查询接口
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年8月3日下午6:36:21
 */
public interface SearchService {
	
	/**	请求小微业务查询接口
	 * @param request 请求报文
	 * @return 小微接口查询结果
	 */
	public Response search(String busSide, Request request);
}
