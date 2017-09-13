package com.cqs.qicaiyun.modules.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 专题收录表
 * @author 
 */
public class Include implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 主题ID
     */
    private Long topicId;

    /**
     * 收录文章ID
     */
    private Long articleId;

    /**
     * 收录时间
     */
    private Date cTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }
}