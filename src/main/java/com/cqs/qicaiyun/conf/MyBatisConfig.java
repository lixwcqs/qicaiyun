package com.cqs.qicaiyun.conf;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.cqs.qicaiyun.common.MyMetaObjectHandler;
import com.cqs.qicaiyun.modules.helper.FollowerType;
import com.cqs.qicaiyun.system.entity.SexType;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.math.RoundingMode;


/**
 * 结合MybatisPlusAutoConfiguration配置
 */
@Configuration    //该注解类似于spring配置文件
@MapperScan(basePackages = {"com.cqs.qicaiyun.*.mapper"})
@Log4j2
public class MyBatisConfig {

    public MyBatisConfig() {
        System.out.println();
    }

    /**
     * @param ds 我也不知道哪里生成了DataSource 反正他就是生成了
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception {
        /**
         * SqlSessionFactoryBean 对应于原生的mybatis
         * MybatisSqlSessionFactoryBean 对应于mybatis-plus
         */
        MybatisSqlSessionFactoryBean fb = new MybatisSqlSessionFactoryBean();
        if (ds instanceof DruidDataSource){
            DruidDataSource dds = (DruidDataSource) ds;
            dds.setMaxActive(50);
            dds.setMinIdle(4);
            dds.addFilters("stat");
        }
        fb.setDataSource(ds);//指定数据源(这个必须有，否则报错)
        //下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        fb.setTypeAliasesPackage("com.cqs.qicaiyun.modules.entity");//指定基包
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        fb.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));//指定xml文件位置
        fb.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));//指定xml文件位置
        fb.setGlobalConfig(getGlobalConfiguration());
        SqlSessionFactory sqlSessionFactory =  fb.getObject();
        //设置枚举类型--处理器
//        sqlSessionFactory.getConfiguration().getInterceptors().add(new PerformanceInterceptor());
        TypeHandlerRegistry registry = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
        registry.register(FollowerType.class,new EnumOrdinalTypeHandler<>(FollowerType.class));
        registry.register(SexType.class,new EnumOrdinalTypeHandler<>(SexType.class));
        registry.register(RoundingMode.class,new EnumOrdinalTypeHandler<>(RoundingMode.class));
        //------------------------
        return sqlSessionFactory;
    }


    //设置全局配置
    private GlobalConfiguration getGlobalConfiguration() {
        GlobalConfiguration gc = new GlobalConfiguration();
        gc.setIdType(IdType.AUTO.ordinal());
        gc.setDbColumnUnderline(true);
        gc.setRefresh(true);
        gc.setFieldStrategy(FieldStrategy.NOT_EMPTY.getKey());
        gc.setMetaObjectHandler( new MyMetaObjectHandler());
        return gc;
    }

}