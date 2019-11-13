package com.uranus.platform.business.admin.entity.bo;

import com.uranus.platform.business.admin.entity.convert.AccountStatus;
import com.uranus.platform.business.admin.entity.model.SystemAdminData;
import com.uranus.tools.beans.BeanCopyUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @author 李亚斌
 * @date 2019/07/25 17:30
 * @since v1.1
 */
class DomainConvertTest {
    private SystemAdminData systemAdminData;
    private SystemAdminDomain systemAdminDomain;

    @BeforeEach
    public void initData(){
        systemAdminDomain = new SystemAdminDomain();
        systemAdminDomain.setStatus(AccountStatus.LOCKED);
        systemAdminDomain.setUsername("testAdmin123");
        systemAdminDomain.setPassword("1234567");
        systemAdminDomain.setIntegral(20);
        systemAdminDomain.setRegtime(LocalDateTime.now());

        systemAdminData = new SystemAdminData();
        systemAdminData.setId(10);
        systemAdminData.setIntegral(100);
        systemAdminData.setPassword("87654321");
        systemAdminData.setUsername("testDataAdmin321");
        systemAdminData.setRegtime(System.currentTimeMillis());
        systemAdminData.setStatus("1");
    }

    @Test
    public void testDomainToData(){
        SystemAdminData convertedData = BeanCopyUtils.INSTANCE.convertTo(systemAdminDomain,SystemAdminData.class);
        System.out.println(convertedData);
        Assertions.assertEquals(convertedData.getUsername(),systemAdminDomain.getUsername());
    }

    @Test
    void testDataToDomain(){
        SystemAdminDomain convertDomain = BeanCopyUtils.INSTANCE.convertTo(systemAdminData,SystemAdminDomain.class);
        System.out.println(convertDomain);
        Assertions.assertEquals(convertDomain.getUsername(),systemAdminData.getUsername());
    }
}