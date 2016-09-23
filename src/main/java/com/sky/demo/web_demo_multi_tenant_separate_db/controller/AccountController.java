package com.sky.demo.web_demo_multi_tenant_separate_db.controller;

import com.google.common.base.Preconditions;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.Pager;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.RetData;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.RetStatus;
import com.sky.demo.web_demo_multi_tenant_separate_db.context.AppContext;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.account.AccountQueryRequest;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.Account;
import com.sky.demo.web_demo_multi_tenant_separate_db.service.AccountService;
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
@RequestMapping("/account")
@Controller
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Resource
    private AccountService accountService;


    @RequestMapping("/query/{id}")
    @ResponseBody
    public RetData<Account> query(@PathVariable int id) {   //, @RequestParam String userName
        RetData<Account> result = null;
        try {
            //AppContext.initAppResourcesByUserName(userName);

            Account Account = accountService.query(id);

            result = RetUtil.buildSuccessRet(Account);
        } catch (Exception e) {
            logger.error("query log error",e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }

    @RequestMapping("/queryList")
    @ResponseBody
    public RetData<Pager<Account>> queryList(@RequestBody AccountQueryRequest request) {

        RetData<Pager<Account>> result = null;
        try {
            Pager<Account> ret  = accountService.queryList(request);
            result = RetUtil.buildSuccessRet(ret);
        } catch (Exception e) {
            logger.error("query log error",e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }


    @RequestMapping("/add")
    @ResponseBody
    public RetData<String> add(@RequestBody Account record) {
        RetData<String> result = null;
        try {
            boolean isAdd = accountService.add(record);
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
    public RetData<String> addList(@RequestBody List<Account> records) {
        RetData<String> result = null;
        try {
            boolean isAdd = accountService.addList(records);
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
    public RetData<String> update(@RequestBody Account record) {
        RetData<String> result = null;
        try {
            boolean isUpdate = accountService.update(record);
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
            boolean isDelete = accountService.delete(id);
            Preconditions.checkArgument(isDelete, "delete error");

            result = RetUtil.buildSuccessRet("success");
        } catch (Exception e) {
            logger.error("delete log error",e);
            result = RetUtil.buildErrorRet(RetStatus.DELETE_ERROR);
        }
        return result;
    }

}
