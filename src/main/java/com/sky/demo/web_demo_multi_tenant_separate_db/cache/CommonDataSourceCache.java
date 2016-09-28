package com.sky.demo.web_demo_multi_tenant_separate_db.cache;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.service.TenantService;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.AppConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by rg on 9/19/16.
 */
@Service
public class CommonDataSourceCache {

    private static final Logger logger = LoggerFactory.getLogger(CommonDataSourceCache.class);

    @Resource
    private TenantService tenantService;


    private static Map<String, JdbcTemplate> tenantJdbcTemplates = Maps.newHashMap();
    private static Map<String, NamedParameterJdbcTemplate> tenantNamedParameterJdbcTemplates = Maps.newHashMap();

    private static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();


    @PostConstruct
    public void init() {
        loadTenants();

        //定时load
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    loadTenants();
                } catch (Exception e) {
                    logger.error("load tenant error", e);
                }
            }
        }, 5, 5, TimeUnit.MINUTES);

    }

    /**
     * 加载租户信息
     * 根据tenant表，新增或者删除租户信息
     */
    public void loadTenants() {
        logger.debug("   ====== load tenant ======   ");

        List<TenantForm> allTenants = null;
        try {
            allTenants = tenantService.queryList(Lists.newArrayList());
        } catch (Exception e) {
            logger.error("load all tenants error", e);
            throw new RuntimeException("load all tenants error");
        }

        if (CollectionUtils.isNotEmpty(allTenants)) {
            List<TenantForm> needAddTenants = getNeedAddTenants(allTenants);
            List<String> needDeleteTenants = getNeedDeleteTenants(allTenants);

            addTenants(needAddTenants);

            deleteTenants(needDeleteTenants);

            //print connetion
            for (Map.Entry<String, JdbcTemplate> entry : tenantJdbcTemplates.entrySet()) {
                DataSource dataSource = (DataSource) entry.getValue().getDataSource();
                try {
                    logger.debug("   =====>  tenant :" + entry.getKey() + " , url=" + dataSource.getConnection().getMetaData().getURL());
                } catch (SQLException e) {
                    logger.error("get connection error", e);
                }
            }
        } else {
            throw new RuntimeException("tenants is empty!");
        }
    }


    /**
     * 重新加载租户信息
     */
    public void reloadAllTenants() {
        logger.debug("   ====== reload all tenant ======   ");

        tenantJdbcTemplates.clear();
        tenantNamedParameterJdbcTemplates.clear();

        List<TenantForm> allTenants = null;
        try {
            allTenants = tenantService.queryList(Lists.newArrayList());
        } catch (Exception e) {
            logger.error("load all tenants error", e);
            throw new RuntimeException("load all tenants error");
        }

        if (CollectionUtils.isNotEmpty(allTenants)) {

            addTenants(allTenants);

            //print connetion
            for (Map.Entry<String, JdbcTemplate> entry : tenantJdbcTemplates.entrySet()) {
                DataSource dataSource = (DataSource) entry.getValue().getDataSource();
                try {
                    logger.debug("   =====>  tenant :" + entry.getKey() + " , url=" + dataSource.getConnection().getMetaData().getURL());
                } catch (SQLException e) {
                    logger.error("get connection error", e);
                }
            }
        } else {
            throw new RuntimeException("tenants is empty!");
        }
    }


    private void addTenants(List<TenantForm> needAddTenants) {
        for (TenantForm tenantForm : needAddTenants) {
            DataSource dataSource = buildDataSource(tenantForm);

            boolean isConnected = false;
            try {
                DataSourceUtils.getConnection(dataSource);
                isConnected = true;
            } catch (CannotGetJdbcConnectionException e) {
                logger.error("      ====[WARNING]====  get db connection failed !!! tenant:" + tenantForm.getName(), e);
            }

            if (isConnected) {
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                tenantJdbcTemplates.put(tenantForm.getName(), jdbcTemplate);

                NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
                tenantNamedParameterJdbcTemplates.put(tenantForm.getName(), namedParameterJdbcTemplate);
            }
        }
    }

    private void deleteTenants(List<String> needDeleteTenants) {
        for (String name : needDeleteTenants) {
            tenantJdbcTemplates.remove(name);

            tenantNamedParameterJdbcTemplates.remove(name);
        }
    }


    private DataSource buildDataSource(TenantForm tenantForm) {
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName(AppConfig.getItem("postgre.jdbc.driver"));

        StringBuilder url = new StringBuilder();
        url.append(AppConfig.getItem("postgre.jdbc.db_url")).append(tenantForm.getDbName());

        dataSource.setUrl(url.toString());
        dataSource.setUsername(AppConfig.getItem("postgre.jdbc.username"));
        dataSource.setPassword(AppConfig.getItem("postgre.jdbc.password"));
        dataSource.setInitialSize(3);       //连接池启动时初始化连接数，默认0
        dataSource.setMaxActive(30);        //连接池最大连接数
        dataSource.setMinIdle(0);           //连接池最小空闲的连接数，低于此数会创建新连接
        dataSource.setMaxIdle(3);           //连接池最大空闲的连接数，超过的空闲链接将被释放，-1表示不限
        dataSource.setMaxWait(50000);       //最大等待时间，当没有可用链接时，等待释放的最大时间，超时则抛异常，-1表示不限

        dataSource.setRemoveAbandonedTimeout(180);      //超过时间限制，回收没有用的链接
        dataSource.setRemoveAbandoned(true);            //超过RemoveAbandonedTimeout后，是否回收
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);
        dataSource.setTestWhileIdle(true);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTimeBetweenEvictionRunsMillis(1000 * 60 * 30);   //检查无效链接的时间间隔 30min

        return dataSource;
    }


    public JdbcTemplate getJdbcTemplate(String tenant) {
        return tenantJdbcTemplates.get(tenant);
    }

    public Map<String, JdbcTemplate> getAllJdbcTemplate() {
        return tenantJdbcTemplates;
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(String tenant) {
        return tenantNamedParameterJdbcTemplates.get(tenant);
    }

    public Map<String, NamedParameterJdbcTemplate> getAllNamedParameterJdbcTemplate() {
        return tenantNamedParameterJdbcTemplates;
    }

    private List<TenantForm> getNeedAddTenants(List<TenantForm> allTenants) {
        List<TenantForm> needAddTenants = Lists.newArrayList();
        for (TenantForm tenantForm : allTenants) {
            if (!tenantJdbcTemplates.keySet().contains(tenantForm.getName())) {
                needAddTenants.add(tenantForm);
            }
        }
        return needAddTenants;
    }

    private List<String> getNeedDeleteTenants(List<TenantForm> allTenants) {
        List<String> needDeleteTenants = Lists.newArrayList();

        List<String> tenantNames = allTenants.stream().map(tenant -> tenant.getName()).collect(Collectors.toList());
        for (String name : tenantJdbcTemplates.keySet()) {
            if (!tenantNames.contains(name)) {
                needDeleteTenants.add(name);
            }
        }
        return needDeleteTenants;
    }


}
