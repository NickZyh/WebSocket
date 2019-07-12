package cn.zifangsky.samplewebsocket.websocket;

import cn.zifangsky.samplewebsocket.service.EchoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * 通过继承 {@link org.springframework.web.socket.handler.AbstractWebSocketHandler} 的示例
 * 这个类的架构位于客户端和服务器之间,这个类的作用就是对服务器和客户端的交互请求的拦截,通过拦截这些请求,即可达到实现自定义逻辑
 * tomcat <-> WebSocketHandler <-> client
 */
public class EchoWebSocketHandler extends TextWebSocketHandler{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "echoServiceImpl")
    private EchoService echoService;

    /**客户端与服务器的WebSocket连接建立成功时,服务器回调客户端的api*/
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("Opened new session in instance " + this);
    }

    /**接收客户端消息*/
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //组装返回的Echo信息
        String echoMessage = this.echoService.echo(message.getPayload());
        logger.debug(MessageFormat.format("Echo message \"{0}\"", echoMessage));

        session.sendMessage(new TextMessage(echoMessage));
    }

    /**处理底层WebSocket消息传输中的错误*/
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
        logger.debug("Info: WebSocket connection closed.");
    }

    /**在任何一方关闭WebSocket连接后或在发生传输错误后调用。尽管会话在技术上可能仍然是打开的（不会立刻关闭），取决于底层
            实现，在者种情况下发送消息是不鼓励的，并且很可能不会成功*/
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    /**WebSocketHandler是否处理部分消息。如果这个标志被设置为true，并且底层WebSocket服务器支持部分消息，
        那么一个大的WebSocket消息或者一个未知大小的消息可能会被拆分，并且可能通过多次调用*/
    @Override
    public boolean supportsPartialMessages(){
        return false;
    }
}
