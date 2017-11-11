package com.cqs.qicaiyun.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cqs.qicaiyun.modules.entity.Comment;
import com.cqs.qicaiyun.modules.entity.Reply;
import com.cqs.qicaiyun.modules.mapper.CommentMapper;
import com.cqs.qicaiyun.modules.service.CommentService;
import com.cqs.qicaiyun.modules.service.ReplyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by cqs on 2017/8/20.
 */
@Service
@Log4j2
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource(name = "replyServiceImpl")
    private ReplyService replyService;


    @Resource
    private CommentMapper commentMapper;


    //根据文章ID查询评论列表
    @Override
    public List<Comment> selectByArticleId(Long articleId) {
        //查询出文章articleId的所有评论
        Wrapper<Comment> wrapper = new Wrapper<Comment>() {
            @Override
            public String getSqlSegment() {
                return String.format("WHERE article_id = %d ORDER BY c_time desc ", articleId);
            }
        };
        List<Comment> comments = selectList(wrapper);
        Wrapper<Reply> replyWrapper = new Wrapper<Reply>() {
            @Override
            public String getSqlSegment() {
//                return String.format("SELECT * FROM reply WHERE comment_id in (SELECT id FROM comment c WHERE c.article_id = %d)", articleId);
                return String.format("WHERE comment_id in (SELECT id FROM comment c WHERE c.article_id = %d)", articleId);
            }
        };
        //找出文章所有评论的所有的回复
        List<Reply> replyList = replyService.selectList(replyWrapper);
        //依据getCommentId分组
        Map<Long, List<Reply>> replyMap = replyList.stream().collect(Collectors.groupingBy(Reply::getCommentId));
        //将回复和评论进行关联
        comments.forEach(comment -> {
            comment.setReplies(replyMap.get(comment.getId()));
        });
        return comments;
    }

    @Override
    public Page<Comment> selectPage(Page<Comment> page, EntityWrapper<Comment> wrapper) {
        //wrapper.eq("article_id", articleId);
        List<Comment> comments = commentMapper.selectPage(page, wrapper);
        page.setRecords(comments);
        return page;
    }

}
