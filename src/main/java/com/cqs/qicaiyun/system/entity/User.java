package com.cqs.qicaiyun.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by cqs on 2017/7/31.
 */


@Getter
@Setter
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

//    @TableId(type = IdType.AUTO)
    private Long id;
    private String account;//账号 -- 不可修改  全局唯一
    private String name;
    private String nickname;
    private String password;


    private SexType sex;
    private String email;
    private String introduction;
    private String telephone;

    @TableField(value = "QR_code")
    private String QRCode;//二维码
    private String image;//touxiang
    private String site;//

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private LocalDateTime cTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private LocalDateTime uTime;
}
