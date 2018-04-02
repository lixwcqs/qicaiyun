#Hessian发布服务及客户端调用的简单总结
##服务端
- 声明接口 并 完成实现类
  见:HessianService和HessianServiceImpl
- 发布服务：
  
  在spring容器中声明HessianServiceExporter实例，如
    ``` 
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
     ```
 注意发布的路径名称为项目工程上下文+HessianServiceExporter的名称， 比如web工程上下文为http://localhost:9090/qicaiyun,
 那么上面HessianService的服务路径就是：http://localhost:9090/qicaiyun/hsu

----------------------------------------------------------------------------------

##客户端
### 实现基于HessianService接口的代理
- 普通实现
```
public  HessianService initHessianService() {
    HessianProxyFactory proxyFactory = new HessianProxyFactory();
    try {
        return (HessianService) proxyFactory.create(HessianService.class,
                "http://192.168.2.145:9090/qicaiyun/hsu");
    } catch (MalformedURLException e) {
        return new HessianServiceImpl();
    }
}

```
细节参考HessianServiceFactory

- spring实现：实例化HessianFactoryBean

基于spring的实现好处是：要是请求服务有多个的话 我们可以借助Spring AOP技术实现在线程中完成服务请求，参考HessianServiceAspect
   
```
@Bean(name = "hessianServiceClient")
public HessianProxyFactoryBean getHessianProxyFactoryBean() {
   HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
   bean.setServiceUrl("http://192.168.2.145:9090/qicaiyun/hsu");
   bean.setServiceInterface(HessianService.class);
   return bean;
}
```
请求也很简单
```
//Intellij检查会can not autowire错误 无视之
@Resource(name = "hessianServiceClient")
private HessianService service;

@Test
public void testHessian() throws Exception {
    User users3 = HessianServiceFactory.getInstance().getUsers3();
    System.out.println("返回结果:"+users3);
}
@Test
public void testHessian2() throws Exception {
    User users3 = service.getUsers3();
    System.out.println("返回结果:"+users3);
}
```

