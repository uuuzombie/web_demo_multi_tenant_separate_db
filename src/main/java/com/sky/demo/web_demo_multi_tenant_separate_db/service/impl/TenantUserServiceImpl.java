package com.sky.demo.web_demo_multi_tenant_separate_db.service.impl;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.Pager;
import com.sky.demo.web_demo_multi_tenant_separate_db.basedb.BaseDao;
import com.sky.demo.web_demo_multi_tenant_separate_db.dao.TenantUserDao;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantUserDto;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantUserForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantUserQueryRequest;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.Tenant;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.TenantUser;
import com.sky.demo.web_demo_multi_tenant_separate_db.service.TenantUserService;
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
public class TenantUserServiceImpl implements TenantUserService {

    private static final Logger logger = LoggerFactory.getLogger(TenantUserServiceImpl.class);

    @Resource
    private TenantUserDao tenantUserDao;

    private static final Function<TenantUserDto, TenantUserForm> transfer2Form = new Function<TenantUserDto, TenantUserForm>() {
        @Override
        public TenantUserForm apply(TenantUserDto input) {
            TenantUserForm tenantUserForm = new TenantUserForm();
            tenantUserForm.setId(input.getId());
            tenantUserForm.setUserName(input.getUserName());
            tenantUserForm.setCreateTime(DateFormatUtils.format(input.getCreateTime(), Constants.DATETIME_PATTERN));
            tenantUserForm.setStatus(TenantUser.Status.getStatusByCode(input.getStatus()));

            TenantForm tenantForm = new TenantForm();
            tenantForm.setId(input.getTenantId());
            tenantForm.setName(input.getTenantName());
            tenantForm.setToken(input.getTenantToken());
            tenantForm.setDbName(input.getTenantDbName());
            tenantForm.setCreateTime(DateFormatUtils.format(input.getCreateTime(), Constants.DATETIME_PATTERN));
            tenantForm.setStatus(Tenant.Status.getStatusByCode(input.getTenantStatus()));
            tenantUserForm.setTenant(tenantForm);

            return tenantUserForm;
        }
    };

    private static final Function<TenantUserForm, TenantUser> transfer2TenantUser = new Function<TenantUserForm, TenantUser>() {
        @Override
        public TenantUser apply(TenantUserForm input) {
            TenantUser tenantUser = new TenantUser();
            tenantUser.setTenantId(input.getTenant().getId());
            tenantUser.setUserName(input.getUserName());
            tenantUser.setCreateTime(new Date());
            tenantUser.setStatus(TenantUser.Status.NORMAL.getCode());
            return tenantUser;
        }
    };

    private static final Function<TenantUserForm, TenantUser> transfer2UpdateTenantUser = new Function<TenantUserForm, TenantUser>() {
        @Override
        public TenantUser apply(TenantUserForm input) {
            TenantUser tenantUser = new TenantUser();
            tenantUser.setId(input.getId());
            tenantUser.setTenantId(input.getTenant().getId());
            tenantUser.setUserName(input.getUserName());
            tenantUser.setCreateTime(new Date());
            tenantUser.setStatus(input.getStatus() == null ? TenantUser.Status.NORMAL.getCode() : input.getStatus().getCode());
            return tenantUser;
        }
    };

    @Override
    public TenantUserForm query(int id) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("id", id);
        condition.put("status", Tenant.Status.NORMAL.getCode());

        TenantUserForm result = null;
        TenantUserDto tenantUser = tenantUserDao.select(condition);
        if (tenantUser != null) {
            result = transfer2Form.apply(tenantUser);
        }
        return result;
    }

    @Override
    public TenantUserForm queryByUserName(String userName) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("userName", userName);
        condition.put("status", Tenant.Status.NORMAL.getCode());

        TenantUserForm result = null;
        TenantUserDto tenantUser = tenantUserDao.select(condition);
        if (tenantUser != null) {
            result = transfer2Form.apply(tenantUser);
        }
        return result;
    }

    @Override
    public List<TenantUserForm> queryList(List<Integer> ids) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("status", Tenant.Status.NORMAL.getCode());

        String strIds = Joiner.on(",").skipNulls().join(ids);
        condition.put("ids", strIds);

        List<TenantUserForm> result = Lists.newArrayList();
        List<TenantUserDto> tenantUsers = tenantUserDao.selectList(condition);
        if (CollectionUtils.isNotEmpty(tenantUsers)) {
            for (TenantUserDto tenantUser : tenantUsers) {
                TenantUserForm tenantUserForm = transfer2Form.apply(tenantUser);
                result.add(tenantUserForm);
            }
        }

        return result;
    }

    @Override
    public Pager<TenantUserForm> queryList(TenantUserQueryRequest request) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("userName", request.getUserName());
        condition.put("beginTime", request.getBeginDate() + " 00:00:00");
        condition.put("endTime", request.getEndDate() + " 23:59:59");
        condition.put("status", Tenant.Status.NORMAL.getCode());

        long totalRecord = tenantUserDao.selectCount(condition);
        Pager<TenantUserForm> ret = new Pager<TenantUserForm>(totalRecord, request.getPageNumber(), request.getPageSize());

        int limit = ret.getPageSize();
        long offset = (ret.getPageNumber() - 1) * ret.getPageSize();
        condition.put(BaseDao.LIMIT, limit);
        condition.put(BaseDao.OFFSET, offset);

        List<TenantUserForm> tenantUserForms = Lists.newArrayList();
        List<TenantUserDto> tenantUsers = tenantUserDao.selectList(condition);
        if (CollectionUtils.isNotEmpty(tenantUsers)) {
            for (TenantUserDto tenantUser : tenantUsers) {
                TenantUserForm tenantUserForm = transfer2Form.apply(tenantUser);
                tenantUserForms.add(tenantUserForm);
            }
        }

        ret.setRows(tenantUserForms);
        return ret;
    }

    @Override
    public boolean add(TenantUserForm record) {
        int row = 0;
        try {
            TenantUser tenantUser = transfer2TenantUser.apply(record);
            row = tenantUserDao.insert(tenantUser);
        } catch (Exception e) {
            logger.error("add tenant error", e);
        }
        return row > 0;
    }

    @Override
    public boolean addList(List<TenantUserForm> records) {
        return false;
    }

    @Override
    public boolean update(TenantUserForm record) {
        TenantUser tenantUser = transfer2UpdateTenantUser.apply(record);
        int row = tenantUserDao.update(tenantUser);
        return row > 0;
    }

    @Override
    public boolean updateList(List<TenantUserForm> records) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        int row = tenantUserDao.delete(id);
        return row > 0;
    }

    @Override
    public boolean deleteList(List<Integer> ids) {
        return false;
    }
}
