package com.cqs.qicaiyun.modules.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cqs.qicaiyun.common.tools.ThreadPoolUtils;
import com.cqs.qicaiyun.common.tools.serializer.KryoUtils;
import com.cqs.qicaiyun.modules.entity.Advice;
import com.cqs.qicaiyun.modules.mapper.AdviceMapper;
import com.cqs.qicaiyun.modules.service.AdviceService;
import com.cqs.qicaiyun.mq.rabbitmq.AdviceRabbitMQUtil;
import com.csvreader.CsvReader;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by cqs on 2018/4/21.
 */
@Scope(value = "prototype")
@Service
@Log4j2
public class AdviceServiceImpl extends ServiceImpl<AdviceMapper, Advice> implements AdviceService, BeanFactoryAware {


    private static BeanFactory factory;


    private static final ThreadLocal<AdviceServiceImpl> local = new ThreadLocal<AdviceServiceImpl>() {
        @Override
        protected AdviceServiceImpl initialValue() {
            return factory.getBean(AdviceServiceImpl.class);
        }
    };


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public boolean insertBatch(List<Advice> entityList) {
        return baseMapper.insertBatch(entityList);
    }

    /**
     * 从MQ中读取 插入记录
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void batchInsertMQ() {
        //队列中获取消息
        ExecutorService instance = ThreadPoolUtils.getInstance();
        for (int i = 0; i < 8; i++) {
            AdviceRabbitMQUtil mqUtil = factory.getBean(AdviceRabbitMQUtil.class);
            instance.execute(mqUtil::consumer);
        }

        try {
            TimeUnit.HOURS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeMQ(String file) {
        //消息队列
        AdviceRabbitMQUtil mqUtil = new AdviceRabbitMQUtil();
        try {
            CsvReader csvReader = new CsvReader(file);
            // 读表头
            csvReader.readHeaders();
            int count = 0;
            while (csvReader.readRecord()) {
                Advice advice = readAdvice(csvReader);
                if (advice == null) continue;
                mqUtil.sendMessage(advice);
                if (++count % 10000 == 0) {
                    log.debug("生成第{}万条消息", count / 10000);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mqUtil.close();
        }
    }



    public void batchInsert(String file) {
        try {
            int count = 0;
            // 创建CSV读对象
            CsvReader csvReader = new CsvReader(file);
            // 读表头
            csvReader.readHeaders();
            int size = 8 << 2;
            ExecutorService exec = Executors.newFixedThreadPool(size);
            BlockingQueue<List<Advice>> tasks = new LinkedBlockingQueue<>(size);
            List<Advice> list = new ArrayList<>();
            AtomicBoolean working = new AtomicBoolean(true);
            AtomicInteger c = new AtomicInteger(0);
            Semaphore semaphore = new Semaphore(size);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Object obj = new Object();

                    while (true) {
                        synchronized (obj) {
                            if (!working.get() && tasks.isEmpty()) break;
                        }
                        try {
                            semaphore.acquire();
                            final List<Advice> task = tasks.take();
                            semaphore.release();
                            exec.submit(new Runnable() {
                                @Override
                                public void run() {
//                                        StopWatch stopWatch = new StopWatch();
//                                        stopWatch.start();
                                    try {
//                                        log.info("消费第 {} 个任务...",c1.addAndGet(1));
                                        semaphore.acquire();
                                        local.get().insertBatch(task);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } finally {
                                        semaphore.release();
                                    }
                                    int record = c.addAndGet(1);
                                    if (record % 500 == 0) {
                                        log.info("插入5万条记录." + record);
                                    }
//                                        stopWatch.stop();
//                                        log.info("插入数据库" + c.addAndGet(1) + "\t时间开销:" + stopWatch.getTime() / 1000.0 + "秒");
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //insertBatch(task);

                    }
                }
            };
            new Thread(runnable).start();
            while (csvReader.readRecord()) {
                Advice advice = readAdvice(csvReader);
                if (advice == null) continue;

                list.add(advice);
                if (list.size() == 1000) {
                    try {
                        tasks.put(list);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    log.info("添加新任务到队列" + tasks.size());
                    count += list.size();
                    list = new ArrayList<>();
                }
            }
            working.set(false);
            count += list.size();
            log.info("记录条数:" + count);
            exec.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Advice readAdvice(CsvReader csvReader) {
        // 读一整行
        String rawRecord = csvReader.getRawRecord();
        String[] split = rawRecord.split(",");
        if (split.length != 8) return null;
        Advice advice = new Advice();
        advice.setIp(Integer.valueOf(split[0]));
        advice.setApp(Integer.valueOf(split[1]));
        advice.setAdvice(Long.valueOf(split[2]));
        advice.setOs(Integer.valueOf(split[3]));
        advice.setChannel(Integer.valueOf(split[4]));
        try {
            advice.setAttributedTime(DateUtils.parseDate(split[5], "yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            log.info("日期转换失败");
            advice.setAttributedTime(null);
        }
        try {
            if (StringUtils.isNotEmpty(split[6])) {
                advice.setClickTime(DateUtils.parseDate(split[6], "yyyy-MM-dd HH:mm:ss"));
            }
        } catch (ParseException e) {
            log.info("日期转换失败");
            advice.setClickTime(null);
        }
        advice.setIsAttributed(Byte.valueOf(split[7]));
        return advice;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        synchronized (AdviceServiceImpl.class) {
            if (factory == null) {
                factory = beanFactory;
            }
        }
    }
}
