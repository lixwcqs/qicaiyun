package com.cqs.qicaiyun.common.result;

import lombok.Getter;

/**
 * Created by cqs on 2017/11/19.
 */
@Getter
//@Setter
public abstract class Result {

    //
    protected Status status;


    public static enum Status {

        FAILED, SUCCESS
    }

}
