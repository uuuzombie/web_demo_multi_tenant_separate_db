package com.sky.demo.web_demo_multi_tenant_separate_db.controller;

import com.sky.demo.web_demo_multi_tenant_separate_db.base.RetData;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.RetStatus;
import com.sky.demo.web_demo_multi_tenant_separate_db.filter.HeaderAuthFilter;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.RetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by user on 16/9/30.
 */
@RequestMapping("/v1/header")
@Controller
public class HeaderController {

    private static final Logger logger = LoggerFactory.getLogger(HeaderController.class);

    @RequestMapping("/query")
    @ResponseBody
    public RetData<String> registry(HttpServletRequest request, HttpServletResponse response) {
        RetData<String> result = null;
        try {
            String auth = request.getHeader("Authorization");

            result = RetUtil.buildSuccessRet(auth);
        } catch (Exception e) {
            logger.error("registry error",e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }


}
