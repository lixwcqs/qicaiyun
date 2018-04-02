package com.cqs.qicaiyun.websocket;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * 无法创建单例
 * Created by cqs on 2018/3/31.
 */
@ServerEndpoint("/ws")
@Component
@Log4j2
public class WebSocketServer {

    private Session session;
    private static CopyOnWriteArraySet<WebSocketServer> wsClientMap = new CopyOnWriteArraySet<>();

    @OnMessage
    public void onMessage(String message) {
        log.info("onMessage:" + message);
        sendMessage(message);
    }


    @OnOpen
    public void onOpen(Session session) {
        log.info("opening:");
        wsClientMap.add(this);
        this.session = session;
    }

    @OnClose
    public void onClose() {
        wsClientMap.remove(this);
        log.info("close:");
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
        log.info("error");
    }


    public void sendMessage(String message) {
        try {
            for (WebSocketServer item : wsClientMap) {
                item.session.getBasicRemote().sendText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\t" + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
