package cn.com.oumeng.biz.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("cn.com.oumeng.*")
@ComponentScan("cn.com.oumeng.*")
@RefreshScope
public class CrmServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrmServerApplication.class,args);
    }
}
