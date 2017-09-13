package com.cqs.qicaiyun.modules.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 专题
 * @author 
 */
@Setter @Getter @ToString
public class Topic implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 专题名
     */
    private String topic;

    /**
     * 图片URL
     */
    private String img;

    /**
     * 公告
     */
    private String announcement;

}