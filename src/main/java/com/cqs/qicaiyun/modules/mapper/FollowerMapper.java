package com.cqs.qicaiyun.modules.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cqs.qicaiyun.modules.entity.Follower;
import org.apache.ibatis.annotations.Delete;

/**
* Created by cqs on 2017-09-01T12:47:05.694.
*/
public interface FollowerMapper extends BaseMapper<Follower> {

    //定义取消订阅的
    @Delete("DELETE FROM follower WHERE from_User_Id = #{fromUserId} AND to_id=#{toId} AND type = #{type}")
    Boolean unfollow(Follower follower);
}
