package com.sky.demo.web_demo_multi_tenant_separate_db.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sky.demo.web_demo_multi_tenant_separate_db.context.AppContext;

import java.util.List;

/**
 * Created by user on 16/9/23.
 */
@Component
public class DataSourceInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceInterceptor.class);


    private static final String AUTHORIZATION = "Authorization";
    private static final List<String> IGNORE_URLS = Lists.newArrayList();

    static {
        IGNORE_URLS.add("/login.jsp");
        IGNORE_URLS.add("/login/");
        IGNORE_URLS.add("/login");
        IGNORE_URLS.add("/logout");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AppContext.releaseAppResources();

        String url = request.getRequestURL().toString();

        String userName = request.getParameter("userName");
//        String token = request.getParameter("token");
        String authorization = request.getHeader(AUTHORIZATION);
        String token = authorization;

        if (StringUtils.isNotBlank(userName)) {
            AppContext.initAppResourcesByUserName(userName.trim());
        } else if (StringUtils.isNotBlank(token)) {
            AppContext.initAppResourcesByToken(token.trim());
        }

        return true;
    }
}
