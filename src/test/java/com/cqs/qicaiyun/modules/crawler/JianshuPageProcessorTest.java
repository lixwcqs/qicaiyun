package com.cqs.qicaiyun.modules.crawler;

import com.cqs.configuration.TestBaseServiceConf;
import com.cqs.qicaiyun.common.tools.ThreadPoolUtils;
import org.junit.Test;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisScheduler;
import us.codecraft.webmagic.selector.Html;

import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author:li
 * <p>
 * create date: 18-5-27 16:54
 */
public class JianshuPageProcessorTest extends TestBaseServiceConf {


    @Resource
    private JianshuPageProcessor processor;

    @Test
    public void process() {
        ThreadPoolExecutor exec = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        new Thread(() -> {
            while (true) {
                if (exec.getQueue().size() <= 1000) {
                    Runnable task = () -> {
                        try {
                            Html html = processor.getTasks().take();
                            processor.extractAndSaveArticle(html);
                        } catch (InterruptedException e) {
                            Thread.yield();
                        }

                    };
                    exec.submit(task);
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    Thread.yield();
                }
            }
        }).start();
        Spider.create(processor)
                .setScheduler(new RedisScheduler("localhost"))
                .addUrl("https://www.jianshu.com")
                .thread(4)
                .run();
    }
}