package com.uranus.platform.config;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uranus.platform.business.ws.login.InfWsLogin;
import com.uranus.platform.business.ws.process.InfWsProcess;
import com.uranus.platform.business.ws.search.InfWsSearch;

/**
 * @Description: webservice接口对象初始化类
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年8月3日下午6:38:30
 * 注解@Configuration 作用：类启动时加载该类
 */
@Configuration//类启动时加载该类
public class CxfClientConfig {

	@Value("${platform.webservice.loginUrl}")
	private String loginUrl;
	@Value("${platform.webservice.processUrl}")
	private String processUrl;
	@Value("${platform.webservice.searchUrl}")
	private String searchUrl;
	/**
	 * 以接口代理方式进行调用 InfWsLogin接口
	 */
	@Bean("infWsLogin")
	public InfWsLogin createInfWsLoginProxy() {
	    JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
	    jaxWsProxyFactoryBean.setAddress(loginUrl);
	    return jaxWsProxyFactoryBean.create(InfWsLogin.class);
	}
	/**
	 * 以接口代理方式进行调用 InfWsLogin接口
	 */
	@Bean("infWsProcess")
	public InfWsProcess createInfWsProcessProxy() {
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
		jaxWsProxyFactoryBean.setAddress(processUrl);
		return jaxWsProxyFactoryBean.create(InfWsProcess.class);
	}
	/**
	 * 以接口代理方式进行调用 InfWsLogin接口
	 */
	@Bean("infWsSearch")
	public InfWsSearch createInfWsSearchProxy() {
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
		jaxWsProxyFactoryBean.setAddress(searchUrl);
		return jaxWsProxyFactoryBean.create(InfWsSearch.class);
	}
}
