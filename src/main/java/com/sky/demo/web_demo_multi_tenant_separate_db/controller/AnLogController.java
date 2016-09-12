package com.sky.demo.web_demo_multi_tenant_separate_db.controller;

import javax.annotation.Resource;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;


/**
 * Created by rg on 2015/6/11.
 */
@Controller
@RequestMapping("/anLog")
public class AnLogController {

    private static final Logger logger = LoggerFactory.getLogger(AnLogController.class);

    @Resource
    private AnLogService anLogService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/query/{id}")
    @ResponseBody
    public RetData<AnLogForm> queryLog(@PathVariable long id) {
        RetData<AnLogForm> result = null;
        try {
            AnLogForm anLogForm = anLogService.query(id);

            result = RetUtil.buildSuccessRet(anLogForm);
        } catch (Exception e) {
            logger.error("query log error",e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }

    @RequestMapping("/queryList")
    @ResponseBody
    public RetData<Pager<AnLogForm>> queryLog(@RequestBody AnLogQueryRequest request) {

        RetData<Pager<AnLogForm>> result = null;
        try {
            Pager<AnLogForm> ret  = anLogService.queryList(request);
            result = RetUtil.buildSuccessRet(ret);
        } catch (Exception e) {
            logger.error("query log error",e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public RetData<String> deleteLog(@PathVariable long id) {
        RetData<String> result = null;
        try {
            boolean isDelete = anLogService.delete(id);
            Preconditions.checkArgument(isDelete, "delete error");

            result = RetUtil.buildSuccessRet("success");
        } catch (Exception e) {
            logger.error("delete log error",e);
            result = RetUtil.buildErrorRet(RetStatus.DELETE_ERROR);
        }
        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public RetData<String> updateLog(@RequestBody AnLogUpdateRequest request) {
        RetData<String> result = null;
        try {
            boolean isUpdate = anLogService.update(request);
            Preconditions.checkArgument(isUpdate, "update error");

            result = RetUtil.buildSuccessRet("success");
        } catch (Exception e) {
            logger.error("update log error",e);
            result = RetUtil.buildErrorRet(RetStatus.UPDATE_ERROR);
        }
        return result;
    }

    @RequestMapping("/insert")
    @ResponseBody
    public RetData<String> insertLog(@RequestBody AnLogInsertRequest request) {
        RetData<String> result = null;
        try {
            boolean isInsert = anLogService.add(request);
            Preconditions.checkArgument(isInsert, "insert error");

            result = RetUtil.buildSuccessRet("success");

        } catch (Exception e) {
            logger.error("insert log error",e);
            result = RetUtil.buildErrorRet(RetStatus.INSERT_ERROR);
        }
        return result;
    }

    @RequestMapping("/insertList")
    @ResponseBody
    public RetData<String> batchInsertLog(@RequestBody List<AnLogInsertRequest> requests) {
        RetData<String> result = null;
        try {
            boolean isInsert = anLogService.addList(requests);
            Preconditions.checkArgument(isInsert, "insert error");

            result = RetUtil.buildSuccessRet("success");

        } catch (Exception e) {
            logger.error("insert log error",e);
            result = RetUtil.buildErrorRet(RetStatus.INSERT_ERROR);
        }
        return result;
    }

}
