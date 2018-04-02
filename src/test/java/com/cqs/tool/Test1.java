package com.cqs.tool;

import org.junit.Test;

import java.security.MessageDigest;

/**
 * Created by cqs on 2017/10/22.
 */
public class Test1 {

    @Test
    public void testMD5() throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        System.out.println("04f79f496dd6bdab3439511606528a4ad9caac5e".length());
    }
}
