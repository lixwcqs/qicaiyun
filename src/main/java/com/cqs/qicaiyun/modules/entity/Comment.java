package com.cqs.qicaiyun.modules.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论表
 *
 * @author
 */
@Getter
@Setter
@ToString
public class Comment implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 文章Id
     */
    private Long articleId;

    /**
     * 评论
     */
    private String content;

    /**
     * 评论人
     */
    private Long userId;

    /**
     * 赞
     */
    private Integer up;

    /**
     * 踩
     */
    private Integer down;

    /**
     * 评论时间
     */
    private LocalDateTime cTime;

    @TableField(exist = false)
    private List<Reply> replies;

    private static final long serialVersionUID = 1L;
}