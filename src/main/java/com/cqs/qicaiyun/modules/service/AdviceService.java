package com.cqs.qicaiyun.modules.service;

import com.baomidou.mybatisplus.service.IService;
import com.cqs.qicaiyun.modules.entity.Advice;

/**
 * Created by cqs on 2018/4/21.
 */
public interface AdviceService  extends IService<Advice> {

    void batchInsertMQ();
    void batchInsert(String file);
    void writeMQ(String file);
}
