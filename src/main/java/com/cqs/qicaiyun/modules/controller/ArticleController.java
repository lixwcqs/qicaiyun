package com.cqs.qicaiyun.modules.controller;

import com.cqs.qicaiyun.modules.entity.Article;
import com.cqs.qicaiyun.modules.service.ArticleService;
import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by cqs on 2017/8/21.
 */
@RequestMapping("/article")
@Controller
@Log4j2
public class ArticleController {
    private final static String VIEW = "redirect:/fd/article/list/p";

    @Resource(name = "articleServiceImpl")
    private ArticleService service;


    //发布文章
    @PostMapping("/publish")
    public String publish(@NotNull Article article) {
        service.publish(article);
        return "redirect:/fd/article/p/" + article.getId();
    }


    @PostMapping("/update")
    public String updateArticle(@NotNull Article article) {
        Article dbArticle = service.selectById(article.getId());
        if (dbArticle == null) {
            throw new RuntimeException("没有查找到对应的记录，修改失败");
        } else {
            BeanUtils.copyProperties(article, dbArticle, "title", "content");
            dbArticle.setUTime(LocalDateTime.now());
            service.updateById(dbArticle);
        }
        return VIEW;
    }


    @GetMapping("/delete/{id}")
    public String deleteArticle(@PathVariable("id") long id) {
        boolean delete = service.deleteById(id);
        return VIEW;
    }

    //返回详细信息
    @GetMapping("/find/{id}")
    @ResponseBody
    public Article findById(@PathVariable("id") long id) {
        Article article = service.selectById(id);
        //查询出内容
        return article;
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Article> list() {
        List<Article> list = service.selectList(null);
        return list;
    }

    @ApiOperation("访问用户页面")
    @GetMapping("/user/u")
    public String infoJsp() {
        return "/user/userInfo";
    }

    @ApiOperation("访问注册页面")
    @GetMapping("/user/r")
    public String registerJsp() {
        return "/user/register";
    }
}
