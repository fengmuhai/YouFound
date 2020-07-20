package com.feng.youfang.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author fengmuhai
 * @date 2020/7/20
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class YoufoundWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(YoufoundWebApplication.class, args);
    }
}
