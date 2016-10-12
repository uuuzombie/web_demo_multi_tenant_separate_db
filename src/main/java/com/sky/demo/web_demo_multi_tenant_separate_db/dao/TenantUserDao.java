package com.sky.demo.web_demo_multi_tenant_separate_db.dao;

import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantUserDto;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.TenantUser;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 16/9/18.
 */
public interface TenantUserDao {

    public TenantUserDto select(Map<String, Object> condition);

    public List<TenantUserDto> selectList(Map<String, Object> condition);

    public int selectCount(Map<String, Object> condition);

    public int insert(TenantUser record);

    public int batchInsert(List<TenantUser> records);

    public int update(TenantUser record);

    public int batchUpdate(List<TenantUser> records);

    public int delete(final int id);

    public int batchDelete(List<Integer> ids);
}
