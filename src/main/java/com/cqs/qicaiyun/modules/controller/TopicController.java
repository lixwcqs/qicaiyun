package com.cqs.qicaiyun.modules.controller;

import com.cqs.qicaiyun.modules.entity.Article;
import com.cqs.qicaiyun.modules.entity.Follower;
import com.cqs.qicaiyun.modules.entity.Topic;
import com.cqs.qicaiyun.modules.helper.FollowerType;
import com.cqs.qicaiyun.modules.service.ArticleService;
import com.cqs.qicaiyun.modules.service.FollowerService;
import com.cqs.qicaiyun.modules.service.TopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
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


    @Resource(name = "articleServiceImpl")
    private ArticleService articleService;

    @Resource(name = "followerServiceImpl")
    private FollowerService followerService;

    @GetMapping("/recommendations/list")
    @ApiOperation("推荐专题列表")
    public List<Topic> recommendTopics() {
        return service.selectList(null);
    }

    @GetMapping("/hot/list")
    @ApiOperation(value = "热门专题列表")
    public List<Topic> hotTopics() {
        return service.selectList(null);
    }


    @GetMapping("/{topicId}/host")
    @ApiOperation("某专题下的热门文章列表")
    @ApiImplicitParams(@ApiImplicitParam(name = "topicId", paramType = "path", value = "主题ID", dataType = "long"))
    public List<Article> hotArticlesTopic(@PathVariable Long topicId) {
        return articleService.selectList(null);
    }


    @GetMapping("/{topicId}/comment")
    @ApiOperation("某专题下最新评论文章列表")
    @ApiImplicitParams(@ApiImplicitParam(name = "topicId", paramType = "path", value = "主题ID", dataType = "long"))
    public List<Article> commentArticlesTopic(@PathVariable Long topicId) {
        return articleService.selectList(null);
    }

    @GetMapping("/{topicId}/include")
    @ApiOperation("某专题下最新评论文章列表")
    @ApiImplicitParams(@ApiImplicitParam(name = "topicId", paramType = "path", value = "主题ID", dataType = "long"))
    public List<Article> includeArticlesTopic(@PathVariable Long topicId) {
        return articleService.selectList(null);
    }

    @GetMapping("/{topicId}")
    @ApiOperation("查询专题")
    @ApiImplicitParams(@ApiImplicitParam(name = "topicId", paramType = "path", value = "主题ID", dataType = "long"))
    public Topic findById(@PathVariable Long topicId) {
        return service.selectById(topicId);
    }

    @PutMapping("/f/{userId}/{topicId}")
    @ApiOperation("关注专题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", paramType = "path", value = "用户ID", dataType = "long"),
            @ApiImplicitParam(name = "topicId", paramType = "path", value = "主题ID", dataType = "long")
    })
    public Boolean follow(@PathVariable Long userId, @PathVariable Long topicId) {
        Follower follower = getFollower(userId, topicId);
        return followerService.follow(follower);
    }

    @DeleteMapping("/{topicId}")
    @ApiOperation("取消关注专题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", paramType = "path", value = "用户ID", dataType = "long"),
            @ApiImplicitParam(name = "topicId", paramType = "path", value = "主题ID", dataType = "long")
    })
    public Boolean unfollow(@PathVariable Long userId, @PathVariable Long topicId) {
        Follower follower = getFollower(userId, topicId);
        return followerService.unfollow(follower);
    }


    @PostMapping("/tp")
    @ApiOperation("新建专题")
    public Boolean create(@RequestBody @NotNull Topic topic) {
        return service.insert(topic);
    }

    @PatchMapping("/tp")
    @ApiOperation("更新专题")
    public Boolean update(@RequestBody @NotNull Topic topic) {
        //--- 选择要更新的项目 不能丢字段
        throw new RuntimeException("还没想好");
    }


    @DeleteMapping("/tp")
    @ApiOperation("删除专题")
    @ApiImplicitParams(@ApiImplicitParam(name = "topicId", paramType = "path", value = "主题ID", dataType = "long"))
    public Boolean delete(@PathVariable Long topicId) {
        return service.deleteById(topicId);
    }

    private Follower getFollower(@NotNull Long userId, @NotNull Long topicId) {
        Follower follower = new Follower();
        follower.setFromUserId(userId);
        follower.setToId(topicId);
        follower.setType(FollowerType.TOPIC);
        return follower;
    }

}
