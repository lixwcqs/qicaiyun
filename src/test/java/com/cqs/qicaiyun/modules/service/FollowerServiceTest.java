package com.cqs.qicaiyun.modules.service;

import com.cqs.configuration.BaseConfigurationTestNG;
import com.cqs.qicaiyun.mock.FollowerMock;
import com.cqs.qicaiyun.modules.entity.Follower;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cqs on 2017/9/1.
 */
public class FollowerServiceTest extends BaseConfigurationTestNG {

    @Resource(name = "followerServiceImpl")
    private FollowerService service;


    @Test
    public void testInsert() throws Exception {
        Follower follower = FollowerMock.newFollower();
        follower.setFromUserId(902680700132577281L);
        follower.setToUserId(902497813412225025L);
        service.insert(follower);
    }

    @Test
    public void testFollowedList() throws Exception {

        long toUserId = 902497813412225025L;
        List<Follower> followers = service.followedList(toUserId);
        if (followers.size() > 0) {
            System.out.println(followers.get(0));
        }
    }
}