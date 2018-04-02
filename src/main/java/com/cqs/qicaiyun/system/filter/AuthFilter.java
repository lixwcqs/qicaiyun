package com.cqs.qicaiyun.system.filter;

import com.cqs.qicaiyun.common.jwt.TokenManager;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by cqs on 2017/11/2.
 */
public class AuthFilter implements Filter {

    private TokenManager manager;


    // The mapping matches URLs using the following rules:
    // ? matches one character
    // * matches zero or more characters
    // ** matches zero or more 'directories' in a path
    // Some examples:
    //  com/t?st.jsp - matches com/test.jsp but also com/tast.jsp or com/txst.jsp
    //  com/*.jsp - matches all .jsp files in the com directory
    //  com/**/test.jsp - matches all test.jsp files underneath the com path
    // org/springframework/**/*.jsp - matches all .jsp files underneath the org/springframework path
    // org/**/servlet/bla.jsp - matches org/springframework/servlet/bla.jsp but also org/springframework/testing/servlet/bla.jsp and org/servlet/bla.jsp
    private AntPathMatcher authPathMater = new AntPathMatcher();

    private Collection<String> excludes = new ArrayList<>();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        manager = TokenManager.getInstance();
        String es = filterConfig.getInitParameter("excludes");
        if (StringUtils.isNotEmpty(es)) {
            this.excludes = Lists.newArrayList(es.split(","));
        }
    }

    @Override
    public void doFilter(ServletRequest request2, ServletResponse response2, FilterChain chain) throws IOException, ServletException {
        //检查是否登录
        HttpServletRequest request = (HttpServletRequest) request2;
        HttpServletResponse response = (HttpServletResponse) response2;
        //不在拦截范围内，放行
        String rp = request.getServletPath();
        for (String pattern : excludes) {
            boolean match = authPathMater.match(pattern, rp);
            if (match) {
                chain.doFilter(request, response);
                return;
            }
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String value = cookie.getValue();
                if (value != null) {
                    if (manager.checkValidate(value)) { //校验cookies里面的token是否有效
                        chain.doFilter(request, response);
                        return;
                    }
                }
            }
        }
        relogin(request, response);

    }

    private void relogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(request.getContextPath() + "/logi");
        } catch (IOException e) {
            throw new RuntimeException("返回登陆页面失败");
        }
    }

    @Override
    public void destroy() {

    }
}
