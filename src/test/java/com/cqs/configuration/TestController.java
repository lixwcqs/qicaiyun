package com.cqs.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;



/**
 * 基本的控制层Controller
 * Created by cqs on 2017/9/15.
 */
public class TestController extends BaseConfigurationTestNG {

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    @BeforeClass
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
}
