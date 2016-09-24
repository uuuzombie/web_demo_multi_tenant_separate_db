package com.sky.demo.web_demo_multi_tenant_separate_db.interceptor;

import com.google.common.collect.Lists;
import com.sky.demo.web_demo_multi_tenant_separate_db.context.AppContext;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantUserForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.SessionInfo;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by user on 16/9/24.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    private static final List<String> IGNORE_URIS = Lists.newArrayList();

    static {
        IGNORE_URIS.add("/login.jsp");
        IGNORE_URIS.add("/login/");
        IGNORE_URIS.add("/login");
        IGNORE_URIS.add("/logout");
    }

//    private static final Predicate<String>


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AppContext.releaseAppResources();

        boolean flag = true;
        try {
            String url = request.getRequestURI();
            if (IGNORE_URIS.contains(url)) {
                flag = true;
            } else {
                SessionInfo sessionInfo = SessionUtil.getSessionInfo(request);
                if (sessionInfo == null || sessionInfo.getTenantUser() == null) {
                    response.sendError(403);
                    flag = false;
                } else {
                    //validate some url

                    TenantUserForm tenantUser = sessionInfo.getTenantUser();
                    AppContext.initAppResourcesByUserName(tenantUser.getUserName());
                }
            }
        } catch (Exception e) {
            logger.error("pre handle login error", e);
            flag = false;
        }
        return flag;
    }
}
