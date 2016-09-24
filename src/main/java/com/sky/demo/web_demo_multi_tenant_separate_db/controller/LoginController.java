package com.sky.demo.web_demo_multi_tenant_separate_db.controller;

import com.sky.demo.web_demo_multi_tenant_separate_db.base.RetData;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.RetStatus;
import com.sky.demo.web_demo_multi_tenant_separate_db.context.AppContext;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantUserForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.Account;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.SessionInfo;
import com.sky.demo.web_demo_multi_tenant_separate_db.service.AccountService;
import com.sky.demo.web_demo_multi_tenant_separate_db.service.TenantUserService;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.RetUtil;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by user on 16/9/24.
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Resource
    private TenantUserService tenantUserService;
    @Resource
    private AccountService accountService;


    @RequestMapping("/login")
    @ResponseBody
    public RetData<TenantUserForm> login(@RequestParam String userName, @RequestParam String password,
                                         HttpServletRequest request, HttpServletResponse response) {

        RetData<TenantUserForm> result = null;
        try {
            AppContext.initAppResourcesByUserName(userName);
            TenantUserForm tenantUserForm = tenantUserService.queryByUserName(userName);

            if (tenantUserForm != null) {
                //validate userName and password
                Account account = accountService.query(userName, password);
                if (account == null) {
                    result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR, "invalid username or password");
                } else {
                    //login success
                    SessionUtil.setSessionInfo(request, tenantUserForm);

                    result = RetUtil.buildSuccessRet(tenantUserForm);
                }
            } else {
                result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR, "invalid username or password");
            }

        } catch (Exception e) {
            logger.error("query log error", e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        } finally {
            //is safe ?
            AppContext.releaseAppResources();
        }
        return result;

    }

}
