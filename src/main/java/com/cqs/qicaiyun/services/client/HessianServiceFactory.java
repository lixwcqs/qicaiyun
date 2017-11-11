package com.cqs.qicaiyun.services.client;

import com.baomidou.mybatisplus.plugins.Page;
import com.caucho.hessian.client.HessianProxyFactory;
import com.cqs.qicaiyun.common.PropertiesUtils;
import com.cqs.qicaiyun.services.HessianService;
import com.cqs.qicaiyun.services.HessianServiceImpl;
import com.cqs.qicaiyun.system.entity.User;

import java.net.MalformedURLException;

/**
 *
 * d
 * Created by cqs on 2017/11/9.
 */
public class HessianServiceFactory {

    private static HessianService hessian = HessianHelper.hessian;


    public static HessianService getInstance(){
        return hessian;
    }


    private static class HessianHelper{

        private static HessianService hessian = initHessianService();

        private static HessianService initHessianService(){
            HessianProxyFactory proxyFactory = new HessianProxyFactory();
            try {
                return (HessianService) proxyFactory.create(HessianService.class,
                        PropertiesUtils.getProperties("hessianServiceUrl"));
            } catch (MalformedURLException e) {
                return new HessianServiceImpl();
            }
        }
    }

    public static void main(String[] args) {
        Page<User> page = new Page<>(1,3);
//        Result<List<User>> users = HessianServiceFactory.getInstance().getUsers(page, null);
        System.out.println(hessian.getUsers3());
        System.out.println(hessian.getUsers2(page,null));
    }
}
