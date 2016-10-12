package com.sky.demo.web_demo_multi_tenant_separate_db.context;

import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Created by user on 16/10/12.
 */
public class DBContext {

    private static final Logger logger = LoggerFactory.getLogger(DBContext.class);

    private static final ThreadLocal<String> dbKeyThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<TenantForm> tenantThreadLocal = new ThreadLocal<>();


    public static void setDbKey(String dbKey) {
        dbKeyThreadLocal.set(dbKey);
    }

    public static String getDbKey() {
        return dbKeyThreadLocal.get();
    }

    public static void removeDbKey() {
        dbKeyThreadLocal.remove();
    }

    public static void setTenant(TenantForm tenant) {
        tenantThreadLocal.set(tenant);

        if (tenant != null) {
            MDC.put("tenant", tenant.getName());
        }
    }

    public static TenantForm getTenant() {
        return tenantThreadLocal.get();
    }

    public static void removeTenant() {
        tenantThreadLocal.remove();
    }


    public static void releaseContext() {
        logger.debug("   ====> release db contexts ");

        removeDbKey();
        removeTenant();
        MDC.remove("tenant");
    }

}
