package com.cqs.qicaiyun.modules.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 回复表
 * @author 
 */
@Setter @Getter @ToString
public class Reply implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 评论Id
     */
    private Long commentId;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 回复人
     */
    private Long fromUserId;

    /**
     * 回复对象
     */
    private Long toUserId;

    /**
     * 回复时间
     */
    private Date cTime;

    private static final long serialVersionUID = 1L;

}