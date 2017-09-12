package com.cqs.qicaiyun.modules.restful.client;

import com.cqs.qicaiyun.system.entity.User;
import org.apache.commons.net.util.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by cqs on 2017/9/7.
 */
public class SpringRestTemplate {
    RestTemplate restTemplate = new RestTemplate();
    @Test
    private  void getUserAsync()
    {
        final String uri = "http://localhost:6060/jianshu/user/902497813412225025";

//
        AsyncRestTemplate restTemplate = new AsyncRestTemplate();
        ListenableFuture<ResponseEntity<User>> forEntity = restTemplate.getForEntity(uri, User.class);
        try {
            ResponseEntity<User> entity = forEntity.get(30, TimeUnit.SECONDS);
            User user = entity.getBody();
            System.out.println(user);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException("查询用户失败");
        }


    }

    //同步
    @Test
    private  void getUser()
    {
        final String uri = "http://localhost:6060/jianshu/user/902497813412225025";
        ResponseEntity<User> forEntity = restTemplate.getForEntity(uri, User.class);
        User user = forEntity.getBody();
        System.out.println(user);

    }

    HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }

    @Test
    public void testAuth() throws Exception {
        String url1 = "http://localhost:8080/smart-sso-server/";
        String url = "http://localhost:8080/smart-sso-server/login";

    }
}
