package com.cqs.qicaiyun.common.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cqs on 2017/11/19.
 */
@Getter
@Setter @ToString
public class PageData<T extends Serializable> implements Serializable {
    private static long serialVersionUID = 1L;

    private int page;//当前页码
    private long pages;//一个多少页
    private long total;//所有的记录数

    private List<T> list;

//    public SuccessResult<T> list(List<T> list) {
//        this.list = list;
//        return SuccessResult;
//    }

}
