package com.sky.demo.web_demo_multi_tenant_separate_db.dao;

import com.sky.demo.web_demo_multi_tenant_separate_db.model.Account;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 16/9/18.
 */
public interface AccountDao {

    public Account select(Map<String, Object> condition);

    public List<Account> selectList(Map<String, Object> condition);

    public int selectCount(Map<String, Object> condition);

    public int insert(Account record);

    public int batchInsert(List<Account> records);

    public int update(Account record);

    public int batchUpdate(List<Account> records);

    public int delete(final int id);

    public int batchDelete(List<Integer> ids);
}
