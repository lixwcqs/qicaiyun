package com.cqs.qicaiyun.modules.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cqs.qicaiyun.modules.entity.Comment;

import java.util.List;

/**
 * Created by cqs on 2017/8/20.
 */
public interface CommentService extends IService<Comment> {

    List<Comment> selectByArticleId(Long articleId);

    Page<Comment> selectPage(Page<Comment> page, EntityWrapper<Comment> wrapper);
}
