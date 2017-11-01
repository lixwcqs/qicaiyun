package com.cqs.qicaiyun.common.tools;

import org.springframework.util.Assert;

import java.security.MessageDigest;

/**
 * 字符串加密算法
 * <p>
 * 参考:http://www.jb51.net/article/96121.htm
 * Created by cqs on 2017/10/22.
 */
public class StringSecurityUtils {


    /**
     * MD5加密 生成32位md5码
     *
     * @param source 待加密字符串
     * @return 返回32位md5码
     * @throws Exception
     */
    public static String md5Encode(String source) throws Exception {
        return encoding(source, SecurityAlgorithm.MD5);
    }

    /***
     * SHA加密 生成40位SHA码
     * @param  source 待加密字符串
     * @return 返回40位SHA码
     */
    public static String shaEncode(String source) throws Exception {
        return encoding(source, SecurityAlgorithm.SHA);
    }

    /***
     *
     * @param  source 待加密字符串
     * @return 返回加密后的状态码
     */
    public static String encoding(String source, SecurityAlgorithm sa) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance(sa.name());
        } catch (Exception e) {
            throw new RuntimeException();
        }
        byte[] byteArray = source.getBytes("UTF-8");
        byte[] bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            int val = ((int) bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    private enum SecurityAlgorithm {
        MD5, SHA
    }

    public static void main(String[] args) throws Exception{
        Assert.isTrue("04f79f496dd6bdab3439511606528a4ad9caac5e".equals(shaEncode("amigoxiexiexingxing")));

    }
}
