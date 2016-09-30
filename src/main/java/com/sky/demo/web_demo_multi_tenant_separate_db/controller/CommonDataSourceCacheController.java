package com.sky.demo.web_demo_multi_tenant_separate_db.controller;

import com.google.common.collect.Maps;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.RetData;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.RetStatus;
import com.sky.demo.web_demo_multi_tenant_separate_db.cache.CommonDataSourceCache;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.RetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by user on 16/9/20.
 */
@RequestMapping("/dataSource")
@Controller
public class CommonDataSourceCacheController {

    private static final Logger logger = LoggerFactory.getLogger(CommonDataSourceCacheController.class);

    @Resource
    private CommonDataSourceCache commonDataSourceCache;


    @RequestMapping("/getAllJdbcTemplate")
    @ResponseBody
    public RetData<Map<String, String>> getAllJdbcTemplate(HttpServletRequest request, HttpServletResponse response) {
        RetData<Map<String, String>> result = null;

        try {
            Map<String, JdbcTemplate> tenantTemplates = commonDataSourceCache.getAllJdbcTemplate();

            Map<String, String> urlMap = Maps.newHashMap();
            for (Map.Entry<String, JdbcTemplate> entry : tenantTemplates.entrySet()) {
                urlMap.put(entry.getKey(), entry.getValue().getDataSource().getConnection().getMetaData().getURL());
            }
            result = RetUtil.buildSuccessRet(urlMap);
        } catch (Exception e) {
            logger.error("query all jdbcTemplate error", e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }

    @RequestMapping("/getAllNamedParameterJdbcTemplate")
    @ResponseBody
    public RetData<Map<String, String>> getAllNamedParameterJdbcTemplate(HttpServletRequest request, HttpServletResponse response) {
        RetData<Map<String, String>> result = null;

        try {
            Map<String, NamedParameterJdbcTemplate> allNamedParameterJdbcTemplate = commonDataSourceCache.getAllNamedParameterJdbcTemplate();

            Map<String, String> urlMap = Maps.newHashMap();
            for (Map.Entry<String, NamedParameterJdbcTemplate> entry : allNamedParameterJdbcTemplate.entrySet()) {
                urlMap.put(entry.getKey(), entry.getValue().getJdbcOperations().toString());
            }
            result = RetUtil.buildSuccessRet(urlMap);
        } catch (Exception e) {
            logger.error("query all namedParameterJdbcTemplate error", e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }

    @RequestMapping("/getJdbcTemplate")
    @ResponseBody
    public RetData<String> getJdbcTemplate(@RequestParam String tenant, HttpServletRequest request, HttpServletResponse response) {
        RetData<String> result = null;

        try {
            JdbcTemplate jdbcTemplate = commonDataSourceCache.getJdbcTemplate(tenant);

            result = RetUtil.buildSuccessRet(jdbcTemplate.getDataSource().getConnection().getMetaData().getURL());
        } catch (Exception e) {
            logger.error("query jdbcTemplate error", e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }

    @RequestMapping("/getNamedParameterJdbcTemplate")
    @ResponseBody
    public RetData<String> getNamedParameterJdbcTemplate(@RequestParam String tenant, HttpServletRequest request, HttpServletResponse response) {
        RetData<String> result = null;

        try {
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = commonDataSourceCache.getNamedParameterJdbcTemplate(tenant);

            result = RetUtil.buildSuccessRet(namedParameterJdbcTemplate.getJdbcOperations().toString());
        } catch (Exception e) {
            logger.error("query namedParameterJdbcTemplate error", e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }

    @RequestMapping("/reloadDataSource")
    @ResponseBody
    public RetData<Boolean> reloadDataSource() {
        RetData<Boolean> result = null;

        try {
            commonDataSourceCache.loadTenants();

            result = RetUtil.buildSuccessRet(Boolean.TRUE);
        } catch (Exception e) {
            logger.error("query all jdbcTemplate error", e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }

    @RequestMapping("/reloadAllDataSource")
    @ResponseBody
    public RetData<Boolean> reloadAllDataSource() {
        RetData<Boolean> result = null;

        try {
            commonDataSourceCache.reloadAllTenants();

            result = RetUtil.buildSuccessRet(Boolean.TRUE);
        } catch (Exception e) {
            logger.error("query all jdbcTemplate error", e);
            result = RetUtil.buildErrorRet(RetStatus.QUERY_ERROR);
        }
        return result;
    }
}
