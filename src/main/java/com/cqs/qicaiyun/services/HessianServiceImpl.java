package com.cqs.qicaiyun.services;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cqs.qicaiyun.common.Result;
import com.cqs.qicaiyun.system.entity.User;
import com.cqs.qicaiyun.system.service.IUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *  返回结果格式
 * Created by cqs on 2017/11/9.
 */
@Component
@Log4j2
public class HessianServiceImpl implements HessianService {

    @Resource(name = "userService")
    private IUserService userService;

    @Override
    public List<User> getUsers2(Page<User> page, Wrapper<User> wrapper) {
        return userService.selectPage(page, wrapper).getRecords();
    }

    @Override
    public User getUsers3() {
        User user = new User();
        user.setAccount("1");
        user.setPassword("2");
        user.setCTime(LocalDateTime.now());
        return user;
    }

    @Override
    public Result<String> test() {

        List<String> ss = new ArrayList<>();
        ss.add("1");
        ss.add("2");
        ss.add("3");
        Result<String> result = new Result<String>().data(ss);
        return result;
    }
}
