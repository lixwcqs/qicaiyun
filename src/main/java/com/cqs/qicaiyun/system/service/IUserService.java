package com.cqs.qicaiyun.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.cqs.qicaiyun.system.entity.User;

import java.io.Serializable;


/**
 * Created by cqs on 2017/8/25.
 */
public interface IUserService extends IService<User> {

    @Override
    User selectById(Serializable id);
}
