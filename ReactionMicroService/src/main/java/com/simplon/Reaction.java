package com.simplon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableWebMvc
@EnableDiscoveryClient
@EnableFeignClients(
        basePackages = "com.simplon"
)
public class Reaction {
    public static void main(String[] args) {
        SpringApplication.run(Reaction.class, args);
    }

}
