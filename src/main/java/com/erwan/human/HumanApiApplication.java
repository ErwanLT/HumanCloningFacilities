package com.erwan.human;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.erwan")
public class HumanApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HumanApiApplication.class, args);
    }

}
