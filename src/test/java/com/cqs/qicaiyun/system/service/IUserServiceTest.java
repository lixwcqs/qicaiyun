package com.cqs.qicaiyun.system.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cqs.configuration.BaseConfigurationTestNG;
import com.cqs.qicaiyun.mock.UserMock;
import com.cqs.qicaiyun.system.entity.User;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by cqs on 2017/9/14.
 */
public class IUserServiceTest extends BaseConfigurationTestNG {

    @Resource
    private IUserService service;


    @Test
    public void testInsert() throws Exception {
        User user = UserMock.newUser();
        service.insert(user);
    }


    @Test
    public void testFind() throws Exception {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        String account = "li";
        String password = "1";
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        wrapper.setEntity(user);
        wrapper.eq("account",account);
        User o = service.selectOne(wrapper);

        System.out.println(o);

    }
}