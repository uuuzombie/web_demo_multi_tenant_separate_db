package com.sky.demo.web_demo_multi_tenant_separate_db.controller;

import com.sky.demo.web_demo_multi_tenant_separate_db.base.RetData;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.RetStatus;
import com.sky.demo.web_demo_multi_tenant_separate_db.context.AppContext;
import com.sky.demo.web_demo_multi_tenant_separate_db.context.DBContext;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantUserForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.Account;
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
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView login(@RequestParam String userName, @RequestParam String password,
                                         HttpServletRequest request, HttpServletResponse response) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            logger.info("login user:" + userName);
//            AppContext.initAppResourcesByUserName(userName);
            DBContext.initResourcesByUserName(userName);
            TenantUserForm tenantUserForm = tenantUserService.queryByUserName(userName);

            if (tenantUserForm != null) {
                //validate userName and password
                Account account = accountService.query(userName, password);
                if (account == null) {
                    logger.error("invalid account");
                    modelAndView.setViewName("error");
                } else {
                    //login success
                    SessionUtil.setSessionInfo(request, tenantUserForm);

                    modelAndView.addObject("sessionId", SessionUtil.getSessionId(request));
                    modelAndView.addObject("tenantUser", tenantUserForm);
                    modelAndView.setViewName("welcome");
                }
            } else {
                modelAndView.setViewName("error");
            }

        } catch (Exception e) {
            logger.error("login error", e);
            modelAndView.setViewName("error");
        } finally {
//            AppContext.releaseAppResources();
            DBContext.removeTenant();
        }
        return modelAndView;
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            SessionUtil.removeSessionInfo(request);

            modelAndView.setViewName("login");
        } catch (Exception e) {
            logger.error("logout error", e);
            modelAndView.setViewName("error");
        } finally {
//            AppContext.releaseAppResources();
            DBContext.releaseContext();
        }
        return modelAndView;
    }


}
