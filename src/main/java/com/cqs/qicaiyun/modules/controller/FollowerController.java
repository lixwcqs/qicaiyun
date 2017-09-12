package com.cqs.qicaiyun.modules.controller;


import com.cqs.qicaiyun.modules.entity.Follower;
import com.cqs.qicaiyun.modules.service.FollowerService;
import com.sun.istack.internal.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 关注
 * Created by cqs on 2017/9/3.
 */
@RequestMapping("/follow")
@RestController
@Api(description = "关注/取消关注")
public class FollowerController {


    @Resource(name = "followerServiceImpl")
    private FollowerService service;

    /**
     * 关注作者
     *
     * @param fromUserId 关注者
     * @param toUserId   被关注者
     * @return
     */
    @ApiOperation(value = "关注",notes = "关注成功返回true,关注失败返回false.",consumes = "application/json",produces = "application/json")
    @PostMapping("/f/{fromUserId}/{toUserId}")
    public Boolean follow(@ApiParam(value = "关注者ID",required = true,readOnly = true) @NotNull @PathVariable final Long fromUserId,
                          @ApiParam(value = "被关注用户ID",required = true,readOnly = true) @PathVariable final Long toUserId) {
        return service.follow(fromUserId,toUserId);
    }

    /**
     * 取消关注
     *
     * @param fromUserId 取消关注着
     * @param toUserId   取消关注对象
     * @return
     */
    @ApiOperation(value = "取消关注",notes = "取消成功返回true,取消失败返回false.",consumes = "application/json",produces = "application/json")
    @DeleteMapping("/f/{fromUserId}/{toUserId}")
    public Boolean unfollow(@ApiParam(value = "关注者ID") @NotNull @PathVariable final Long fromUserId,
                            @ApiParam(value = "被关注用户ID") @NotNull @PathVariable final Long toUserId) {
        return service.unfollow(fromUserId,toUserId);
    }


    /**
     * serId关注了哪些用户
     * @param fromUserId
     * @return
     */
    @ApiOperation(value = "关注用户列表",consumes = "application/json",produces = "application/json")
    @GetMapping("/fs/{userId}")
    public List<Follower> followList(@ApiParam("关注用户ID")@NotNull @PathVariable("userId") final Long fromUserId){
        return service.followList(fromUserId);
    }

    /**
     * 哪些用户关注了serId
     * @param toUserId
     * @return
     */
    @ApiOperation(value = "被哪些用户关注了",consumes = "application/json",produces = "application/json")
    @GetMapping("/fds/{userId}")
    public List<Follower> followedList(@ApiParam(value = "被关注用户ID", required = true) @NotNull @PathVariable("userId") final Long toUserId){
        return service.followedList(toUserId);
    }

}
