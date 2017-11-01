package com.cqs.qicaiyun.modules.controller;

import com.cqs.qicaiyun.modules.entity.Comment;
import com.cqs.qicaiyun.modules.service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by cqs on 2017/8/27.
 */
@RequestMapping("/comment")
@RestController
public class CommentController {

    @Resource(name = "commentServiceImpl")
    private CommentService service;

    /**
     * 根据文章ID查询评论列表
     *
     * @param articleId 文章ID
     * @return
     */
    @GetMapping("/a/{articleId}")
    public List<Comment> selectComments(@PathVariable("articleId") Long articleId) {
        return service.selectByArticleId(articleId);
    }

    /**
     * 保存评论
     *
     * @return
     */
    @PostMapping("/a")
    @ApiOperation("保存评论")
    public Boolean insertComments(@RequestBody Comment comment) {
        LocalDateTime now = LocalDateTime.now();
        Assert.notNull(comment.getArticleId(),"文章ID不能为空");
        comment.setCTime(now);
        comment.setUp(0);
        comment.setDown(0);
        return service.insert(comment);
    }


    /**
     * 删除评论
     *
     * @param articleId 文章ID
     * @return
     */
    @DeleteMapping("/a/{articleId}")
    public Boolean deleteComments(@PathVariable("articleId") Long articleId) {
        return service.deleteById(articleId);
    }

}
