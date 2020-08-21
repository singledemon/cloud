package cn.com.oumeng.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringBootApplication
public class JobServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobServerApplication.class,args);
    }
}
