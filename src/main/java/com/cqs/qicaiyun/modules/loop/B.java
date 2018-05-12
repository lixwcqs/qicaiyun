package com.cqs.qicaiyun.modules.loop;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by cqs on 2018/5/7.
 */
@Component
public class B  implements InitializingBean{

    public int id = 2;

    @Resource
    private A a;


    @Override
    public String toString() {
        return "B{" +
                "id=" + id +
                ", a=" + a.id +
                '}';
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化B结束" + toString());
    }
}
