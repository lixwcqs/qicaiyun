package com.cqs.configuration;

import com.cqs.qicaiyun.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by cqs on 2017/9/9.
 */
//@RunWith(SpringTest.class)
//@SpringBootTest(classes = MockServletContext.class)
@SpringBootTest(classes = {MockServletContext.class, Application.class})
@WebAppConfiguration
public class BaseConfigurationTestNG extends AbstractTestNGSpringContextTests {}
