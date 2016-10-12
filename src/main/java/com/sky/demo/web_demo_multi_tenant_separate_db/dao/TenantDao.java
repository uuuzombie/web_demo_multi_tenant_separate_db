package com.sky.demo.web_demo_multi_tenant_separate_db.dao;

import com.sky.demo.web_demo_multi_tenant_separate_db.model.Tenant;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 16/9/18.
 */
public interface TenantDao {

    public Tenant select(Map<String, Object> condition);

    public List<Tenant> selectList(Map<String, Object> condition);

    public int selectCount(Map<String, Object> condition);

    public int insert(Tenant record);

    public int batchInsert(List<Tenant> records);

    public int update(Tenant record);

    public int batchUpdate(List<Tenant> records);

    public int delete(final int id);

    public int batchDelete(List<Integer> ids);
}
