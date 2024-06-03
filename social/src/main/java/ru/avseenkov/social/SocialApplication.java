package ru.avseenkov.social;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SocialApplication  {

    public static void main(String[] args) {
        SpringApplication.run(SocialApplication.class, args);
    }


}
