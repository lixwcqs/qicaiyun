package com.cqs.qicaiyun.modules.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *  文章内容表
 * Created by cqs on 2017/8/20.
 */
@ToString
@Getter
@Setter
@TableName("article_content")
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String content;
}
