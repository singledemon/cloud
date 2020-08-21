package cn.com.oumeng.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 监控中心
 * @author maql
 */
@EnableAdminServer
@SpringCloudApplication
public class MonitorServerApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(MonitorServerApplication.class, args);

    }
}
