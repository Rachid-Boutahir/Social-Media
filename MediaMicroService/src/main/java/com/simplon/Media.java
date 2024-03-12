package com.simplon;

import com.simplon.servicemedia.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableFeignClients(
        basePackages = "com.simplon"
)
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@EnableDiscoveryClient
public class Media
{
    public static void main( String[] args )
    {
        SpringApplication.run(Media.class, args);
    }
}
