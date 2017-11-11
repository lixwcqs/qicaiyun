package com.cqs.qicaiyun.conf;

import com.cqs.qicaiyun.common.PropertiesUtils;
import com.cqs.qicaiyun.services.HessianService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 *
 * Created by cqs on 2017/11/9.
 */
@Configuration
public class ContextConfig {

    //发布Hessian服务 -- client
    @Bean
    public HessianProxyFactoryBean getHessianProxyFactoryBean() {
        HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
        bean.setServiceUrl(PropertiesUtils.getProperties("hessianServiceUrl"));
        bean.setServiceInterface(HessianService.class);
        return bean;
    }

    @Resource(name = "hessianServiceImpl")
    private HessianService hessianService;

    @Bean(name = "/hsu")
    public HessianServiceExporter getHessianServiceExporter() {
        HessianServiceExporter bean = new HessianServiceExporter();
        Assert.notNull(hessianService, "hessianService实现类还没有初始化");
        bean.setService(hessianService);
        bean.setServiceInterface(HessianService.class);
        return bean;
    }
}
