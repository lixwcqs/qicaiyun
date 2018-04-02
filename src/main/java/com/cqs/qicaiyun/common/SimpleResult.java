package com.cqs.qicaiyun.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 返回请求数据的标准格式
 * Created by cqs on 2017/11/12.
 */
@ToString
@Getter
@Setter
@Deprecated
public class SimpleResult<T> implements Serializable {

    private final static long serialVersionUID = 1L;
    private boolean status;
    private String message;
    private T data;

    public SimpleResult<T> status(boolean status) {
        this.status = status;
        return this;
    }

    public SimpleResult<T> data(T data) {
        this.data = data;
        return this;
    }

    public SimpleResult<T> message(String message) {
        this.message = message;
        return this;
    }

}
