package com.cqs.configuration;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by cqs on 2017/9/17.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ComponentScan(value = "com.cqs", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {
                Controller.class, EnableSwagger2.class, SpringBootApplication.class
        })
})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestBaseServiceConf {
}
