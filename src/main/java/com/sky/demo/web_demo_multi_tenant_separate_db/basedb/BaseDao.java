package com.sky.demo.web_demo_multi_tenant_separate_db.basedb;

import com.sky.demo.web_demo_multi_tenant_separate_db.context.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.Resource;

/**
 * Created by user on 16/9/18.
 */
public abstract class BaseDao {

    private static final Logger logger = LoggerFactory.getLogger(BaseDao.class);

    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";


    @Resource
    private JdbcTemplate jdbcTemplate;          //default_db
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;      //default_db

    //default_db
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    //tenant dbs
    public JdbcTemplate getTenantJdbcTemplate() {
        return AppContext.getJdbcTemplate();
    }

    public NamedParameterJdbcTemplate getTenantNamedParameterJdbcTemplate() {
        return AppContext.getNamedParameterJdbcTemplate();
    }




}
