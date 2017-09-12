package com.cqs.qicaiyun.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cqs.qicaiyun.system.entity.User;
import com.cqs.qicaiyun.modules.mapper.UserMapper;
import com.cqs.qicaiyun.system.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * Created by cqs on 2017/8/25.
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {}
