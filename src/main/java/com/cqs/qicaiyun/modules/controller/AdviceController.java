package com.cqs.qicaiyun.modules.controller;

import com.cqs.qicaiyun.modules.service.AdviceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * Created by cqs on 2018/4/24.
 */
@RestController
@RequestMapping("/advice")
@Log4j2
public class AdviceController {


    @Resource(name = "adviceServiceImpl")
    private AdviceService service;

    @GetMapping("/bi")
    public Mono<Boolean> importDate(){

        String dir = "F:/data/small_train/";
        int tasks = 35;
        for (int i = 0; i < tasks; i++) {
            log.info("处理第{}个文件:" + i);
            service.batchInsert(dir + i + ".csv");
        }
        log.info("处理完所有的文件");
        return Mono.just(true);
    }

}
