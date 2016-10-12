package com.sky.demo.web_demo_multi_tenant_separate_db.cache;

import com.google.common.collect.Lists;
import com.sky.demo.web_demo_multi_tenant_separate_db.basedb.TenantRoutingDataSource;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.service.TenantService;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.SpringUtil;
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
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by user on 16/10/12.
 */
@Service
public class TenantDataSourceCache {

    private static final Logger logger = LoggerFactory.getLogger(TenantDataSourceCache.class);

    @Resource
    private TenantService tenantService;

    private static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();


    @PostConstruct
    public void init() {
        loadTenantDataSource();

        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    loadTenantDataSource();
                } catch (Exception e) {
                    logger.error("load tenant data source error!", e);
                }
            }
        }, 5, 5, TimeUnit.MINUTES);

    }

    @PreDestroy
    private void destroy() {
        scheduledExecutorService.shutdown();
    }


    private synchronized void loadTenantDataSource() {
        logger.debug("   ====== load tenant data source ======   ");

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

            //print connection url
//            for (Map.Entry<String, JdbcTemplate> entry : tenantJdbcTemplates.entrySet()) {
//                DataSource dataSource = (DataSource) entry.getValue().getDataSource();
//                try {
//                    logger.debug("   =====>  tenant :" + entry.getKey() + " , url=" + dataSource.getConnection().getMetaData().getURL());
//                } catch (SQLException e) {
//                    logger.error("get connection error", e);
//                }
//            }
        } else {
            throw new RuntimeException("tenants is empty!");
        }

    }

    private void addTenants(List<TenantForm> needAddTenants) {
        TenantRoutingDataSource tenantRoutingDataSource = SpringUtil.getCtx().getBean(TenantRoutingDataSource.class);
        for (TenantForm tenantForm : needAddTenants) {


        }
    }

    private void deleteTenants(List<String> needDeleteTenants) {
        TenantRoutingDataSource tenantRoutingDataSource = SpringUtil.getCtx().getBean(TenantRoutingDataSource.class);

        for (String name : needDeleteTenants) {
        }
    }

    private List<TenantForm> getNeedAddTenants(List<TenantForm> allTenants) {
        List<TenantForm> needAddTenants = Lists.newArrayList();
        for (TenantForm tenantForm : allTenants) {

        }
        return needAddTenants;
    }

    private List<String> getNeedDeleteTenants(List<TenantForm> allTenants) {
        List<String> needDeleteTenants = Lists.newArrayList();

        List<String> tenantNames = allTenants.stream().map(tenant -> tenant.getName()).collect(Collectors.toList());

        return needDeleteTenants;
    }
}
