package com.cqs.qicaiyun.modules.controller;

import com.cqs.qicaiyun.modules.entity.Article;
import com.cqs.qicaiyun.modules.entity.Content;
import com.cqs.qicaiyun.modules.service.ArticleService;
import com.cqs.qicaiyun.modules.service.ContentService;
import com.sun.istack.internal.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by cqs on 2017/8/21.
 */
@RequestMapping("/article")
@Controller
@Log4j2
public class ArticleController {


    private final static String VIEW = "redirect:/article/list/p";

    @Resource(name = "articleServiceImpl")
    private ArticleService service;

    @Resource(name = "contentServiceImpl")
    private ContentService contentService;

    //发布文章
    @PostMapping("/publish")
    public String publish(@NotNull Article article) {

        Content content = new Content();
        content.setContent(article.getContent());
        boolean insertOK = contentService.insert(content);
        if (!insertOK) {
            log.error("发布文章失败：写入文本内容异常");
            throw new RuntimeException("发布文章失败");
        }
        article.setContentId(content.getId());
        LocalDateTime now = LocalDateTime.now();
        article.setUTime(now);
        article.setUTime(now);
        service.insert(article);
        return VIEW;
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
        Content content = contentService.selectById(article.getContentId());
        article.setContent(content.getContent());
        return article;
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Article> list() {
        List<Article> list = service.selectList(null);
        return list;
    }

    /**
     * 跳转到文章详情页面
     *
     * @return
     */
    @GetMapping("/p/{id}")
    public String articleInfoJsp(@PathVariable("id") String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "/article/articleInfo";
    }

    /**
     * 跳转到文章发布页面
     *
     * @return
     */
    @GetMapping("/write")
    public String addPageJsp() {
        return "/article/article";
    }


    /**
     * 跳转到文章列表页面
     *
     * @return
     */
    @GetMapping("/list/p")
    public String listJsp() {
        return "/article/list";
    }
}
