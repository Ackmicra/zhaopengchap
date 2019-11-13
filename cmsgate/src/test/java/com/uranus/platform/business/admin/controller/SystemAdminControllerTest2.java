package com.uranus.platform.business.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.business.admin.entity.view.SystemAdminView;
import com.uranus.platform.test.base.BaseMockMvcTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

/**
 * @author 李亚斌
 * @date 2019/07/30 9:49
 * @since v1.1
 */
@SpringBootTest
@Slf4j
@ExtendWith(SpringExtension.class)
class SystemAdminControllerTest2 extends BaseMockMvcTest {
    @Autowired
    private ObjectMapper objectMapper;

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
        new BaseMockMvcRequest(RequestType.POST,"/systemAdmin")
                .content(adminViewJson)
                .assertOk();
    }

    @Test
    void findSystemAdminTest() throws Exception {
        String adminName = "testDataAdmin321";
        log.info("测试查询管理员用户");
        String path = "/systemAdmin/"+adminName;

       new BaseMockMvcRequest(RequestType.GET,path).assertOk();
    }
}