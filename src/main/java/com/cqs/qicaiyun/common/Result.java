package com.cqs.qicaiyun.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 *  返回请求数据的标准格式
 * @param <T>
 */
@Getter
@Setter
@ToString @Deprecated
public final class Result<T>  implements Serializable {
    private final static long serialVersionUID = 1L;

    //private int code;        // 状态码，一般是当 success 为 true 或者 false 时不足够表达时可使用

    private int page;//当前页码
    private int pages;//一个多少页
    private int total;//所有的记录数

    private List<T> data;

    private boolean status;
    private String message;

    public Result<T> status(boolean status){
        this.status = status;
        return this;
    }

    public Result<T> data(List<T> data){
        this.data = data;
        return this;
    }

    public Result<T> message(String message){
        this.message = message;
        return this;
    }


}