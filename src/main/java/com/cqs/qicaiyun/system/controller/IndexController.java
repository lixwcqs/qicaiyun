package com.cqs.qicaiyun.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cqs.qicaiyun.common.jwt.JWTUtils;
import com.cqs.qicaiyun.common.jwt.TokenManager;
import com.cqs.qicaiyun.system.entity.User;
import com.cqs.qicaiyun.system.service.IUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 首页访问
 * 登录登出
 * Created by cqs on 2017/11/5.
 */
@Controller
public class IndexController {

    private TokenManager manager = TokenManager.getInstance();

    @Resource(name = "userService")
    private IUserService service;


    @ApiOperation("访问登录页面")
    @GetMapping("/logi")
    public String loginJsp() {
        return "/user/login";
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录提交")
    @ApiImplicitParams({@ApiImplicitParam(name = "account", value = "登录账号", dataTypeClass = String.class),
            @ApiImplicitParam(name = "password", value = "密码", dataTypeClass = String.class)})
    public String login(@RequestParam("account") String account, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        //登录的话
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        wrapper.setEntity(user);
        wrapper.eq("account", account);
        User u = service.selectOne(wrapper);
        if (u != null) {
            String token = JWTUtils.createTokenHS256(account);
            Cookie cookie = new Cookie("_token", token);
            cookie.setMaxAge(3600);//1小时
            cookie.setHttpOnly(true);
            request.getSession(true).setAttribute("cookie", cookie);
            response.addCookie(cookie);
            manager.addToken(token, user);
            return "redirect:/fd";
        } else {
            request.setAttribute("em", "用户名或者密码错误");
            return "redirect:/logi";
        }
    }


    @RequestMapping("/")
    @ApiOperation(value = "系统首页")
    public String home() {
        return "/index";
    }
}
