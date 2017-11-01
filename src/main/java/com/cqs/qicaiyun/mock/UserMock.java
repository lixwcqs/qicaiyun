
package com.cqs.qicaiyun.mock;

import com.cqs.qicaiyun.system.entity.SexType;
import com.cqs.qicaiyun.system.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by cqs on 2017/8/25.
 */
public class UserMock {

    private static Random random = new Random();

    public static User newUser() {
        User user = new User();
        user.setEmail("zhangsan@126.com");
        user.setIntroduction("哔哔哔");
        user.setNickname("七彩云");
        user.setPassword("1");
        user.setUTime(LocalDateTime.now());
        user.setCTime(LocalDateTime.now());
        user.setImage("http://cdn2.jianshu.io/assets/default_avatar/4-3397163ecdb3855a0a4139c34a695885.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/120/h/120");
        user.setSex(SexType.MALE);
        user.setSite("http://www.jianshu.com/u/b33f682bb96e");
        user.setQRCode("http://www.jianshu.com/u/b33f682bb96e");
        user.setTelephone("010-7655312");
        return user;
    }

    public static List<User> newUsers() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(newUser());
        }
        return list;
    }
}
