package com.cqs.qicaiyun.modules.controller;

import com.cqs.configuration.BaseConfigurationTestNG;
import com.cqs.qicaiyun.mock.FollowerMock;
import com.cqs.qicaiyun.modules.entity.Follower;
import com.cqs.qicaiyun.modules.service.FollowerService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


public class MyTests extends BaseConfigurationTestNG {

    @TestConfiguration
    static class Conf {
        @MockBean
        FollowerService service;
    }

    @Autowired
    private FollowerService service;


    @Resource(name = "followerController")
    FollowerController controller;



    @Test
    public void exampleTest() {
        Follower follower = FollowerMock.newFollower();
        given(service.follow(follower)).willReturn(true);
        Boolean stat = controller.follow(follower.getFromUserId(), follower.getToId());
        assertThat(stat).isEqualTo(true);
    }
}