package com.simplon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.simplon")
@EnableFeignClients(
        basePackages = "com.simplon"
)
public class FriendApp {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        SpringApplication.run(FriendApp.class, args);
    }
}
