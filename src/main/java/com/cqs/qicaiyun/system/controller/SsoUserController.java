package com.cqs.qicaiyun.system.controller;


import com.cqs.qicaiyun.common.Result;
import com.cqs.qicaiyun.system.entity.User;
import com.smart.mvc.config.ConfigUtils;
import com.smart.mvc.model.ResultCode;
import com.sun.istack.internal.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.AsyncRestTemplate;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by cqs on 2017/9/9.
 */
public class SsoUserController {

    @Resource(name = "asyncRestTemplate")
    private AsyncRestTemplate arTemplate;

    //注册的时候需要先判定用户名是否存在
    private Result checkRemote(String account) {
        String uri = ConfigUtils.getProperty("sso.server.url") + "/admin/user/validateAccount/-1/" + account;
        ListenableFuture<ResponseEntity<com.smart.mvc.model.Result>> future = arTemplate.getForEntity(uri, com.smart.mvc.model.Result.class);
        try {
            ResponseEntity<com.smart.mvc.model.Result> entity = future.get(30, TimeUnit.SECONDS);
            int code = entity.getBody().getCode();
            return code == ResultCode.SUCCESS ? Result.ok() : Result.fail("不存在");
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return Result.fail(e.getMessage());
        }
    }

    //在用户配置中心注册  -- 还没实现  --- restclient 认证 测试 没通过
    private Result<User> regUserResultRemote(@NotNull @RequestBody User user) {
        String uri = ConfigUtils.getProperty("sso.server.url") + "/admin/user/save" ;
        Long id = user.getId();
        String account = user.getAccount();
        String password = user.getPassword();
        ListenableFuture<ResponseEntity<com.smart.mvc.model.Result>> forEntity = arTemplate.getForEntity(uri, com.smart.mvc.model.Result.class, id, account, password);
        //写入用户中心编码
        int code = ResultCode.ERROR;
        try {
            ResponseEntity<com.smart.mvc.model.Result> entity = forEntity.get(30, TimeUnit.SECONDS);
            code = entity.getBody().getCode();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException("注册失败【写入用户中心失败】");
        }
        if (code == ResultCode.SUCCESS) {
            // TO DO  本地保存用户信息
            throw new RuntimeException("本地保存用户信息");
        }else{
            return Result.fail("注册失败").data(user);
        }
    }
}
