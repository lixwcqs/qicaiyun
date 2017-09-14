package com.cqs.qicaiyun.system.service;

import com.cqs.configuration.BaseConfigurationTestNG;
import com.cqs.qicaiyun.mock.UserMock;
import com.cqs.qicaiyun.system.entity.User;
import org.testng.annotations.Test;

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
}