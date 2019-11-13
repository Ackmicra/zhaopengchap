package com.uranus.platform;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author 李亚斌
 * @date 2019/07/23 18:06
 * @since v1.1
 */
@SpringBootApplication
@EnableSwagger2Doc
@EnableCaching
@MapperScan("com.uranus.platform.business.*.dao")
public class SingleDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SingleDemoApplication.class,args);
    }
}
