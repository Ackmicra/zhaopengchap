package com.uranus.platform.business.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.business.admin.entity.view.SystemAdminView;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

/**
 * @author 李亚斌
 * @date 2019/07/30 9:49
 * @since v1.1
 */
@SpringBootTest
@Slf4j
@ExtendWith(SpringExtension.class)
class SystemAdminControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  //初始化MockMvc对象
    }

    @Test
    void addNewSystemAdminTest() throws Exception {
        SystemAdminView adminView = new SystemAdminView();
        adminView.setUsername(UUID.randomUUID().toString());
        adminView.setIntegral(20);
        adminView.setRegtime(System.currentTimeMillis());
        adminView.setPassword("123456");
        adminView.setStatus("1");
        String adminViewJson = objectMapper.writeValueAsString(adminView);

        log.info("测试插入管理员用户");
        String responseString = mockMvc.perform(
                MockMvcRequestBuilders.post("/systemAdmin").contentType(MediaType.APPLICATION_JSON)
                .content(adminViewJson)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json = " + responseString);
    }

    @Test
    void findSystemAdminTest() throws Exception {
        String adminName = "testDataAdmin321";

        log.info("测试查询管理员用户");
        String responseString = mockMvc.perform(
                MockMvcRequestBuilders.get("/systemAdmin/"+adminName).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json = " + responseString);
    }
}