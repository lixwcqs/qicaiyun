package com.cqs.qicaiyun.modules.service;

import com.cqs.configuration.TestBaseServiceConf;
import com.cqs.qicaiyun.modules.entity.Advice;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by cqs on 2018/4/21.
 */
@Log4j2
public class AdviceServiceTest extends TestBaseServiceConf implements BeanFactoryAware {

    @Autowired
    private AdviceService service;

    private BeanFactory beanFactory;

    private String dir = "/home/li/softs/mysql/small_train/";


    @Test
    public void batchInsertMQ() throws Exception {
        service.batchInsertMQ();
        TimeUnit.DAYS.sleep(1);
    }

    @Test
    public void writeMQ() {
        int tasks = 1;
        for (int i = 0; i < tasks; i++) {
            log.info("处理第{}个文件中...",i);
            service.writeMQ(dir + i + ".csv");
            log.info("处理完第{}文件",i);
        }
        log.info("处理完所有的文件");

    }

    @Test
    public void readSave2() throws Exception {

        int tasks = 35;
        for (int i = 0; i < tasks; i++) {
            log.info("处理第{}个文件:" + i);
            service.batchInsert(dir + i + ".csv");
        }
        log.info("处理完所有的文件");

    }

    @Test
    public void insertBatch() throws Exception {
        List<Advice> list = new ArrayList<>();
        for (int i = 0; i <3 ; i++) {
            list.add(mockAdvice());
        }
        boolean b = service.insertBatch(list);
        System.out.println(b);
    }

    private Advice mockAdvice() {
        Random r = new Random();
        Advice advice = new Advice();
        advice.setOs(r.nextInt(100));
        advice.setIsAttributed((byte)8);
        advice.setClickTime(new Date());
        advice.setChannel(r.nextInt(100));
        advice.setApp(r.nextInt(100));
        advice.setAdvice((long)r.nextInt(1000000));
        return advice;
    }

    @Test
    public void selectById() throws Exception {
        Advice advice = service.selectById(1952101);
        System.out.println(advice);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}