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

/**
 * Created by cqs on 2018/4/21.
 */
@Log4j2
public class AdviceServiceTest extends TestBaseServiceConf implements BeanFactoryAware {

    @Autowired
    private AdviceService service;

    private BeanFactory beanFactory;


    @Test
    public void scope() throws Exception {
        AdviceService service = beanFactory.getBean(AdviceService.class);
        AdviceService service2 = beanFactory.getBean(AdviceService.class);
        System.out.println();
    }

    //    @Test
    public void readSave() throws Exception {

        service.selectById(1);
        System.out.println("1");
        ExecutorService exec = Executors.newFixedThreadPool(8);
        String dir = "F:/data/small_train/";
        int tasks = 1;
        final Queue<String> queue = new LinkedList<>();
        for (int i = 0; i < tasks; i++) {
            queue.add(dir + i + ".csv");
        }


        CountDownLatch latch = new CountDownLatch(tasks);
        while (queue.size() != 0) {
            final String task = queue.poll();
            exec.submit(new Runnable() {
                @Override
                public void run() {
//                    AdviceService service = beanFactory.getBean(AdviceServiceImpl.class);
                    service.readSave(task);
                    latch.countDown();
                }
            });


        }
        latch.await();
        log.info("处理完所有的文件");
        exec.shutdown();

    }





    @Test
    public void readSave2() throws Exception {
        String dir = "F:/data/small_train/";
        int tasks = 35;
        for (int i = 0; i < tasks; i++) {
            log.info("处理第{}个文件:" + i);
            service.readSave(dir + i + ".csv");
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