package com.uranus.platform.authorize.login;

import com.uranus.platform.business.admin.entity.view.SystemAdminView;
import com.uranus.tools.validator.UranusValidator;
import org.hibernate.validator.HibernateValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author 李亚斌
 * @date 2019/07/24 11:09
 * @since v1.1
 */
@SpringBootTest
class AdminDetailsServiceTest {
    @Autowired
    private UranusValidator uranusValidator;

    @Autowired
    private Validator validator;

    @Test
    public void validateBean(){
        SystemAdminView adminView = new SystemAdminView();
        adminView.setUsername("1234");
        String result = uranusValidator.validate(adminView);
        System.out.println(result);
        Assertions.assertNotNull(result);
    }

    public void validateBean2(){
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure()
                .failFast( true )
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
    }
}