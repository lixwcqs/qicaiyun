package com.cqs.qicaiyun.system.net.heartbeat;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by cqs on 2018/4/21.
 */
@Log4j2
public class HeartBeatClient {
    private static SocketChannel channel;

    @Scheduled(fixedRate = 10000)
    public static void checkServerStatus() {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        int read = 0;
        AtomicInteger failTimes = new AtomicInteger(0);
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            if (channel == null || !channel.isOpen()) {
                try {
                    channel = SocketChannel.open();
                } catch (IOException e) {
                    log.error("打开通道失败", e.getMessage());
                    failTimes.addAndGet(1);
                }
            }
            if (!channel.isConnected()) {
                try {
                    channel.connect(new InetSocketAddress("192.168.2.108", 23456));
                    channel.socket().setSoTimeout(1000);
                    channel.socket().setKeepAlive(true);
                    channel.socket().setReuseAddress(true);
                } catch (IOException e) {
                    log.error("建立连接失败", e.getMessage());
                    log.info("服务离线");
                    try {
                        channel.close();
                    } catch (IOException e1) {
                    }
                    return;
                }
            }
            try {
                if (read != channel.read(buffer)) {
                    buffer.flip();
                    byte[] data = buffer.array();
                    if (data[0] == 1) {
                        log.info("服务正常");
                        failTimes.set(0);
                    }else{
                        log.info("未知服务");
                    }
                }
            } catch (IOException e) {
                log.error("读取数据失败");
                failTimes.addAndGet(1);
                if (failTimes.get() < 3) {
                    log.info("服务没响应");
                } else {
                    log.info("服务离线");
                    try {
                        channel.close();
                    } catch (IOException e1) {
                        log.info("socket关闭异常");
                    }
                }
            }
        }, 0, 3, TimeUnit.SECONDS);
    }


    public static void main(String[] args) throws IOException {
        checkServerStatus();
    }
}
