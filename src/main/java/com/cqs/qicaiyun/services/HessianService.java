package com.cqs.qicaiyun.services;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cqs.qicaiyun.common.Result;
import com.cqs.qicaiyun.system.entity.User;

import java.util.List;

/**
 * Created by cqs on 2017/11/9.
 */
public interface HessianService {

    List<User> getUsers2(Page<User> page, Wrapper<User> wrapper);
    User getUsers3();

    Result<String> test();

}
