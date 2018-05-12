package com.cqs.qicaiyun.modules.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 
 */
@ToString
@Getter @Setter
public class Advice implements Serializable {
    private Integer id;

    private Integer ip;

    private Integer app;

    private Long advice;

    private Integer os;

    private Integer channel;

    private Date clickTime;

    private Date attributedTime;

    private Byte isAttributed;

    private LocalDateTime cTime;

    private static final long serialVersionUID = 1L;
}