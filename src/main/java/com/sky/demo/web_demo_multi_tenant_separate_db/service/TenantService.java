package com.sky.demo.web_demo_multi_tenant_separate_db.service;

import com.sky.demo.web_demo_multi_tenant_separate_db.base.Pager;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantQueryRequest;

import java.util.List;

/**
 * Created by user on 16/9/18.
 */
public interface TenantService {

    public TenantForm query(int id);

    public TenantForm queryByName(String name);

    public TenantForm queryByClientId(String clientId);

    public TenantForm queryByDeviceId(String deviceId);

    public TenantForm queryByDeviceToken(String deviceToken);

    public TenantForm queryByDbName(String dbName);

    public List<TenantForm> queryList(List<Integer> ids);

    public Pager<TenantForm> queryList(TenantQueryRequest queryRequest);

    public boolean add(TenantForm record);

    public boolean addList(List<TenantForm> records);

    public boolean update(TenantForm record);

    public boolean updateList(List<TenantForm> records);

    public boolean delete(int id);

    public boolean deleteList(List<Integer> ids);
}
