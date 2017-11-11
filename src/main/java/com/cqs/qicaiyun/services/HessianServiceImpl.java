package com.cqs.qicaiyun.services;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cqs.qicaiyun.common.Result;
import com.cqs.qicaiyun.system.entity.User;
import com.cqs.qicaiyun.system.service.IUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cqs on 2017/11/9.
 */
@Component
@Log4j2
public class HessianServiceImpl implements HessianService {

    @Resource(name = "userService")
    private IUserService userService;

    @Override
    public Result<List<User>> getUsers(Page<User> page, Wrapper<User> wrapper) {
        @SuppressWarnings("unchecked")
        Result<List<User>> userResult = Result.ok();
        try {
            userResult.data(userService.selectPage(page, wrapper).getRecords());
        } catch (Exception e) {
            log.error("查询用户信息异常",e);
            userResult.success(false);
            userResult.message(e.getMessage());
        }
        return userResult;
    }

    @Override
    public List<User> getUsers2(Page<User> page, Wrapper<User> wrapper) {
        return userService.selectPage(page, wrapper).getRecords();
    }

    @Override
    public User getUsers3() {
        User user = new User();
        user.setAccount("1");
        user.setPassword("2");
        return user;
    }
}
