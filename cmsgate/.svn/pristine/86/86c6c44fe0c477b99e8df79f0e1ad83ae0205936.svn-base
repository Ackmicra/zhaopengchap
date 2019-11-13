package com.uranus.platform.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.business.ModelTest;

class RestTemplateConfigTest extends ModelTest{
	
	@Autowired
	private RestTemplate restTemplate;//这样就可以直接调用api使用了或者直接new对象也一样
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void test() {
		String paras = "data";
        String url="http://127.0.0.1";

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity;
		try {
			formEntity = new HttpEntity<String>(objectMapper.writeValueAsString(paras), headers);
//			String result = restTemplate.postForObject(url, formEntity, String.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
