package com.sky.demo.web_demo_multi_tenant_separate_db.service;

import com.sky.demo.web_demo_multi_tenant_separate_db.base.Pager;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantUserForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantUserQueryRequest;

import java.util.List;

/**
 * Created by user on 16/9/18.
 */
public interface TenantUserService {

    public TenantUserForm query(int id);

    public TenantUserForm queryByUserName(String userName);

    public List<TenantUserForm> queryList(List<Integer> ids);

    public Pager<TenantUserForm> queryList(TenantUserQueryRequest queryRequest);

    public boolean add(TenantUserForm record);

    public boolean addList(List<TenantUserForm> records);

    public boolean update(TenantUserForm record);

    public boolean updateList(List<TenantUserForm> records);

    public boolean delete(int id);

    public boolean deleteList(List<Integer> ids);
}
