package com.cqs.qicaiyun.modules.service;

import com.baomidou.mybatisplus.service.IService;

import com.cqs.qicaiyun.modules.entity.Follower;
import com.sun.istack.internal.NotNull;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
* Created by cqs on 2017-09-01T12:47:05.697.
*/
public interface FollowerService extends IService<Follower> {
    /**
     * toUserId关注了哪些用户
     * @param fromUserId
     * @return
     */
    List<Follower> followList(@NotNull final Long fromUserId);


    /**
     * 哪些用户关注了toUserId
     * @param toUserId
     * @return
     */

   List<Follower> followedList(@NotNull final Long toUserId);



    /**
     * 关注作者
     *
     * @param fromUserId 关注者
     * @param toUserId   被关注者
     * @return
     */
    Boolean follow(@NotNull final Long fromUserId, @NotNull final Long toUserId);

    /**
     * 取消关注
     *
     * @param fromUserId 取消关注着
     * @param toUserId   取消关注对象
     * @return
     */
    Boolean unfollow(@NotNull final Long fromUserId, @NotNull @PathVariable final Long toUserId);

}