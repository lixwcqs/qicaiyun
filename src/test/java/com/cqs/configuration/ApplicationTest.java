package com.cqs.configuration;

import com.cqs.qicaiyun.modules.service.ArticleService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * Created by cqs on 2017/11/13.
 */
@SpringBootApplication(scanBasePackageClasses = com.cqs.qicaiyun.modules.controller.ArticleController.class)
public class ApplicationTest {


    @MockBean(name = "contentServiceImpl")
    private ArticleService service;

}
