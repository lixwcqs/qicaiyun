package com.cqs.qicaiyun.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.net.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * http://www.jianshu.com/p/576dbf44b2ae
 * iss: jwt签发者
 * sub: jwt所面向的用户
 * aud: 接收jwt的一方
 * exp: jwt的过期时间，这个过期时间必须要大于签发时间
 * nbf: 定义在什么时间之前，该jwt都是不可用的.
 * iat: jwt的签发时间
 * jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
 * <p>
 * Created by cqs on 2017/11/1.
 */
public class JWTUtils {

    private static KeyPair keyPair = getKeyPair();

    public static String createTokenHS256(String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth0")
                    .sign(algorithm);
            return token.intern();
        } catch (UnsupportedEncodingException | JWTCreationException exception) {
            return null;
        }
    }

    /**
     * http://www.cnblogs.com/SirSmith/p/4990183.html
     *
     * @return
     */
    public static String createTokenRS256() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
            String token = JWT.create()
                    .withIssuer("auth0")
                    .sign(algorithm);
            return token.intern();
        } catch (JWTCreationException exception) {
            return null;
        }
    }



    public static void verifierRH256(String token, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
        } catch (UnsupportedEncodingException | JWTVerificationException exception) {
            //UTF-8 encoding not supported
            throw new RuntimeException(exception);
        }
    }

    public static void verifierRS256(String token) {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
            JWTVerifier verifier = JWT.require(algorithm).acceptExpiresAt(60 * 60 * 2)//10秒钟
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
           verifier.verify(token);
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            throw new RuntimeException(exception);
        }
    }

    private static KeyPair getKeyPair() {
        String KEY_ALGORITHM = "RSA";
        int KEY_SIZE = 1024;
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return keyPairGenerator.generateKeyPair();
    }


    public static void main(String[] args) {

//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        System.out.println(createTokenRS256());
//        stopWatch.stop();
//        System.out.println(stopWatch.getTotalTimeMillis());


        String s = "H";
        byte[] bytes = s.getBytes();
//
        String tokenRS256 = createTokenRS256();
        verifierRS256(tokenRS256);
        Base64 base64 = new Base64();
        String s1 = base64.encodeToString(bytes);
        System.out.println(s1);
    }
}
