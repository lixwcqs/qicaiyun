package com.cqs.qicaiyun.modules.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户处理页面跳转
 * Created by cqs on 2017/9/13.
 */
@Controller
@Log4j2
@RequestMapping("/fd")
@Api(description = "跳转处理器")
public class ForwardController {


    @ApiOperation(value = "热门专题页")
    @GetMapping("/topic/list")
    public String topics() {
        return "/topic/list";
    }
}
