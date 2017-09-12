package com.cqs.qicaiyun.modules.service;

import com.cqs.config.BaseConfigurationTestNG;
import com.cqs.jianshu.modules.entity.Comment;
import com.cqs.mock.CommentMock;
import com.cqs.system.service.IUserService;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cqs on 2017/8/27.
 */
public class CommentServiceTest extends BaseConfigurationTestNG {

    @Resource(name = "commentServiceImpl")
    private CommentService service;
    @Resource(name = "userService")
    private IUserService userService;
    @Resource(name = "articleServiceImpl")
    private ArticleService articleService;

    @Test
    public void testInsert() throws Exception {
        Comment comment  = CommentMock.mockComment();
        comment.setUserId(userService.selectList(null).get(0).getId());
        comment.setArticleId(articleService.selectList(null).get(0).getId());
        service.insert(comment);
    }

    @Test
    public void selectByArticleId() throws Exception {
        Long articleId = 900757830410186753L;
        service.selectByArticleId(articleId);
    }

    @Test
    public void selectList() throws Exception {
        List<Comment> comments = service.selectList(null);
        if (comments.size() > 0)
        System.out.println(comments.get(0));
    }
}