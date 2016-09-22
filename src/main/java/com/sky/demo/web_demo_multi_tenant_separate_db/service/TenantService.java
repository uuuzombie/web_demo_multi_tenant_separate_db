package com.sky.demo.web_demo_multi_tenant_separate_db.service;

import com.sky.demo.web_demo_multi_tenant_separate_db.base.Pager;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantQueryRequest;

import java.util.List;

/**
 * Created by user on 16/9/18.
 */
public interface TenantService {

    TenantForm query(int id);

    TenantForm queryByName(String name);

    TenantForm queryByToken(String token);

    List<TenantForm> queryList(List<Integer> ids);

    Pager<TenantForm> queryList(TenantQueryRequest queryRequest);

    boolean add(TenantForm record);

    boolean addList(List<TenantForm> records);

    boolean update(TenantForm record);

    boolean updateList(List<TenantForm> records);

    boolean delete(int id);

    boolean deleteList(List<Integer> ids);
}
