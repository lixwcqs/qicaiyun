package com.cqs.configuration;

import com.cqs.tool.rest.RestTemplateDemo;
import org.springframework.web.client.RestTemplate;

/**
 * 基本的控制层Controller
 * Created by cqs on 2017/9/15.
 */
public class TestSimpleController {
    protected RestTemplate template = RestTemplateDemo.createRestTemplate();
}
