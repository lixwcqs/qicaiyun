package com.cqs.qicaiyun.modules.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cqs.qicaiyun.conf.Application;
import com.cqs.qicaiyun.mock.CommentMock;
import com.cqs.qicaiyun.modules.entity.Comment;
import com.cqs.qicaiyun.system.service.IUserService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cqs on 2017/8/27.
 */
@SpringBootTest(classes = Application.class)
public class CommentServiceTest extends AbstractTestNGSpringContextTests {

    @Resource(name = "commentServiceImpl")
    private CommentService service;
    @Resource(name = "userService")
    private IUserService userService;
    @Resource(name = "articleServiceImpl")
    private ArticleService articleService;

    @Test
    public void testInsert() throws Exception {
        for (int i = 0; i < 10; i++) {
            Comment comment  = CommentMock.mockComment();
            comment.setUserId(userService.selectList(null).get(0).getId());
            comment.setArticleId(articleService.selectList(null).get(0).getId());
            service.insert(comment);
        }
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


    @Test
    public void testSelectPage() throws Exception {
        Page<Comment> page = new Page<>(1,5);
        EntityWrapper<Comment> wrapper = new EntityWrapper<>();
        wrapper.eq("article_id",1);
        wrapper.orderBy("down",false);
        service.selectPage(page, wrapper);
        page.getRecords().forEach(System.out::println);
        System.out.println(page);
    }
}