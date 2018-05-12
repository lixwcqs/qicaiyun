package com.cqs.qicaiyun.modules.loop;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by cqs on 2018/5/7.
 */
@Component
public class A implements InitializingBean {


    public int id = 1;

    @Resource
    private B b;


    @Override
    public String toString() {
        return "A{" +
                "id=" + id +
                ", b=" + b.id +
                '}';
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化A结束：" + toString());
    }
}
