package com.cqs.qicaiyun.modules.controller;

import com.cqs.qicaiyun.services.RedisService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cqs on 2018/10/4.
 */
@RestController
@Log4j2
public class DemoController {


    @Autowired
    private RedisService redisService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }


    @GetMapping("/redis/{key}")
    public String count(@PathVariable("key") String key){
        return redisService.getStr(key);
    }

    public static void main(String[] args) {

    }
}
