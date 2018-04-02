package com.cqs.qicaiyun.conf;

import com.cqs.qicaiyun.services.HessianService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.util.Assert;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.Resource;

/**
 *
 * Created by cqs on 2017/11/9.
 */
@Configuration
@PropertySource("classpath:config.properties")
public class ContextConfig {


    @Value("${hessianServiceUrl}")
    private String hessianServiceUrl;

    //发布Hessian服务 -- client
    @Bean
    public HessianProxyFactoryBean getHessianProxyFactoryBean() {
        HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
        bean.setServiceUrl(hessianServiceUrl);
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

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
