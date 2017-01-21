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
@Deprecated
public abstract class BaseDao {

    private static final Logger logger = LoggerFactory.getLogger(BaseDao.class);

    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";


    @Resource
    private JdbcTemplate defaultJdbcTemplate;          //default_db
    @Resource
    private NamedParameterJdbcTemplate defaultNamedParameterJdbcTemplate;      //default_db

    //default_db
    public JdbcTemplate getDefaultJdbcTemplate() {
//        jdbcTemplate.getDataSource().getConnection().setCatalog(catalogName);

        return defaultJdbcTemplate;
    }

    public void setDefaultJdbcTemplate(JdbcTemplate defaultJdbcTemplate) {
        this.defaultJdbcTemplate = defaultJdbcTemplate;
    }

    public NamedParameterJdbcTemplate getDefaultNamedParameterJdbcTemplate() {
        // NamedParameterJdbcTemplate
        return defaultNamedParameterJdbcTemplate;
    }

    public void setDefaultNamedParameterJdbcTemplate(NamedParameterJdbcTemplate defaultNamedParameterJdbcTemplate) {
        this.defaultNamedParameterJdbcTemplate = defaultNamedParameterJdbcTemplate;
    }


    //tenant dbs
    public JdbcTemplate getTenantJdbcTemplate() {
//        jdbcTemplate.getDataSource().getConnection().setCatalog(catalogName);

        return AppContext.getJdbcTemplate();
    }

    public NamedParameterJdbcTemplate getTenantNamedParameterJdbcTemplate() {
        return AppContext.getNamedParameterJdbcTemplate();
    }




}
