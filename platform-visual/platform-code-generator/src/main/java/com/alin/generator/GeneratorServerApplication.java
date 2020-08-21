package com.alin.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.com.*")
public class GeneratorServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GeneratorServerApplication.class,args);
    }
}
