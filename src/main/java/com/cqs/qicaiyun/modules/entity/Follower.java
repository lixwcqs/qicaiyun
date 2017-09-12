package com.cqs.qicaiyun.modules.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 关注表
 * @author 
 */
@ToString @Getter @Setter
public class Follower implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 关注者
     */
    private Long fromUserId;

    /**
     * 被关注用户
     */
    private Long toUserId;

    /**
     * 回复时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cTime;

    private static final long serialVersionUID = 1L;
}