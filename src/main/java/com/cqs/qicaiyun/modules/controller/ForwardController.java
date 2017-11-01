package com.cqs.qicaiyun.modules.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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


    /**
     * 跳转到文章详情页面
     *
     * @return
     */
    @GetMapping("/article/p/{id}")
    @ApiOperation(value = "文章详情")
    @ApiImplicitParams(@ApiImplicitParam(name = "id",paramType = "path",value = "文章ID",dataType = "long",required = true))
    public String articleInfo(@PathVariable("id") String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "/article/articleInfo";
    }

    /**
     * 跳转到文章发布页面
     *
     * @return
     */
    @GetMapping("/article/write")
    public String articlePublish() {
        return "/article/article";
    }


    /**
     * 跳转到文章列表页面
     *
     * @return
     */
    @GetMapping("/article/list/p")
    public String articleList() {
        return "/article/list";
    }


    @GetMapping("")
    public String index(){
        return "/index";
    }
}
