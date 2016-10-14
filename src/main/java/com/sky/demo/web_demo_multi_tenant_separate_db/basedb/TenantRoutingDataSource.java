package com.sky.demo.web_demo_multi_tenant_separate_db.basedb;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sky.demo.web_demo_multi_tenant_separate_db.context.DBContext;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.service.TenantService;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.AppConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 16/10/12.
 */
public class TenantRoutingDataSource extends AbstractRoutingDataSource{

    private static final Logger logger = LoggerFactory.getLogger(TenantRoutingDataSource.class);

    private static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Resource
    private TenantService tenantService;

    @Resource
    private BasicDataSource tenantDataSource;



    /**
     * 该方法必须重写,为了根据数据库标示符取得当前的数据库
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DBContext.getDbKey();
    }


    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = super.getConnection();
        changeTenant(connection);
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection connection = super.getConnection(username, password);
        changeTenant(connection);
        return connection;
    }

    @Override
    public void afterPropertiesSet() {
        //AbstractRoutingDataSource已经实现了spring的InitializingBean接口
        initDataSources();

        //定时加载
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    initDataSources();
                } catch (Exception e) {
                    logger.error("init data sources error", e);
                }
            }
        }, 5, 5, TimeUnit.MINUTES);

        super.afterPropertiesSet(); //千万别忘了，用来通知spring容器
    }

    /**
     * 切换租户数据库
     * 注:PostgreSQL的同一个会话Connection无法切换DB
     * MySQL 可以使用 use xxx 切换
     * @param connection
     */
    private void changeTenant(Connection connection) {
        if (DBContext.getDbKey() != null && !StringUtils.equals(AppConfig.getItem("jdbc.default.key", "default_db"),
                DBContext.getDbKey())) {
            try {
//                connection.setCatalog(DBContext.getDbKey());
                connection.createStatement().execute("use `" + DBContext.getDbKey() + "`");
//                connection.createStatement().execute("\\c " + DBContext.getDbKey());


            } catch (SQLException e) {
                logger.error("change tenant db error : {}", DBContext.getDbKey(), e);

            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        logger.error("close connection error", e);
                    }
                }
            }

        }
    }


    public void initDataSources() {
        logger.debug("   ====== load tenant data source ======   ");

        //TenantService tenantService = SpringUtil.getCtx().getBean(TenantService.class);
        List<TenantForm> allTenants = null;
        try {
            allTenants = tenantService.queryList(Lists.newArrayList());
        } catch (Exception e) {
            logger.error("load all tenants error", e);
            throw new RuntimeException("load all tenants error");
        }

        if (CollectionUtils.isNotEmpty(allTenants)) {
            Map<Object, Object> targetDataSources = Maps.newHashMap();
//            DataSource dataSource = (DataSource) SpringUtil.getCtx().getBean("tenantDataSource");

            for (TenantForm tenant : allTenants) {
                targetDataSources.put(tenant.getDbName(), tenantDataSource);
            }

            this.setTargetDataSources(targetDataSources);

            super.afterPropertiesSet();
            logger.info("初始化动态数据源完成");

            //print connection url
            for (Map.Entry<Object, Object> entry : targetDataSources.entrySet()) {
                BasicDataSource ds = (BasicDataSource) entry.getValue();
                try {
                    logger.debug("   =====>  tenant :" + entry.getKey());
                } catch (Exception e) {
                    logger.error("get connection error", e);
                }
            }
        } else {
            throw new RuntimeException("tenants is empty!");
        }
    }






}
