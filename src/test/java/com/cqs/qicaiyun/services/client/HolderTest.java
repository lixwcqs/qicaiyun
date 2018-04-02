package com.cqs.qicaiyun.services.client;

import com.baomidou.mybatisplus.plugins.Page;
import com.cqs.Application;
import com.cqs.qicaiyun.common.Result;
import com.cqs.qicaiyun.services.HessianService;
import com.cqs.qicaiyun.system.entity.User;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by cqs on 2017/11/10.
 */
@SpringBootTest(classes = Application.class)
public class HolderTest {

//
//    @Resource(name = "hessianServiceClient")
//    private HessianService service;



    @Test
    public void testHessian() throws Exception {
        Page<User> page = new Page<>(1,3);
        HessianService instance = HessianServiceFactory.getInstance();
//        System.out.println(instance.getUsers(page, null));
//        System.out.println(instance.getUsers2(page, null));
        Result result = instance.test();
        System.out.println(instance.test());
//        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss:mm")));
//        System.out.println(UserMock.newUser());
//        System.out.println(instance.getUsers3());
//        System.out.println(new Date());
    }

//    @Test
//    public void testHessian2() throws Exception {
//        User users3 = service.getUsers3();
//        System.out.println("返回结果:"+users3);
//    }
}