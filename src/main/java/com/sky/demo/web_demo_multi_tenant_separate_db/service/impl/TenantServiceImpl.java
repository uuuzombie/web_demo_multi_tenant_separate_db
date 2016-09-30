package com.sky.demo.web_demo_multi_tenant_separate_db.service.impl;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.Pager;
import com.sky.demo.web_demo_multi_tenant_separate_db.basedb.BaseDao;
import com.sky.demo.web_demo_multi_tenant_separate_db.dao.TenantDao;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantQueryRequest;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.Tenant;
import com.sky.demo.web_demo_multi_tenant_separate_db.service.TenantService;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 16/9/19.
 */
@Service
public class TenantServiceImpl implements TenantService {

    private static final Logger logger = LoggerFactory.getLogger(TenantServiceImpl.class);

    @Resource
    private TenantDao tenantDao;


    private static final Function<Tenant, TenantForm> transfer2Form = new Function<Tenant, TenantForm>() {
        @Override
        public TenantForm apply(Tenant input) {
            TenantForm tenantForm = new TenantForm();
            tenantForm.setId(input.getId());
            tenantForm.setName(input.getName());
            tenantForm.setDeviceId(input.getDeviceId());
            tenantForm.setDeviceToken(input.getDeviceToken());
            tenantForm.setDbName(input.getDbName());
            tenantForm.setCreateTime(DateFormatUtils.format(input.getCreateTime(), Constants.DATETIME_PATTERN));
            tenantForm.setStatus(Tenant.Status.getStatusByCode(input.getStatus()));

            return tenantForm;
        }
    };

    private static final Function<TenantForm, Tenant> transfer2Tenant = new Function<TenantForm, Tenant>() {
        @Override
        public Tenant apply(TenantForm input) {
            Tenant tenant = new Tenant();
            tenant.setName(input.getName());
            tenant.setDeviceId(input.getDeviceId());
            tenant.setDeviceToken(input.getDeviceToken());
            tenant.setDbName(input.getDbName());
            tenant.setCreateTime(new Date());
            tenant.setStatus(Tenant.Status.NORMAL.getCode());

            return tenant;
        }
    };

    private static final Function<TenantForm, Tenant> transfer2UpdateTenant = new Function<TenantForm, Tenant>() {
        @Override
        public Tenant apply(TenantForm input) {
            Tenant tenant = new Tenant();
            tenant.setId(input.getId());
            tenant.setName(input.getName());
            tenant.setDeviceId(input.getDeviceId());
            tenant.setDeviceToken(input.getDeviceToken());
            tenant.setDbName(input.getDbName());
            tenant.setCreateTime(new Date());
            tenant.setStatus(input.getStatus() == null ? Tenant.Status.NORMAL.getCode() : input.getStatus().getCode());

            return tenant;
        }
    };

    @Override
    public TenantForm query(int id) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("id", id);
        condition.put("status", Tenant.Status.NORMAL.getCode());

        TenantForm result = null;
        Tenant tenant = null;
        try {
            tenant = tenantDao.select(condition);
        } catch (Exception e) {
            logger.error("query error", e);
        }

        if (tenant != null) {
            result = transfer2Form.apply(tenant);
        }
        return result;
    }

    @Override
    public TenantForm queryByName(String name) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("name", name);
        condition.put("status", Tenant.Status.NORMAL.getCode());

        TenantForm result = null;
        Tenant tenant = null;
        try {
            tenant = tenantDao.select(condition);
        } catch (Exception e) {
            logger.error("query by name error", e);
        }

        if (tenant != null) {
            result = transfer2Form.apply(tenant);
        }
        return result;
    }

    @Override
    public TenantForm queryByDeviceId(String deviceId) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("deviceId", deviceId);
        condition.put("status", Tenant.Status.NORMAL.getCode());

        TenantForm result = null;
        Tenant tenant = null;
        try {
            tenant = tenantDao.select(condition);
        } catch (Exception e) {
            logger.error("query by device id error", e);
        }

        if (tenant != null) {
            result = transfer2Form.apply(tenant);
        }
        return result;
    }

    @Override
    public TenantForm queryByDeviceToken(String deviceToken) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("deviceToken", deviceToken);
        condition.put("status", Tenant.Status.NORMAL.getCode());

        TenantForm result = null;
        Tenant tenant = null;
        try {
            tenant = tenantDao.select(condition);
        } catch (Exception e) {
            logger.error("query by device token error", e);
        }

        if (tenant != null) {
            result = transfer2Form.apply(tenant);
        }
        return result;
    }

    @Override
    public List<TenantForm> queryList(List<Integer> ids) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("status", Tenant.Status.NORMAL.getCode());

        if (CollectionUtils.isNotEmpty(ids)) {
            String strIds = Joiner.on(",").skipNulls().join(ids);
            condition.put("ids", strIds);
        }

        List<TenantForm> result = Lists.newArrayList();
        List<Tenant> tenants = null;
        try {
            tenants = tenantDao.selectList(condition);
        } catch (Exception e) {
            logger.error("query list error", e);
        }

        if (CollectionUtils.isNotEmpty(tenants)) {
            for (Tenant tenant : tenants) {
                TenantForm tenantForm = transfer2Form.apply(tenant);
                result.add(tenantForm);
            }
        }

        return result;
    }

    @Override
    public Pager<TenantForm> queryList(TenantQueryRequest queryRequest) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("beginTime", queryRequest.getBeginDate() + " 00:00:00");
        condition.put("endTime", queryRequest.getEndDate() + " 23:59:59");
        condition.put("status", Tenant.Status.NORMAL.getCode());

        Pager<TenantForm> ret = null;
        List<TenantForm> tenantForms = null;
        List<Tenant> tenants = null;
        try {
            long totalRecord = tenantDao.selectCount(condition);
            ret = new Pager<TenantForm>(totalRecord, queryRequest.getPageNumber(), queryRequest.getPageSize());

            int limit = ret.getPageSize();
            long offset = (ret.getPageNumber() - 1) * ret.getPageSize();
            condition.put(BaseDao.LIMIT, limit);
            condition.put(BaseDao.OFFSET, offset);

            tenantForms = Lists.newArrayList();
            tenants = tenantDao.selectList(condition);
        } catch (Exception e) {
            logger.error("query list error", e);
        }

        if (CollectionUtils.isNotEmpty(tenants)) {
            for (Tenant tenant : tenants) {
                TenantForm tenantForm = transfer2Form.apply(tenant);
                tenantForms.add(tenantForm);
            }
        }

        ret.setRows(tenantForms);
        return ret;
    }

    @Override
    public boolean add(TenantForm record) {
        int row = 0;
        try {
            Tenant tenant = transfer2Tenant.apply(record);
            row = tenantDao.insert(tenant);
        } catch (Exception e) {
            logger.error("add tenant error", e);
        }
        return row > 0;
    }

    @Override
    public boolean addList(List<TenantForm> records) {
        return false;
    }

    @Override
    public boolean update(TenantForm record) {
        Tenant tenant = transfer2UpdateTenant.apply(record);
        int row = 0;
        try {
            row = tenantDao.update(tenant);
        } catch (Exception e) {
            logger.error("update error", e);
        }
        return row > 0;
    }

    @Override
    public boolean updateList(List<TenantForm> records) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        int row = 0;
        try {
            row = tenantDao.delete(id);
        } catch (Exception e) {
            logger.error("delete error", e);
        }
        return row > 0;
    }

    @Override
    public boolean deleteList(List<Integer> ids) {
        return false;
    }
}
