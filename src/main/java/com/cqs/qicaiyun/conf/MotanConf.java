package com.cqs.qicaiyun.conf;

import com.weibo.api.motan.config.springsupport.AnnotationBean;
import com.weibo.api.motan.config.springsupport.BasicServiceConfigBean;
import com.weibo.api.motan.config.springsupport.ProtocolConfigBean;
import com.weibo.api.motan.config.springsupport.RegistryConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by cqs on 2018/4/2.
 */
@Configuration
public class MotanConf {

    @Bean
    public static AnnotationBean motanAnnotationBean() {
        AnnotationBean bean = new AnnotationBean();
        bean.setPackage("com.cqs.qicaiyun.modules.rpc.motan.impl");
        return bean;
    }


    @Bean(name = "motan")
    public ProtocolConfigBean protocolConfig1() {
        ProtocolConfigBean config = new ProtocolConfigBean();
        config.setDefault(true);
        config.setName("motan");
        config.setMaxContentLength(1048576);
        return config;
    }

    @Bean(name = "registry")
    public RegistryConfigBean registryConfig() {
        RegistryConfigBean config = new RegistryConfigBean();
        config.setRegProtocol("local");
//        config.setAddress("127.0.0.1:8500");
        return config;
    }

    @Bean
    public BasicServiceConfigBean baseServiceConfig() {
        BasicServiceConfigBean config = new BasicServiceConfigBean();
        config.setExport("motan:8002");
        config.setRegistry("registry");
        return config;
    }
}

