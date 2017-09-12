package com.cqs.qicaiyun.mock;

import com.cqs.qicaiyun.modules.entity.Content;

/**
 * Created by cqs on 2017/8/20.
 */
public class ContentMock {
    public static Content mockContent(){
        Content content = new Content();
        content.setContent("Hello");
        return content;
    }
}
