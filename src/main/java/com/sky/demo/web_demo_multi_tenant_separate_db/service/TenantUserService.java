package com.sky.demo.web_demo_multi_tenant_separate_db.service;

import com.sky.demo.web_demo_multi_tenant_separate_db.base.Pager;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantUserForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantUserQueryRequest;

import java.util.List;

/**
 * Created by user on 16/9/18.
 */
public interface TenantUserService {

    TenantUserForm query(int id);

    TenantUserForm queryByUserName(String userName);

    List<TenantUserForm> queryList(List<Integer> ids);

    Pager<TenantUserForm> queryList(TenantUserQueryRequest queryRequest);

    boolean add(TenantUserForm record);

    boolean addList(List<TenantUserForm> records);

    boolean update(TenantUserForm record);

    boolean updateList(List<TenantUserForm> records);

    boolean delete(int id);

    boolean deleteList(List<Integer> ids);
}
