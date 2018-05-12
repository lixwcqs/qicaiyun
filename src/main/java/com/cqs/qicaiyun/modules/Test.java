package com.cqs.qicaiyun.modules;

/**
 * Created by cqs on 2018/5/6.
 */
public class Test {

    private Test(){}

    public static Test getInstance(){
        return Builder.test;
    }


    private static class Builder {

        private final static Test test = new Test();

    }
}
