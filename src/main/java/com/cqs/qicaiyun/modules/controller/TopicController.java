package com.cqs.qicaiyun.modules.controller;

import com.cqs.qicaiyun.modules.entity.Topic;
import com.cqs.qicaiyun.modules.service.TopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 专题controller
 * Created by cqs on 2017/9/13.
 */
@RestController
@Api(description = "专题")
@Log4j2
@RequestMapping("/topic")
public class TopicController {

    @Resource(name = "topicServiceImpl")
    private TopicService service;

    @GetMapping("/recommendations/list")
    @ApiOperation("推荐专题列表")
    public List<Topic> recommendTopics(){
        return service.selectList(null);
    }

    @GetMapping("/hot/list")
    @ApiOperation("热门专题列表")
    public List<Topic> hotTopics(){
        return service.selectList(null);
    }
}
