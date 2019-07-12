package cn.zifangsky.samplewebsocket.config;

import cn.zifangsky.samplewebsocket.websocket.EchoWebSocketHandler;
import cn.zifangsky.samplewebsocket.websocket.ReverseWebSocketEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket相关配置
 * @author zifangsky
 * @date 2018/9/30
 * @since 1.0.0
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    /**
     * 将信息都注册到WebSocketHandlerRegistry中去
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 原生websocket(Stom协议); String参数能够理解为需要暴露给前端的url,用于第一次建立连接
        registry.addHandler(echoWebSocketHandler(), "/echoMessage");
        // Sockjs协议的websocket
        registry.addHandler(echoWebSocketHandler(), "/echoMessage_SockJS").withSockJS();
    }

    /**
     * 通过继承 {@link org.springframework.web.socket.handler.AbstractWebSocketHandler} 的示例
     * 框架中的一种常用组合方式就是抽象类实现接口方法,但是重写的接口的方法没有实现;这么做的好处就是抽象类的子类不需要重写接口的所有方法
     *      AbstractWebSocketHandler:实现了WebSocketHandler接口,WebSocketHandler接口中包含五个方法,这五个方法对应的WebSocket接口的动作
     *
     * 在配置中定义Bean和在类上使用@Component这两个的作用实际上是一样的,其核心目的都是为了将Bean交给Spring管理,所以这个地方实际上也可以直接在
     * EchoWebSocketHandler上使用@Component标记
     */
    @Bean
    public WebSocketHandler echoWebSocketHandler(){
        // 定义支持websocket协议的服务器收到请求后回调的请求
        return new EchoWebSocketHandler();
    }


    /**
     * 注册@ServerEndpoint
     */
    @Bean
    public ReverseWebSocketEndpoint reverseWebSocketEndpoint() {
        return new ReverseWebSocketEndpoint();
    }

    /**
     * 使用@Bean注入就是有一个好处:由于使用@Component标记类的前提是我们必须要有这个类,也就是说这个类是我们自定义的,那么此时无论使用@Component还是@Bean都无关
     * 紧要,但是如果我们想要使用Spring内置好的,或者说默认实现好的Bean时,那么此时我们就只能使用@Bean的方式注入;
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
