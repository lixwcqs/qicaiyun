package com.cqs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 置于项目根目录下
 */
//The @SpringBootApplication annotation is equivalent to using @Configuration, @EnableAutoConfiguration
// and @ComponentScan with their default attributes:
@SpringBootApplication
//@EnableEurekaClient
//@EnableFeignClients
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


//    @Bean
//    public WebMvcConfigurerAdapter getConfig() {
//        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
//            @Override
//            public void addInterceptors(InterceptorRegistry registry) {
////                String[] excludes = {"/log*","/swagger-resources/**","/error"};
////                registry.addInterceptor(new AuthInterceptor()).excludePathPatterns(excludes);
//                registry.addInterceptor(new CrossDomainInterceptor());//跨域
//            }
//        };
//        return adapter;
//    }

//    @Bean
//    public FilterRegistrationBean configEncodingFilter() {
//        FilterRegistrationBean bean = new FilterRegistrationBean();
//        bean.setFilter(new CharacterEncodingFilter());
//        bean.addInitParameter("encoding", "UTF-8");
//        bean.addUrlPatterns("/*");
//        bean.setName("characterEncodingFilter");
//        bean.setOrder(1);
//        return bean;
//    }
//
//    //配置认证过滤器
////    @Bean
//    @Deprecated
//    public FilterRegistrationBean configAuthFilter() {
//        FilterRegistrationBean bean = new FilterRegistrationBean();
//        bean.setName("af");
//        bean.setOrder(2);
//        bean.setFilter(new AuthFilter());
//        bean.addUrlPatterns("/*");
//        bean.addInitParameter("excludes", "/,/log*,/resources/**,/WEB-INF/**");
//        return bean;
//    }

//    public static void main(String[] args) {
////        SpringApplication.run(Application.class, args);
//        System.setProperty("spring.devtools.restart.enabled", "false");//不自动重启
//        //System.setProperty("server.jsp-servlet.init-parameters.development", "true");//不自动重启
//        SpringApplication app = new SpringApplication(Application.class);
//        app.setBannerMode(Banner.Mode.OFF);
//        app.run(args);
//    }


}
