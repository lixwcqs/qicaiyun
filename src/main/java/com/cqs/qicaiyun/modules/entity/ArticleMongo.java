package com.cqs.qicaiyun.modules.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

/**
 * 使用Mongo存储文章内容
 * @author lixiaowen
 * @create 18-12-1
 */
@Data
@Document(collection = "article")
public class ArticleMongo {

    @Id
    private String id;

    /**
     * 内容
     */
    private String content;

}
