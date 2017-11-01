package com.cqs.qicaiyun.modules.controller;

import com.cqs.configuration.TestController;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by cqs on 2017/9/15.
 */
@Log4j2
public class TopicControllerTest extends TestController {



    @Test
    public void testRecommendTopics() throws Exception {
        mockMvc.perform(get("/topic/hot/list"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testHotTopics() throws Exception {

    }

    @Test
    public void testHotArticlesTopic() throws Exception {
        //
    }

    @Test
    public void testCommentArticlesTopic() throws Exception {
    }

    @Test
    public void testIncludeArticlesTopic() throws Exception {
    }



    @DataProvider(name = "ids")
    public Object[][] ids() {
        return new Object[][]{{1L},{2L}};
    }

    @Test(dataProvider = "ids")
    public void testFindById(long id) throws Exception {
        mockMvc.perform(get("/topic/{topicId}",id))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testFollow() throws Exception {

    }

    @Test
    public void testUnfollow() throws Exception {
    }

    @Test
    public void testCreate() throws Exception {

    }

    @Test
    public void testUpdate() throws Exception {
    }

    @Test
    public void testDelete() throws Exception {
    }

}