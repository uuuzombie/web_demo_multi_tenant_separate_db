package com.sky.demo.web_demo_multi_tenant_separate_db.service;


import com.sky.demo.web_demo_multi_tenant_separate_db.base.Pager;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.account.AccountQueryRequest;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.Account;

import java.util.List;

/**
 * Created by user on 16/9/19.
 */
public interface AccountService {

    public Account query(int id);

    public Account query(String userName, String password);

    public List<Account> queryList(List<Integer> ids);

    public Pager<Account> queryList(AccountQueryRequest queryRequest);

    public boolean add(Account record);

    public boolean addList(List<Account> records);

    public boolean update(Account record);

    public boolean updateList(List<Account> records);

    public boolean delete(int id);

    public boolean deleteList(List<Integer> ids);
}
