package com.cqs.qicaiyun.websocket;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by cqs on 2018/3/31.
 */
@Component
@Log4j2
public class PushDemo implements InitializingBean{

    @Resource
    WebSocketServer server;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("---start");
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                server.sendMessage("Hello--");
                log.info("后台主动推动消息");
            }
        },10,3,TimeUnit.SECONDS);

    }
}
