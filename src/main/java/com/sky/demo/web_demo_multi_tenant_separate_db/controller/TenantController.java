package com.sky.demo.web_demo_multi_tenant_separate_db.controller;

import com.google.common.base.Preconditions;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.Pager;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.RetData;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.RetStatus;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantQueryRequest;
import com.sky.demo.web_demo_multi_tenant_separate_db.service.TenantService;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.RetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by user on 16/9/19.
 */
@RequestMapping("/tenant")
@Controller
public class TenantController {

    private static final Logger logger = LoggerFactory.getLogger(TenantController.class);

    @Resource
    private TenantService tenantService;


    @RequestMapping("/query/{id}")
    @ResponseBody
    public RetData<TenantForm> query(@PathVariable int id) {
        RetData<TenantForm> result = null;
        try {
            TenantForm TenantForm = tenantService.query(id);

            result = RetUtil.buildSuccessRet(TenantForm);
        } catch (Exception e) {
            logger.error("query log error",e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }

    @RequestMapping("/queryByName")
    @ResponseBody
    public RetData<TenantForm> queryByName(@RequestParam String name) {
        RetData<TenantForm> result = null;
        try {
            TenantForm TenantForm = tenantService.queryByName(name);

            result = RetUtil.buildSuccessRet(TenantForm);
        } catch (Exception e) {
            logger.error("query log error",e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }

    @RequestMapping("/queryByToken")
    @ResponseBody
    public RetData<TenantForm> queryByToken(@RequestParam String token) {
        RetData<TenantForm> result = null;
        try {
            TenantForm TenantForm = tenantService.queryByToken(token);

            result = RetUtil.buildSuccessRet(TenantForm);
        } catch (Exception e) {
            logger.error("query log error",e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }

    @RequestMapping("/queryList")
    @ResponseBody
    public RetData<Pager<TenantForm>> queryList(@RequestBody TenantQueryRequest request) {

        RetData<Pager<TenantForm>> result = null;
        try {
            Pager<TenantForm> ret  = tenantService.queryList(request);
            result = RetUtil.buildSuccessRet(ret);
        } catch (Exception e) {
            logger.error("query log error",e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }


    @RequestMapping("/add")
    @ResponseBody
    public RetData<String> add(@RequestBody TenantForm record) {
        RetData<String> result = null;
        try {
            boolean isAdd = tenantService.add(record);
            Preconditions.checkArgument(isAdd, "add error");

            result = RetUtil.buildSuccessRet("success");

        } catch (Exception e) {
            logger.error("insert log error",e);
            result = RetUtil.buildErrorRet(RetStatus.INSERT_ERROR);
        }
        return result;
    }

    @RequestMapping("/addList")
    @ResponseBody
    public RetData<String> addList(@RequestBody List<TenantForm> records) {
        RetData<String> result = null;
        try {
            boolean isAdd = tenantService.addList(records);
            Preconditions.checkArgument(isAdd, "add error");

            result = RetUtil.buildSuccessRet("success");

        } catch (Exception e) {
            logger.error("add log error",e);
            result = RetUtil.buildErrorRet(RetStatus.INSERT_ERROR);
        }
        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public RetData<String> update(@RequestBody TenantForm record) {
        RetData<String> result = null;
        try {
            boolean isUpdate = tenantService.update(record);
            Preconditions.checkArgument(isUpdate, "update error");

            result = RetUtil.buildSuccessRet("success");
        } catch (Exception e) {
            logger.error("update log error",e);
            result = RetUtil.buildErrorRet(RetStatus.UPDATE_ERROR);
        }
        return result;
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public RetData<String> delete(@PathVariable int id) {
        RetData<String> result = null;
        try {
            boolean isDelete = tenantService.delete(id);
            Preconditions.checkArgument(isDelete, "delete error");

            result = RetUtil.buildSuccessRet("success");
        } catch (Exception e) {
            logger.error("delete log error",e);
            result = RetUtil.buildErrorRet(RetStatus.DELETE_ERROR);
        }
        return result;
    }
}
