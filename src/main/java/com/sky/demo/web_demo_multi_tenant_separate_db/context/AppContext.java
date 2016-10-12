package com.sky.demo.web_demo_multi_tenant_separate_db.context;

import com.google.common.base.Preconditions;
import com.sky.demo.web_demo_multi_tenant_separate_db.cache.CommonDataSourceCache;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantUserForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.service.TenantService;
import com.sky.demo.web_demo_multi_tenant_separate_db.service.TenantUserService;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.SpringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * Created by user on 16/9/20.
 */
@Deprecated
public class AppContext implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(AppContext.class);

    private static ThreadLocal<TenantForm> tenantThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<TenantUserForm> tenantUserThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<JdbcTemplate> jdbcTemplateThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<NamedParameterJdbcTemplate> namedParameterJdbcTemplateThreadLocal = new ThreadLocal<>();


    public static TenantForm getTenant() {
        return tenantThreadLocal.get();
    }

    public static void setTenant(TenantForm tenant) {
        tenantThreadLocal.set(tenant);

        if (tenant != null) {
            MDC.put("tenant", tenant.getName());
        }
    }

    public static void releaseTenant() {
        tenantThreadLocal.remove();
    }

    public static TenantUserForm getTenantUser() {
        return tenantUserThreadLocal.get();
    }

    public static void setTenantUser(TenantUserForm tenantUser) {
        tenantUserThreadLocal.set(tenantUser);
    }

    public static void releaseTenantUser() {
        tenantUserThreadLocal.remove();
    }


    public static JdbcTemplate getJdbcTemplate() {
        return jdbcTemplateThreadLocal.get();
    }

    public static void setJdbcTemplate() {
        JdbcTemplate jdbcTemplate = null;
        CommonDataSourceCache commonDataSourceCache = SpringUtil.getCtx().getBean(CommonDataSourceCache.class);
        if (commonDataSourceCache != null) {
            TenantForm tenant = getTenant();
            jdbcTemplate = commonDataSourceCache.getJdbcTemplate(tenant.getName());
            jdbcTemplateThreadLocal.set(jdbcTemplate);
        }
    }

    public static void releaseJdbcTemplate() {
        jdbcTemplateThreadLocal.remove();
    }

    public static NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplateThreadLocal.get();
    }

    public static void setNamedParameterJdbcTemplate() {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;
        CommonDataSourceCache commonDataSourceCache = SpringUtil.getCtx().getBean(CommonDataSourceCache.class);
        if (commonDataSourceCache != null) {
            TenantForm tenant = getTenant();
            namedParameterJdbcTemplate = commonDataSourceCache.getNamedParameterJdbcTemplate(tenant.getName());
            namedParameterJdbcTemplateThreadLocal.set(namedParameterJdbcTemplate);
        }
    }

    public static void releaseNamedParameterJdbcTemplate() {
        namedParameterJdbcTemplateThreadLocal.remove();
    }


    /**
     * 初始化tenant，JdbcTemplate信息
     * @param userName
     */
    public static void initAppResourcesByUserName(String userName) {
        Preconditions.checkState(StringUtils.isNotBlank(userName), "userName is blank!!");
        logger.debug("   ====> init App Resources user name = " + userName);

        try {
            TenantUserService tenantUserService = SpringUtil.getCtx().getBean(TenantUserService.class);
            TenantUserForm tenantUser = tenantUserService.queryByUserName(userName);
            Preconditions.checkNotNull(tenantUser, "tenant user is null!");

            setTenantUser(tenantUser);
            setTenant(tenantUser.getTenant());
            setJdbcTemplate();
            setNamedParameterJdbcTemplate();
        } catch (BeansException e) {
            logger.error("init app resources by userName error", e);
        }

        try {
            logger.debug("   ====> init AppResources by userName: " + userName + ", tenantUser=" + getTenantUser().getUserName()
                    + ", tenant = " + getTenant().getDbName() + ", jdbcTemplate = "
                    + getJdbcTemplate().getDataSource().getConnection().getMetaData().getURL());
        } catch (SQLException e) {
            logger.error("print app resource error");
        }

    }

    /**
     * 初始化tenant，JdbcTemplate信息
     * @param token
     */
    public static void initAppResourcesByToken(String token) {
        Preconditions.checkState(StringUtils.isNotBlank(token), "token is blank!!");
        logger.debug("   ====> init App Resources token = " + token);

        try {
            TenantService tenantService = SpringUtil.getCtx().getBean(TenantService.class);
            TenantForm tenant = tenantService.queryByDeviceToken(token);
            Preconditions.checkNotNull(tenant, "tenant is null!");

            setTenant(tenant);
            setJdbcTemplate();
            setNamedParameterJdbcTemplate();
        } catch (BeansException e) {
            logger.error("init app resource by token error", e);
        }

        try {
            logger.debug("   ====> init AppResources by token: " + token
                    + ", tenant = " + getTenant().getDbName() + ", jdbcTemplate = "
                    + getJdbcTemplate().getDataSource().getConnection().getMetaData().getURL());
        } catch (SQLException e) {
            logger.error("print app resource error");
        }
    }

    /**
     * 释放AppContext中资源
     */
    public static void releaseAppResources() {
        logger.debug("   ====> release App Resources ");

        releaseTenant();
        releaseTenantUser();
        releaseJdbcTemplate();
        releaseNamedParameterJdbcTemplate();
        MDC.remove("tenant");
    }
}
