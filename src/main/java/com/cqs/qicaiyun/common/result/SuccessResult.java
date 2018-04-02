package com.cqs.qicaiyun.common.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by cqs on 2017/11/19.
 */
@Getter
@Setter
@ToString
public class SuccessResult<T extends Serializable> extends Result implements Serializable {

    private static long serialVersionUID = 1L;

    private T data;

    private  SuccessResult() {
        status = Status.SUCCESS;
    }


    @SuppressWarnings("unchecked")
    public static <T extends Serializable> SuccessResult<T> build() {
        return new SuccessResult();
    }

    public SuccessResult<T> result(T data) {
        this.data = data;
        return this;
    }

    public SuccessResult<T> data(T data) {
        this.data = data;
        return this;
    }


}
