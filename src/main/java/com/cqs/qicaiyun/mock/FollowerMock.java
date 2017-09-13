package com.cqs.qicaiyun.mock;

import com.cqs.qicaiyun.modules.entity.Follower;
import com.cqs.qicaiyun.modules.helper.FollowerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
* Created by cqs on 2017-08-31T00:39:04.500
*/
public class FollowerMock {

    private static Random random = new Random();

    public static Follower newFollower(){
        Follower follower = new Follower();
        follower.setFromUserId(902680700132577281L);
        follower.setToId(902497813412225025L);
        follower.setType(FollowerType.USER);
        return follower;
    }

    public static List<Follower> newFollowers(){
        List <Follower> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(newFollower());
        }
        return list;
    }
}