package com.cqs.qicaiyun.common.interceptor;

import com.cqs.qicaiyun.common.jwt.TokenManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截器实现登录认证
 * Created by cqs on 2017/11/6.
 */
public class AuthInterceptor implements HandlerInterceptor {


    private TokenManager tokenManager = TokenManager.getInstance();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);
                if (session != null){
            final Cookie cookie = (Cookie) session.getAttribute("cookie");
            if (cookie != null && tokenManager.checkValidate(cookie.getValue())){
                return true;
            }
        }
        response.sendRedirect(request.getContextPath() + "/logi");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
