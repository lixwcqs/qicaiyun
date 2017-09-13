package com.cqs.qicaiyun.modules.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cqs.qicaiyun.modules.entity.Follower;
import com.cqs.qicaiyun.modules.mapper.FollowerMapper;
import com.cqs.qicaiyun.modules.service.FollowerService;
import com.sun.istack.internal.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
* Created by cqs on 2017-09-01T12:49:50.858.
*/
@Service
public class FollowerServiceImpl extends ServiceImpl<FollowerMapper, Follower> implements FollowerService {


    /**
     * 关注作者
     *
     * @param fromUserId 关注者
     * @param toUserId   被关注者
     * @return
     */
    @PostMapping("/f/{fromUserId}/{toId}")
    public Boolean follow(@NotNull @PathVariable final Long fromUserId, @NotNull @PathVariable final Long toUserId) {
        Follower follower = new Follower();
        follower.setFromUserId(fromUserId);
        follower.setToId(toUserId);
//        follower.setCTime(LocalDateTime.now());//数据库自动插入
        return insert(follower);
    }

    /**
     * 取消关注
     *
     * @param fromUserId 取消关注着
     * @param toUserId   取消关注对象
     * @return
     */
    @DeleteMapping("/f/{fromUserId}/{toId}")
    public Boolean unfollow(@NotNull @PathVariable final Long fromUserId, @NotNull @PathVariable final Long toUserId) {
        return delete(new Wrapper<Follower>() {
            @Override
            public String getSqlSegment() {
                return String.format("WHERE from_user_id = %d AND to_user_id = %d ", fromUserId, toUserId);
            }
        });
    }

    /**
     * toUserId关注了哪些用户
     * @param fromUserId
     * @return
     */
    @GetMapping("/fs/{userId}")
    public List<Follower> followList(@NotNull @PathVariable("userId") final Long fromUserId){
        return selectList(new Wrapper<Follower>() {
            @Override
            public String getSqlSegment() {
                return "WHERE from_user_id = " + fromUserId;
            }
        });
    }




    /**
     * 哪些用户关注了toUserId
     * @param toUserId
     * @return
     */
    @GetMapping("/fds/{userId}")
    public List<Follower> followedList(@NotNull @PathVariable("userId") final Long toUserId){
        return selectList(new Wrapper<Follower>() {
            @Override
            public String getSqlSegment() {
                return "WHERE to_user_id = " + toUserId;
            }
        });
    }
}
