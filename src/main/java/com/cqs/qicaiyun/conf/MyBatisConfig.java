package com.cqs.qicaiyun.conf;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;


/**
 * 结合MybatisPlusAutoConfiguration配置
 */
@Configuration    //该注解类似于spring配置文件
@MapperScan(basePackages = {"com.cqs.qicaiyun.*.mapper"})
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
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);//指定数据源(这个必须有，否则报错)
        //下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        fb.setTypeAliasesPackage(env.getProperty("mybatis-plus.typeAliasesPackage"));//指定基包
        String sss = env.getProperty("mybatis-plus.mapper-locations");
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis-plus.mapper-locations")));//指定xml文件位置
        return fb.getObject();
    }

}