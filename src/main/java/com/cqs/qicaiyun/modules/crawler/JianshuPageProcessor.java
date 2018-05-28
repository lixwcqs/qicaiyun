package com.cqs.qicaiyun.modules.crawler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cqs.qicaiyun.modules.entity.Article;
import com.cqs.qicaiyun.modules.entity.Content;
import com.cqs.qicaiyun.modules.service.ArticleService;
import com.cqs.qicaiyun.modules.service.ContentService;
import com.cqs.qicaiyun.system.entity.User;
import com.cqs.qicaiyun.system.service.IUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import javax.annotation.Resource;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Author:li
 * <p>
 * create date: 18-5-26 19:44
 */
@Log4j2
@Component
public class JianshuPageProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    private BlockingQueue<Html> tasks = new LinkedBlockingQueue<>(100000);


    @Resource(name = "contentServiceImpl")
    private ContentService contentService;

    @Resource(name = "articleServiceImpl")
    private ArticleService articleService;

    @Resource(name = "userService")
    private IUserService userService;


    @Transactional(propagation = Propagation.NESTED)
    public Article extractAndSaveArticle(Html html) {

        Article article = new Article();
        //标题
        article.setTitle(html.xpath("//h1[@class='title']/text()").get());
        //其他信息
        String pageDate = html.xpath("//script[@data-name='page-data']/html()").get();
        JSONObject object = JSON.parseObject(pageDate);
//        log.info(pageDate);
        JSONObject note = JSONObject.parseObject(object.getString("note"));
        Selectable css = html.css("div.show-content");
        article.setUp(note.getInteger("likes_count"));
        String accountId = note.getString("user_id");

        JSONObject author = (JSONObject) note.get("author");
        User user = new User();
        user.setAccount(accountId);
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.setEntity(user);
        user = userService.selectOne(wrapper);
        if (user == null) {
            user = new User();
//            user.setId(1L);
            user.setAccount(accountId);
            user.setNickname(author.getString("nickname"));
            user.setName(author.getString("nickname"));
            user.setPassword("1");
            userService.insert(user);

        }
        log.debug(user);

        article.setAuthorId(user.getId());
        article.setReading(note.getInteger("views_count"));


        Content content = new Content();
        content.setContent(css.smartContent().get());
//        content.setContent("1234");
//        content.setId(1L);
        contentService.insert(content);
        log.debug(content);
        article.setContentId(content.getId());
        article.setUTime(article.getCTime());
//        article.setId(1L);
        articleService.insert(article);
        return article;
    }

    @Override
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        //内容页面 也就需要提取内容的页面
        if (page.getUrl().get().startsWith("https://www.jianshu.com/p")) {
            Html html = page.getHtml();
            try {
                tasks.put(html);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(https://www.jianshu\\.com/[\\w\\-]+/[\\w\\-]+)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public BlockingQueue<Html> getTasks() {
        return tasks;
    }
}
