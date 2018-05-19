package com.cqs.qicaiyun.mq.rabbitmq;

import com.cqs.qicaiyun.common.tools.serializer.KryoUtils;
import com.cqs.qicaiyun.modules.entity.Advice;
import com.cqs.qicaiyun.modules.service.AdviceService;
import com.rabbitmq.client.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Log4j2
@Component
@Scope("prototype")
public class AdviceRabbitMQUtil {
    private static final ConnectionFactory factory = initConnectionFactory();

    private static final String defQueue = "qicaiyun";
    private Connection connection;
    private Channel channel;
    private KryoUtils kryo = new KryoUtils();
    private volatile boolean isDecalred = false;


    @Autowired
    private AdviceService adviceService;


    private static ConnectionFactory initConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.2.116");
        factory.setUsername("qicaiyun");
        factory.setPassword("1");
        return factory;
    }

    public void consumer() {
        channel();
        try {
            List<Advice> list = new ArrayList<>();
            channel.queueDeclare(defQueue, true, false, false, null);
            int batch = 1000;
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    //do work
                    Advice advice = kryo.deserialize(body, Advice.class);
                    list.add(advice);
                    if (list.size() >= batch) {
                        log.debug(Thread.currentThread() +"\t" + adviceService + "模拟批量处理{}条消息", list.size());
                        adviceService.insertBatch(list);
                        list.clear();
                    }
                }
            };
            channel.basicConsume(defQueue, consumer);
            log.debug("consumer over...");
        } catch (IOException e) {
            throw new RuntimeException("发送消息失败" + e.getCause());
        }
    }

    public <T extends Serializable> void sendMessage(T message) {
        sendMessage(kryo.serialize(message));
    }

    public void sendMessage(byte[] message) {
        channel();
        try {
            if (!isDecalred) {
                channel.queueDeclare(defQueue, true, false, false, null);
            }
            channel.basicPublish("", defQueue, null, message);
        } catch (IOException e) {
            throw new RuntimeException("发送消息失败" + e.getCause());
        }
    }

    private Connection connection() {
        if (connection == null) {
            synchronized (AdviceRabbitMQUtil.class) {
                if (connection == null) {
                    try {
                        connection = factory.newConnection();
                    } catch (IOException | TimeoutException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }

    private Channel channel() {
        if (channel == null) {
            synchronized (AdviceRabbitMQUtil.class) {
                if (channel == null) {
                    try {
                        channel = connection().createChannel();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return channel;
    }

    public void close() {
        if (channel != null) {
            try {
                channel.close();
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(2);
        ExecutorService exec = Executors.newSingleThreadExecutor();
        int count = 0;
        while (++count < 5) {
            barrier.reset();
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });

            System.out.println("THREAD OVER");
            barrier.await();
            System.out.println("MAIN THREAD OVER");
        }
        exec.shutdown();
    }
}
