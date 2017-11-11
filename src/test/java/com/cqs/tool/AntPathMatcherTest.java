package com.cqs.tool;

import org.springframework.util.AntPathMatcher;
import org.testng.annotations.Test;

/**
 * Created by cqs on 2017/11/5.
 */
public class AntPathMatcherTest {


    private AntPathMatcher matcher = new AntPathMatcher();

    @Test
    public void test1() throws Exception {
        String s = "/log*,/,/**.jsp,/webjars/**,/swagger**,/v2/api-docs";
        String path = "/logi";
        for (String s1 : s.split(",")) {
            boolean match = matcher.match(s1, "/logi");
            if (match){
                System.out.println(match);
            }
        }
    }
}
