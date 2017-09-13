package com.cqs.qicaiyun.mock;

import com.cqs.qicaiyun.modules.entity.Include;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
* Created by cqs on 2017-09-13T23:00:01.762
*/
public class IncludeMock {

    private static Random random = new Random();

    public static Include newInclude(){
        Include include = new Include();
        return include;
    }

    public static List<Include> newIncludes(){
        List <Include> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(newInclude());
        }
        return list;
    }
}