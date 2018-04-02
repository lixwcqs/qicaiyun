package com.cqs.tool.rest;

import com.cqs.qicaiyun.conf.JacksonConfig;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;


public class RestTemplateDemo {
    private static RestTemplate restTemplate = createRestTemplate();

    private static String cpath = "http://localhost:9090/qicaiyun";


    /**
     * 避免项目上下文路径修改后出现打范围修改上下文路径的情况
     *
     * @param url
     * @return
     */
    private static String getURL(String url) {
        return cpath + url;
    }


    public static RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(new JacksonConfig().serializingObjectMapper());
        converters.add(0, jsonConverter);
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }

//
//    @DataProvider(name = "ids")
//    public static Object[][] primeNumbers() {
//        return new Object[][]{{1L},{2L}};
//    }
//
//    //测试
//    @Test(dataProvider = "ids")
//    public void someRestCall(Long id) {
//        restTemplate = createRestTemplate();
//        Article article = restTemplate.getForObject(getURL("/article/find/{id}"),
//                Article.class, id);
//        System.out.println(article);
//        assertEquals(article.getId(), id);
//    }

}