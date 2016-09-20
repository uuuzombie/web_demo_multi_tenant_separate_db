package com.sky.demo.web_demo_multi_tenant_separate_db.context;

import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by user on 16/9/20.
 */
public class AppContext implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(AppContext.class);

    private static ThreadLocal<TenantForm> tenant = new ThreadLocal<>();


}
