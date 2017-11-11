package com.cqs.qicaiyun.common.jwt;

import com.cqs.qicaiyun.system.entity.User;
import org.testng.annotations.Test;

/**
 * Created by cqs on 2017/11/6.
 */
public class TokenManagerTest {

    TokenManager manager = TokenManager.getInstance();


    @Test
    public void testGetInstance() throws Exception {
    }

    @Test
    public void testAddToken() throws Exception {
    }

    @Test
    public void testGetToken() throws Exception {
    }

    @Test
    public void testInvalidate() throws Exception {

        System.out.println("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCJ9.vp3O4oyTjspIuUeB2YdCnnZ24xSe35WVOGg6kJ0xpKQ".equals(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCJ9.vp3O4oyTjspIuUeB2YdCnnZ24xSe35WVOGg6kJ0xpKQ"
        ));

        String[] s = {"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCJ9.vp3O4oyTjspIuUeB2YdCnnZ24xSe35WVOGg6kJ0xpKQ",
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCJ9.vp3O4oyTjspIuUeB2YdCnnZ24xSe35WVOGg6kJ0xpKQ",
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCJ9.vp3O4oyTjspIuUeB2YdCnnZ24xSe35WVOGg6kJ0xpKQ"};
        System.out.println(s[1].equals(s[0]));
        System.out.println(s[2].equals(s[0]));
    }

    @Test
    public void testCheckValidate() throws Exception {
        String token1 = JWTUtils.createTokenHS256("li");
        String token2 = JWTUtils.createTokenHS256("li");
        manager.addToken(token1,new User());
        manager.addToken(token2,new User());

        System.out.println(manager);

    }

}