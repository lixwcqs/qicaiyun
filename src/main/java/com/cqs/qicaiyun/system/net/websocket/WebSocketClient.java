package com.cqs.qicaiyun.system.net.websocket;

import lombok.extern.log4j.Log4j2;

import javax.websocket.*;

/**
 * Created by cqs on 2018/4/19.
 */
//@ClientEndpoint
@Log4j2
public class WebSocketClient {

    public static void main(String[] args) {
//        ws://localhost:9090/qicaiyun/ws
//        try {
//            WebSocketContainer container = ContainerProvider.getWebSocketContainer(); // 获取WebSocket连接器，其中具体实现可以参照websocket-api.jar的源码,Class.forName("org.apache.tomcat.websocket.WsWebSocketContainer");
//            String uri = "ws://localhost:9090/qicaiyun/ws";
//            Session session = container.connectToServer(WebSocketClient.class, URI.create(uri)); // 连接会话
//            session.getBasicRemote().sendText("123132132131"); // 发送文本消息
//            session.getBasicRemote().sendText("4564546");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        String s = "";
        for (int i = 0; i < 1; i++) {
            s +="建立连接";
        }
        log.info(s);
    }

    @OnOpen
    public void connect(Session session){
        log.debug("建立连接: " + session);
    }

    @OnMessage
    public void onMessage(String message) {
        log.debug("接受消息: " + message);
    }



    @OnClose
    public void close(Session session) {
        log.debug("关闭: " + session);
    }

    @OnError
    public void error(Throwable throwable) {
        log.debug("关闭: " + throwable.getMessage());
    }
}
