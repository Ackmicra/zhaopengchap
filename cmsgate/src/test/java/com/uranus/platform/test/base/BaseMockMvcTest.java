package com.uranus.platform.test.base;

import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author 李亚斌
 * @date 2019/07/30 13:51
 * @since v1.1
 */

public class BaseMockMvcTest {
    protected MockMvc mockMvc;

    @Lazy
    @Autowired
    private WebApplicationContext wac;
    @BeforeEach
    void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  //初始化MockMvc对象
    }

    @Getter
    protected
    class BaseMockMvcRequest {

        private final String path;
        private final RequestType requestType;
        private boolean isPrintResponse;
        private MockHttpServletRequestBuilder builder;

        public BaseMockMvcRequest(RequestType requestType, String path) {
            this.path = path;
            this.requestType = requestType;
            this.isPrintResponse = true;
            switch (this.requestType) {
                case GET:
                    builder = MockMvcRequestBuilders.get(this.path).contentType(MediaType.APPLICATION_JSON);
                    break;
                case PUT:
                    builder = MockMvcRequestBuilders.put(this.path).contentType(MediaType.APPLICATION_JSON);
                    break;
                case POST:
                    builder = MockMvcRequestBuilders.post(this.path).contentType(MediaType.APPLICATION_JSON);
                    break;
                case PATCH:
                    builder = MockMvcRequestBuilders.patch(this.path).contentType(MediaType.APPLICATION_JSON);
                    break;
                case DELETE:
                    builder = MockMvcRequestBuilders.delete(this.path).contentType(MediaType.APPLICATION_JSON);
                    break;
                default:
                    throw new NoSuchElementException("不存在此种请求类型:" + this.requestType);
            }
        }

        public BaseMockMvcRequest content(String content){
            builder = builder.content(content);
            return this;
        }

        public BaseMockMvcRequest header(String headName, String headValue){
            builder = builder.header(headName,headValue);
            return this;
        }

        public BaseMockMvcRequest paramMap(Map<String,String> paramMap){
            Iterator<String> keyIterator = paramMap.keySet().iterator();
            String key = null;
            while (keyIterator.hasNext()){
                key = keyIterator.next();
                builder = builder.param(key,paramMap.get(key));
            }
            return this;
        }

        public String assertOk() throws Exception {
            return assertOther(HttpStatus.OK);
        }

        public String assertOther(HttpStatus status) throws Exception {
            ResultMatcher resultMatcher = MockMvcResultMatchers.status().is(status.value());
            ResultActions resultActions = mockMvc.perform(builder);

            resultActions = resultActions.andExpect(resultMatcher);
            if(isPrintResponse){
                resultActions = resultActions.andDo(MockMvcResultHandlers.print());
            }
            return resultActions.andReturn().getResponse().getContentAsString();
        }

        public BaseMockMvcRequest printResponse(boolean isPrintResponse){
            this.isPrintResponse = isPrintResponse;
            return this;
        }
    }

    protected enum RequestType {
        POST,
        GET,
        PUT,
        DELETE,
        PATCH;
    }

}
