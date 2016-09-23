package com.sky.demo.web_demo_multi_tenant_separate_db.interceptor;

import com.sky.demo.web_demo_multi_tenant_separate_db.context.AppContext;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantUserForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.service.TenantUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by user on 16/9/23.
 */
@Component
public class DataSourceInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceInterceptor.class);


    @Resource
    private TenantUserService tenantUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AppContext.releaseAppResources();

        String userName = request.getParameter("userName");
        String token = request.getParameter("token");

        TenantUserForm tenantFromUserName = null;
        if (StringUtils.isNotBlank(userName)) {
            tenantFromUserName = tenantUserService.queryByUserName(userName);
        }

        if (StringUtils.isNotBlank(token)) {
        }

        return super.preHandle(request, response, handler);
    }
}
