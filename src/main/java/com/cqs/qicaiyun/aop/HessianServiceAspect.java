package com.cqs.qicaiyun.aop;

import com.cqs.qicaiyun.common.tools.ThreadPoolUtils;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 拦截远程服务访问方法 在线程中执行
 * Created by cqs on 2017/11/9.
 */
@Aspect
@Component
@Log4j2
public class HessianServiceAspect {


    @Pointcut("this(com.cqs.qicaiyun.services.HessianService)")
    private void hessianService() {
    }


    @Around("hessianService()")
    public Object threadInvoker(ProceedingJoinPoint pjp) {
        Future<Object> future = ThreadPoolUtils.getInstance().submit(() -> {
            try {
                return pjp.proceed(pjp.getArgs());
            } catch (Throwable throwable) {
                log.error("执行{}异常\r\n: {}", pjp.getSignature(), throwable.getCause());
                return null;
            }
        });
        Object result = null;
        try {
            result = future.get(30, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("执行{}异常\r\n: {}", pjp.getSignature(), e.getCause());
        }
        log.debug("执行{}结果：{}", pjp.getSignature(), result);
        return result;
    }

    @Around("hessianService()")
    public void stopWatch(ProceedingJoinPoint pjp) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());

    }


}
