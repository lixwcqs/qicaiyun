package com.cqs.qicaiyun.conf;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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

    @Autowired
    private Environment env;



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
        fb.setDataSource(ds);//指定数据源(这个必须有，否则报错)
        /**
         * 设置处理器 ---------- 没起作用 神马情况
        fb.setTypeHandlers(new TypeHandler[]{
                new EnumOrdinalTypeHandler<>(FollowerType.class),
                new EnumOrdinalTypeHandler<>(SexType.class),
                new EnumOrdinalTypeHandler<>(RoundingMode.class)
        });
         ***/
        //下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        fb.setTypeAliasesPackage(env.getProperty("mybatis-plus.typeAliasesPackage"));//指定基包
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis-plus.mapper-locations")));//指定xml文件位置
        fb.setGlobalConfig(getGlobalConfiguration());
        SqlSessionFactory sqlSessionFactory =  fb.getObject();
        //设置枚举类型--处理器
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