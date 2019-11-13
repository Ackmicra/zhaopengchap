package com.uranus.platform.config;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
 
/**
 * @Description: RestTemplate配置类
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年8月20日下午3:33:50
 */
@Configuration
public class RestTemplateConfig {
    
	//连接超时时间
    @Value("${rest.connection.timeout}")
    private Integer connectionTimeout;
     // 信息读取超时时间
    @Value("${rest.read.timeout}")
    private Integer readTimeout;
 
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
    	 RestTemplate restTemplate= new RestTemplate(factory);
         // 支持中文编码
         restTemplate.getMessageConverters().set(1,
                 new StringHttpMessageConverter(Charset.forName("UTF-8")));
         return restTemplate;
    }
 
    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(readTimeout);//单位为ms
        factory.setConnectTimeout(connectionTimeout);//单位为ms
        return factory;
    }
}