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
public class FailedResult extends Result implements Serializable {

    private static long serialVersionUID = 1L;

    private String reason;

    private FailedResult() {
        status = Status.FAILED;
    }


    public static FailedResult build() {
        return new FailedResult();
    }

    public FailedResult reason(String reason) {
        this.reason = reason;
        return this;
    }


}
