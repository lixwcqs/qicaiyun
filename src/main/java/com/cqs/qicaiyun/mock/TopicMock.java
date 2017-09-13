package com.cqs.qicaiyun.mock;

import com.cqs.qicaiyun.modules.entity.Topic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
* Created by cqs on 2017-09-13T22:58:26.503
*/
public class TopicMock {

    private static Random random = new Random();

    public static Topic newTopic(){
        Topic topic = new Topic();
        topic.setAnnouncement("手绘、漫画爱好者分享交流学习的地方 (≧∇≦)ﾉ ，无论高手还是菜鸟都大大地欢迎！ \n" +
                "想成为简书网红？来参加绘画游戏：http://www.jianshu.com/p/579b7c69dd09\n" +
                "\n" +
                "投稿须知：欢迎大家来投递以下类型的稿件：1、原创手绘作品2、原创漫画3、绘画教程绘画工具没有限制，手绘、板绘、鼠绘都Ok，用大脚趾画画我也拦不住你啊！\n" +
                "\n" +
                "㊙️ 微信添加主编顾釉止微信【stay55555】，备注“漫画”，即可进入简书官方漫画手绘专题\n" +
                "\n" +
                "关注公众号“简宝玉”（jianshubaoyu），进入简书丰富多彩的专题社群！");
        topic.setImg("http://upload.jianshu.io/collections/images/283250/%E6%BC%AB%E7%94%BB%E4%B8%93%E9%A2%98.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240");
        topic.setTopic("漫画·手绘");
        return topic;
    }

    public static List<Topic> newTopics(){
        List <Topic> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(newTopic());
        }
        return list;
    }
}