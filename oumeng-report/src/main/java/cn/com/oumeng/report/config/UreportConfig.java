package cn.com.oumeng.report.config;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import com.bstek.ureport.console.UReportServlet;

/**
 * Ureport配置类
 * @author T460P
 *
 */
@Configuration
@ImportResource("classpath:ureportcontext.xml")
public class UreportConfig {
	
	@Bean(name = "uReportServlet")
	public ServletRegistrationBean buildUfloServlet() {
		return new ServletRegistrationBean(new UReportServlet(), "/ureport/*");
	}
}
