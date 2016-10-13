package com.sky.demo.web_demo_multi_tenant_separate_db.aop;

import com.sky.demo.web_demo_multi_tenant_separate_db.context.DBContext;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.AppConfig;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by user on 16/10/12.
 */
@Aspect
@Component
public class DaoAop {

    private static final Logger logger = LoggerFactory.getLogger(DaoAop.class);

    //default db
    @Before("target(com.sky.demo.web_demo_multi_tenant_separate_db.basedb.MarkDefaultDao)")
    public void defaultDaoBefore() {
        String dbKey = AppConfig.getItem("jdbc.default.key", "default_db");
        logger.debug("aop before default db ==>  set DBContext dbKey={}", dbKey);
        DBContext.setDbKey(dbKey);
    }

    //tenant db
    @Before("target(com.sky.demo.web_demo_multi_tenant_separate_db.basedb.MarkTenantDao)")
    public void tenantDaoBefore() {
        String dbKey = DBContext.getTenant().getDbName();
        logger.debug("aop before tenant db  ==>  set DBContext dbKey={}", dbKey);
        DBContext.setDbKey(dbKey);
    }
}
