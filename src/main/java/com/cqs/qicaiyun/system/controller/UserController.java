package com.cqs.qicaiyun.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cqs.qicaiyun.common.Result;
import com.cqs.qicaiyun.system.entity.User;
import com.cqs.qicaiyun.system.service.IUserService;
import com.sun.istack.internal.NotNull;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by cqs on 2017/8/25.
 */
@Controller
@RequestMapping("/user")
@Log4j2
@Api(description = "本地用户信息")
public class UserController {

    @Resource(name = "userService")
    private IUserService service;

    @ApiOperation("查询本地用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", paramType = "path", value = "用户ID", required = true, dataType = "long")})
    @GetMapping("/{userId}")
    @ResponseBody
    public User userInfo(@PathVariable final Long userId) {
        User user = service.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户userId:" + userId + "不存在");
        }
        return user;
    }

    @PatchMapping("/u")
    @ResponseBody
    @ApiOperation("更新本地用户信息")
    //@ApiImplicitParams({@ApiImplicitParam(name = "user",value = "用户信息",paramType = "form", dataTypeClass = User.class, required = true)})
    public Result<User> updateUser(@NotNull @RequestBody User user) {
        //注意：账号字段不可修改
        // ----------------------------------  防止其他字段被更新
        boolean success = service.updateById(user);
        Result<User> result = Result.newInstance();
        result.success(success).data(user);
        return result;
    }


    @DeleteMapping("/{userId}")
    @ResponseBody
    @ApiOperation("删除本地用户信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", paramType = "path", value = "用户ID", dataType = "long", required = true)})
    public Boolean delete(@PathVariable Long userId) {
        return service.deleteById(userId);
    }



    @GetMapping("/check/{account}")
    @ResponseBody
    @ApiOperation(value = "(用户中心)账号是否存在", notes = "success：表示账号存在，反之不存在")
    public Result checkUserExist(@ApiParam(name = "account", value = "用户账号", required = true) @PathVariable String account) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("account", account);
        int num = service.selectCount(wrapper);
        return num > 0 ? Result.ok() : Result.fail("账号不存在");
    }




    @ApiOperation("访问登录页面")
    @GetMapping("/lg")
    public String loginJsp() {
        return "/user/login";
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录提交")
    @ApiImplicitParams({@ApiImplicitParam(name = "account", value = "登录账号", dataTypeClass = String.class),
            @ApiImplicitParam(name = "password", value = "密码", dataTypeClass = String.class)})
    public String login(@PathVariable String account, @PathVariable String password) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        //登录的话
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        wrapper.setEntity(user);
        wrapper.eq("account",account);
        User u = service.selectOne(wrapper);
        if (u != null) {

        }
        return "redirect:/";
    }


    @ApiOperation("访问注册页面")
    @GetMapping("r")
    public String register(){
        return "/user/register";
    }


    @PostMapping("/reg")
    @ResponseBody
    @ApiOperation(value = "本地注册", notes = "待实现：统一在用户管理中心注册")
    public Result<User> register(@RequestBody User user) {
        boolean success = false;
        log.debug(Thread.currentThread().getName());
        try {
            success = service.insert(user);
        } catch (Exception e) {
            return Result.fail("注册失败",user);
        }
        Result<User> result = Result.newInstance();
        result.success(success);
        result.data(user);
        return result;
    }


    @PatchMapping("/p/{account}/password")
    @ResponseBody
    @ApiOperation(value = "修改密码", hidden = true)
    @ApiImplicitParams({@ApiImplicitParam(name = "account", value = "账号", dataTypeClass = String.class, required = true, readOnly = true),
            @ApiImplicitParam(name = "newPassword", value = "密码", required = true)})
    public Result modifyPassword(@PathVariable String account, @PathVariable String newPassword) {
        throw new RuntimeException("还没有实现");
    }





}
