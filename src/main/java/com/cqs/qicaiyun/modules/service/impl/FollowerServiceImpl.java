package com.cqs.qicaiyun.modules.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cqs.qicaiyun.modules.entity.Follower;
import com.cqs.qicaiyun.modules.mapper.FollowerMapper;
import com.cqs.qicaiyun.modules.service.FollowerService;
import com.sun.istack.internal.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by cqs on 2017-09-01T12:49:50.858.
 */
@Service
public class FollowerServiceImpl extends ServiceImpl<FollowerMapper, Follower> implements FollowerService {


    /**
     * 关注
     *
     * @param follower
     * @return
     */
    @Override
    public Boolean follow(Follower follower) {
        return insert(follower);
    }


    /**
     * 取消关注
     *
     * @param follower
     * @return
     */
    @Override
    public Boolean unfollow(Follower follower) {
        return baseMapper.unfollow(follower);
    }

    /**
     * toUserId关注了哪些用户
     *
     * @param fromId
     * @return
     */
    public List<Follower> followList(@NotNull final Long fromId) {
        return selectList(new Wrapper<Follower>() {
            @Override
            public String getSqlSegment() {
                return "WHERE from_user_id = " + fromId;
            }
        });
    }


    /**
     * 哪些用户关注了toUserId
     *
     * @param toId
     * @return
     */
    public List<Follower> followedList(@NotNull final Long toId) {
        return selectList(new Wrapper<Follower>() {
            @Override
            public String getSqlSegment() {
                return "WHERE to_id = " + toId;
            }
        });
    }


    public Boolean unfollow(@NotNull  final Long fromUserId, @NotNull  final Long toId) {
        throw new RuntimeException("废弃方法");
    }

    public Boolean follow(@NotNull @PathVariable final Long fromUserId, @NotNull @PathVariable final Long toId) {
        throw new RuntimeException("废弃方法");
    }
}
