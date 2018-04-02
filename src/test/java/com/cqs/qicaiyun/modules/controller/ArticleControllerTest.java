package com.cqs.qicaiyun.modules.controller;

import com.cqs.configuration.ApplicationTest;
import com.cqs.qicaiyun.modules.service.ArticleService;
import com.cqs.qicaiyun.modules.service.impl.ArticleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Created by cqs on 2017/11/13.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ArticleController.class)
@ContextConfiguration(classes = ApplicationTest.class)
public class ArticleControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean(name = "contentServiceImpl",value = ArticleServiceImpl.class)
    private ArticleService service;

    @Test
    public void testExample() throws Exception {
        given(service.selectList(null)).willReturn(new ArrayList<>());
        this.mvc.perform(get("/article/list/20/3").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().
                string("Honda Civic"));
    }

    @Test
    public void publish() throws Exception {
        System.out.println(mvc);
        System.out.println(111);
    }

    @Test
    public void updateArticle() throws Exception {
    }

    @Test
    public void deleteArticle() throws Exception {
    }

    @Test
    public void findById() throws Exception {
    }

    @Test
    public void list() throws Exception {
    }

}