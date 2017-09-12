package com.cqs.qicaiyun.modules.controller;

import com.cqs.qicaiyun.modules.entity.Comment;
import com.cqs.qicaiyun.modules.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
     * @param articleId 文章ID
     * @return
     */
    @PostMapping("/a/{articleId}")
    public Boolean insertComments(@PathVariable("articleId") Long articleId) {
        Comment comment = new Comment();
        return service.insert(comment);
    }

    /**
     * 更新评论
     *
     * @param articleId 文章ID
     * @return
     */
    @PatchMapping("/a/{articleId}")
    public Boolean updateComments(@PathVariable("articleId") Long articleId) {
        throw new RuntimeException("not Implementation ");
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
