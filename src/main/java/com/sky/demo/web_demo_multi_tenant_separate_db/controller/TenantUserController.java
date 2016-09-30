package com.sky.demo.web_demo_multi_tenant_separate_db.controller;

import com.google.common.base.Preconditions;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.Pager;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.RetData;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.RetStatus;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantUserForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantUserQueryRequest;
import com.sky.demo.web_demo_multi_tenant_separate_db.service.TenantUserService;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.RetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by user on 16/9/19.
 */
@RequestMapping("/tenantUser")
@Controller
public class TenantUserController {
    
    private static final Logger logger = LoggerFactory.getLogger(TenantUserController.class);

    @Resource
    private TenantUserService tenantUserService;


    @RequestMapping("/query/{id}")
    @ResponseBody
    public RetData<TenantUserForm> query(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {
        RetData<TenantUserForm> result = null;
        try {
            TenantUserForm TenantUserForm = tenantUserService.query(id);

            result = RetUtil.buildSuccessRet(TenantUserForm);
        } catch (Exception e) {
            logger.error("query error", e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }

    @RequestMapping("/queryByUserName")
    @ResponseBody
    public RetData<TenantUserForm> queryByUserName(@RequestParam String userName, HttpServletRequest request, HttpServletResponse response) {
        RetData<TenantUserForm> result = null;
        try {
            TenantUserForm TenantUserForm = tenantUserService.queryByUserName(userName);

            result = RetUtil.buildSuccessRet(TenantUserForm);
        } catch (Exception e) {
            logger.error("query error", e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }

    @RequestMapping("/queryList")
    @ResponseBody
    public RetData<Pager<TenantUserForm>> queryList(@RequestBody TenantUserQueryRequest queryRequest, HttpServletRequest request, HttpServletResponse response) {

        RetData<Pager<TenantUserForm>> result = null;
        try {
            Pager<TenantUserForm> ret = tenantUserService.queryList(queryRequest);
            result = RetUtil.buildSuccessRet(ret);
        } catch (Exception e) {
            logger.error("query list error", e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }


    @RequestMapping("/add")
    @ResponseBody
    public RetData<String> add(@RequestBody TenantUserForm record, HttpServletRequest request, HttpServletResponse response) {
        RetData<String> result = null;
        try {
            boolean isAdd = tenantUserService.add(record);
            Preconditions.checkArgument(isAdd, "add error");

            result = RetUtil.buildSuccessRet("success");

        } catch (Exception e) {
            logger.error("insert error", e);
            result = RetUtil.buildErrorRet(RetStatus.INSERT_ERROR);
        }
        return result;
    }

    @RequestMapping("/addList")
    @ResponseBody
    public RetData<String> addList(@RequestBody List<TenantUserForm> records, HttpServletRequest request, HttpServletResponse response) {
        RetData<String> result = null;
        try {
            boolean isAdd = tenantUserService.addList(records);
            Preconditions.checkArgument(isAdd, "add error");

            result = RetUtil.buildSuccessRet("success");

        } catch (Exception e) {
            logger.error("add error", e);
            result = RetUtil.buildErrorRet(RetStatus.INSERT_ERROR);
        }
        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public RetData<String> update(@RequestBody TenantUserForm record, HttpServletRequest request, HttpServletResponse response) {
        RetData<String> result = null;
        try {
            boolean isUpdate = tenantUserService.update(record);
            Preconditions.checkArgument(isUpdate, "update error");

            result = RetUtil.buildSuccessRet("success");
        } catch (Exception e) {
            logger.error("update error", e);
            result = RetUtil.buildErrorRet(RetStatus.UPDATE_ERROR);
        }
        return result;
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public RetData<String> delete(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {
        RetData<String> result = null;
        try {
            boolean isDelete = tenantUserService.delete(id);
            Preconditions.checkArgument(isDelete, "delete error");

            result = RetUtil.buildSuccessRet("success");
        } catch (Exception e) {
            logger.error("delete error", e);
            result = RetUtil.buildErrorRet(RetStatus.DELETE_ERROR);
        }
        return result;
    }
}
