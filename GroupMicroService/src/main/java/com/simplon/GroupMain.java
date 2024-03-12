package com.simplon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.simplon"})
@EnableFeignClients(
        basePackages = "com.simplon"
)
public class GroupMain {
    public static void main(String[] args) {
        SpringApplication.run(GroupMain.class, args);
    }
}