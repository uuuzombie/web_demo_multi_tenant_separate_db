package com.sky.demo.web_demo_multi_tenant_separate_db.basedb;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.Resource;

/**
 * Created by user on 16/10/12.
 */
public abstract class BaseTenantDao implements MarkTenantDao {

    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public JdbcTemplate getTenantJdbcTemplate() {
        return jdbcTemplate;
    }

    public NamedParameterJdbcTemplate getTenantNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }
}
