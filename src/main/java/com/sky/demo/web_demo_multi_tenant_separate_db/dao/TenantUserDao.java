package com.sky.demo.web_demo_multi_tenant_separate_db.dao;

import com.sky.demo.web_demo_multi_tenant_separate_db.model.TenantUser;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 16/9/18.
 */
public interface TenantUserDao {

    TenantUser select(Map<String, Object> condition);

    List<TenantUser> selectList(Map<String, Object> condition);

    int selectCount(Map<String, Object> condition);

    int insert(TenantUser record);

    int batchInsert(List<TenantUser> records);

    int update(TenantUser record);

    int batchUpdate(List<TenantUser> records);

    int delete(final int id);

    int batchDelete(List<Integer> ids);
}
