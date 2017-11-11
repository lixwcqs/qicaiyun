package com.cqs.qicaiyun.services.client;

import com.baomidou.mybatisplus.plugins.Page;
import com.cqs.qicaiyun.common.Result;
import com.cqs.qicaiyun.conf.Application;
import com.cqs.qicaiyun.system.entity.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by cqs on 2017/11/10.
 */
@SpringBootTest(classes = Application.class)
public class HessianHelperTest extends AbstractTestNGSpringContextTests{


    @Test
    public void testHessian() throws Exception {
        Page<User> page = new Page<>(1,3);
        Result<List<User>> users = HessianServiceFactory.getInstance().getUsers(page, null);
        System.out.println(users);
    }
}