package com.cqs.qicaiyun.modules.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Created by cqs on 2017/8/20.
 */
@ToString @Getter @Setter
public class Article {

    private Long id;

    private String title;

    private String author;

    private Long contentId;

    @TableField(exist = false)
    private String content;

    private Integer up;

    private Integer reading;

    private Byte category;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cTime;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uTime;
}
