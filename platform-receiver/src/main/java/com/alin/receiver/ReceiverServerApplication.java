package com.alin.receiver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.com.oumeng.*")
public class ReceiverServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReceiverServerApplication.class,args);
    }
}
