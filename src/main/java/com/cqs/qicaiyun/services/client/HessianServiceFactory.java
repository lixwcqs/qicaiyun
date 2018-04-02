package com.cqs.qicaiyun.services.client;

import com.caucho.hessian.client.HessianProxyFactory;
import com.cqs.qicaiyun.common.PropertiesUtils;
import com.cqs.qicaiyun.services.HessianService;
import com.cqs.qicaiyun.services.HessianServiceImpl;

import java.net.MalformedURLException;

/**
 * Holder模式实现单利
 * 懒加载 + 线程安全
 * Created by cqs on 2017/11/9.
 */
public class HessianServiceFactory {

    private static HessianService hessian = Holder.hessian;

    public static HessianService getInstance() {
        return hessian;
    }

    private static class Holder {

        private final static HessianService hessian = initHessianService();

        private static HessianService initHessianService() {
            HessianProxyFactory proxyFactory = new HessianProxyFactory();
            proxyFactory.setOverloadEnabled(true);
            try {
                return (HessianService) proxyFactory.create(HessianService.class,
                        PropertiesUtils.getProperties("hessianServiceUrl"));
            } catch (MalformedURLException e) {
                return new HessianServiceImpl();
            }
        }
    }
}
