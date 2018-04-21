package com.cqs.qicaiyun.system.net.heartbeat;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * 23456端口用于心跳处理
 * <p>
 * Created by cqs on 2018/4/21.
 */
@Log4j2
public class HeartBeatServer {

    public static void main(String[] args) throws IOException {
        heartBeat();
    }

    //基于socket设计的心跳
    public static void heartBeat() throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        try {
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_ACCEPT);
            channel.bind(new InetSocketAddress("192.168.2.108", 23456));
            byte status = 1;//OK
            byte[] bs = new byte[1];
            bs[0] = status;
            ByteBuffer bb = ByteBuffer.wrap(bs);
            ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
            Map<SocketChannel, ScheduledFuture> futureMap = new ConcurrentHashMap<>();
            log.info("socket启动");
            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey sk = iterator.next();
                    iterator.remove();
                    log.info("接收到事件");
                    ServerSocketChannel ssc = (ServerSocketChannel) sk.channel();
                    final SocketChannel accept = ssc.accept();
                    accept.socket().setKeepAlive(true);
                    accept.socket().setOOBInline(true);
                    log.info("建立连接:" + accept.isConnected());
                    ScheduledFuture<?> scheduledFuture = exec.scheduleAtFixedRate(() -> {
                        if (!accept.isOpen()||!accept.isConnected()) {
                            try {
                                log.info("socket error");
                                ScheduledFuture future = futureMap.get(accept);
                                if (future != null) {
                                    future.cancel(true);
                                    log.info("关闭异常socket" + channel.socket().getInetAddress());
                                }
                                accept.close();
                            } catch (IOException e) {

                            }
                        } else {
                            synchronized (bb) {
                                bb.rewind();
                                try {
                                    accept.write(bb);
                                    log.debug("发送服务器状态结束");
                                } catch (IOException e) {
                                    log.debug("发送服务器状态失败");
                                    try {
                                        accept.close();
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }

                            }
                        }


                    }, 0, 3, TimeUnit.SECONDS);
                    futureMap.put(accept,scheduledFuture);
                }
            }
        } finally {
            selector.close();
            channel.close();
        }
    }


}
