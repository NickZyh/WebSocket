package cn.zifangsky.samplewebsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
/**
 * 使得Springboot能夠使用并解析支持Servlet的注解,如Servlet、Filter、Listener可以直接通过@WebServlet、@WebFilter、@WebListener注解自动注册，无需其他代码
 **/
@ServletComponentScan
@EnableAsync
public class SampleWebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleWebsocketApplication.class, args);
	}
}
