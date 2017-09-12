package com.cqs.qicaiyun.mock;

import com.cqs.qicaiyun.modules.entity.Comment;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Created by cqs on 2017/8/27.
 */
public class CommentMock {
    private static Random random = new Random();

    public static Comment mockComment(){
        Comment comment = new Comment();
        comment.setArticleId(123L);
        comment.setDown(random.nextInt(10));
        comment.setUp(random.nextInt(1000));
        comment.setContent("HHHH");
        comment.setCTime(LocalDateTime.now());
        return comment;
    }
}
