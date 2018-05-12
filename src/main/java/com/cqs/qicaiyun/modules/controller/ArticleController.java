package com.cqs.qicaiyun.modules.controller;

import com.cqs.qicaiyun.common.result.FailedResult;
import com.cqs.qicaiyun.common.result.PageData;
import com.cqs.qicaiyun.common.result.Result;
import com.cqs.qicaiyun.common.result.SuccessResult;
import com.cqs.qicaiyun.modules.entity.Article;
import com.cqs.qicaiyun.modules.service.ArticleService;
import com.sun.istack.internal.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cqs on 2017/8/21.
 */
//@RequestMapping("/article")
//@RestController
@Log4j2
@Api("文章模块")
public class ArticleController {

    @Resource(name = "articleServiceImpl")
    private ArticleService service;

    //发布文章
    @ApiOperation("文章发布")
    @PostMapping("/publish")
    public Result publish(@NotNull Article article) {
        try {
            service.publish(article);
        } catch (Throwable e) {
            return FailedResult.build().reason("发布失败");
        }
        return SuccessResult.build();
    }


    @PostMapping("/update")
    @ApiOperation("文章修改")
    public Result updateArticle(@NotNull Article article) {
        Article dbArticle = service.selectById(article.getId());
        if (dbArticle == null) {
            return FailedResult.build().reason("文章不存在");
        } else {
            BeanUtils.copyProperties(article, dbArticle, "title", "content");
            dbArticle.setUTime(LocalDateTime.now());
            try {
                service.updateById(dbArticle);
            } catch (Throwable throwable) {
                return FailedResult.build().reason("更新失败");
            }
        }
        return SuccessResult.build();
    }

    @ApiOperation("文章依据主键删除")
    @GetMapping("/delete/{id}")
    public Result deleteArticle(@PathVariable("id") long id) {
        Article dbArticle = service.selectById(id);
        if (dbArticle == null) {
            return FailedResult.build().reason("文章不存在");
        } else {
            try {
                service.deleteById(id);
            } catch (Throwable throwable) {
                return FailedResult.build().reason("删除失败");
            }
        }
        return SuccessResult.<String>build().result("删除成功");
    }

    @ApiOperation("文章根据主键查询")
    //返回详细信息
    @GetMapping("/find/{id}")
    public Result findById(@PathVariable("id") long id) {
        Article article = service.selectById(id);
        if (article == null) {
            return FailedResult.build().reason("文章不存在");
        } else {
            return SuccessResult.build().result(article);
        }
    }


    @GetMapping("/list")
    public List<String> list(){
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        list.add(new Date().toString());
        return list;
    }



    @GetMapping("/say")
    public String say(){

         String result = "Helloicaiyun";
         return result;
    }

    @GetMapping("/result")
    public  SuccessResult<String> result(){
        SuccessResult<String> result = SuccessResult.<String>build().data("Hello Qicaiyun");
        return result;
    }
    @GetMapping("/fail")
    public  FailedResult fail(){
        return FailedResult.build().reason("查询失败");
    }

    @GetMapping("/list/{rows}/{page}")
    @ApiOperation("文章翻页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, defaultValue = "10", paramType = "path", dataType = "long", name = "bmt", value = "第一次请求的时间戳，" +
                    "后面翻页均是同一个"),
            @ApiImplicitParam(required = true, defaultValue = "1", paramType = "path", dataType = "int", name = "page", value = "页码")
    })
    public Result list(@PathVariable("bmt") long bmt,
                       @PathVariable("page") int page) {
        try {
            final PageData<Article> list = service.list(page, bmt);
            return SuccessResult.build().result(list);
        } catch (Exception e) {
            return FailedResult.build().reason("查询失败");
        }

    }
}
