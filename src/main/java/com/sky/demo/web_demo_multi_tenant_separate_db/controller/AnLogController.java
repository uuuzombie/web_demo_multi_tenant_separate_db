package com.sky.demo.web_demo_multi_tenant_separate_db.controller;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sky.demo.web_demo_multi_tenant_separate_db.context.AppContext;
import com.sky.demo.web_demo_multi_tenant_separate_db.context.DBContext;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantUserForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.SessionInfo;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.AsyncWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Preconditions;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.Pager;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.RetData;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.RetStatus;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.anlog.AnLogForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.anlog.AnLogInsertRequest;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.anlog.AnLogQueryRequest;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.anlog.AnLogUpdateRequest;
import com.sky.demo.web_demo_multi_tenant_separate_db.service.AnLogService;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.RetUtil;


/**
 * Created by rg on 2015/6/11.
 */
@RequestMapping("/anLog")
@Controller
public class AnLogController {

    private static final Logger logger = LoggerFactory.getLogger(AnLogController.class);

    @Resource
    private AnLogService anLogService;


    @RequestMapping("/query/{id}")
    @ResponseBody
    public RetData<AnLogForm> query(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) {
        RetData<AnLogForm> result = null;
        try {
            AnLogForm anLogForm = anLogService.query(id);

            result = RetUtil.buildSuccessRet(anLogForm);
        } catch (Exception e) {
            logger.error("query error",e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }

    @RequestMapping("/queryList")
    @ResponseBody
    public RetData<Pager<AnLogForm>> queryList(@RequestBody AnLogQueryRequest queryRequest, HttpServletRequest request, HttpServletResponse response) {

        RetData<Pager<AnLogForm>> result = null;
        try {
            Pager<AnLogForm> ret  = anLogService.queryList(queryRequest);
            result = RetUtil.buildSuccessRet(ret);
        } catch (Exception e) {
            logger.error("query list error",e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }


    @RequestMapping("/add")
    @ResponseBody
    public RetData<String> add(@RequestBody AnLogInsertRequest insertRequest, HttpServletRequest request, HttpServletResponse response) {
        RetData<String> result = null;
        try {
            boolean isAdd = anLogService.add(insertRequest);
            Preconditions.checkArgument(isAdd, "add error");

            result = RetUtil.buildSuccessRet("success");

        } catch (Exception e) {
            logger.error("add error",e);
            result = RetUtil.buildErrorRet(RetStatus.INSERT_ERROR);
        }
        return result;
    }

    @RequestMapping("/addList")
    @ResponseBody
    public RetData<String> addList(@RequestBody List<AnLogInsertRequest> insertRequests, HttpServletRequest request, HttpServletResponse response) {
        RetData<String> result = null;
        try {
            boolean isAdd = anLogService.addList(insertRequests);
            Preconditions.checkArgument(isAdd, "add error");

            result = RetUtil.buildSuccessRet("success");

        } catch (Exception e) {
            logger.error("add list error",e);
            result = RetUtil.buildErrorRet(RetStatus.INSERT_ERROR);
        }
        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public RetData<String> update(@RequestBody AnLogUpdateRequest updateRequest, HttpServletRequest request, HttpServletResponse response) {
        RetData<String> result = null;
        try {
            boolean isUpdate = anLogService.update(updateRequest);
            Preconditions.checkArgument(isUpdate, "update error");

            result = RetUtil.buildSuccessRet("success");
        } catch (Exception e) {
            logger.error("update error",e);
            result = RetUtil.buildErrorRet(RetStatus.UPDATE_ERROR);
        }
        return result;
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public RetData<String> delete(@PathVariable long id) {
        RetData<String> result = null;
        try {
            boolean isDelete = anLogService.delete(id);
            Preconditions.checkArgument(isDelete, "delete error");

            result = RetUtil.buildSuccessRet("success");
        } catch (Exception e) {
            logger.error("delete error",e);
            result = RetUtil.buildErrorRet(RetStatus.DELETE_ERROR);
        }
        return result;
    }

    @RequestMapping("/asyncAdd")
    @ResponseBody
    public RetData<String> asyncAdd(@RequestBody AnLogInsertRequest insertRequest, HttpServletRequest request, HttpServletResponse response) {
        RetData<String> result = null;
        try {
//            TenantUserForm tenantUser = AppContext.getTenantUser();
            TenantForm tenant = DBContext.getTenant();

            Future<Boolean> futrueResult = AsyncWorker.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    boolean isAdd = false;
                    try {
//                        AppContext.initResourcesByUserName(tenantUser.getUserName());    //need init App Resource
                        DBContext.initResourcesByDbKey(tenant.getDbName());    //need init App Resource

                        isAdd = anLogService.add(insertRequest);
                    } catch (Exception e) {
                        logger.error("async add error", e);
                    } finally {
//                        AppContext.releaseResources();
                        DBContext.releaseContext();
                    }
                    return isAdd;
                }
            });

            Preconditions.checkArgument(futrueResult.get(), "add error");

            result = RetUtil.buildSuccessRet("success");

        } catch (Exception e) {
            logger.error("add error",e);
            result = RetUtil.buildErrorRet(RetStatus.INSERT_ERROR);
        }
        return result;
    }

    @RequestMapping("/asyncUpdate")
    @ResponseBody
    public RetData<String> asyncUpdate(@RequestBody AnLogUpdateRequest updateRequest, HttpServletRequest request, HttpServletResponse response) {
        RetData<String> result = null;
        try {
            boolean isUpdate = anLogService.asyncUpdate(updateRequest);
            Preconditions.checkArgument(isUpdate, "async update error");

            result = RetUtil.buildSuccessRet("success");
        } catch (Exception e) {
            logger.error("update error",e);
            result = RetUtil.buildErrorRet(RetStatus.UPDATE_ERROR);
        }
        return result;
    }
}
