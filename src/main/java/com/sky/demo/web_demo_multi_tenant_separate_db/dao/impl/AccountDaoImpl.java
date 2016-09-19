package com.sky.demo.web_demo_multi_tenant_separate_db.dao.impl;

import com.google.common.collect.Lists;
import com.sky.demo.web_demo_multi_tenant_separate_db.basedb.BaseDao;
import com.sky.demo.web_demo_multi_tenant_separate_db.dao.AccountDao;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.Account;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 16/9/19.
 */
@Repository
public class AccountDaoImpl extends BaseDao implements AccountDao {

    private static final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);

    private static final String TABLE_NAME = "account";
    private static final String TABLE_COLUMN = "id, user_name, password";
    private static final String INSERT_COLUMN = "user_name, password";

    @Override
    public Account select(Map<String, Object> condition) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(TABLE_COLUMN)
                .append("from ").append(TABLE_NAME)
                .append(" where 1 = 1 ");

        List<Object> params = Lists.newArrayList();
        Long id = (Long) condition.get("id");
        if (id != null) {
            sql.append("and id = ? ");
            params.add(id);
        }

        String userName = (String) condition.get("userName");
        if (StringUtils.isNotEmpty(userName)) {
            sql.append("and user_name >= ? ");
            params.add(userName);
        }

        RowMapper<Account> rowMapper = BeanPropertyRowMapper.newInstance(Account.class);
        Account result = getJdbcTemplate().queryForObject(sql.toString(), params.toArray(), rowMapper);

        return result;
    }

    @Override
    public List<Account> selectList(Map<String, Object> condition) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(TABLE_COLUMN)
                .append("from ").append(TABLE_NAME)
                .append(" where 1 = 1 ");

        List<Object> params = Lists.newArrayList();
        String userName = (String) condition.get("userName");
        if (StringUtils.isNotEmpty(userName)) {
            sql.append("and user_name >= ? ");
            params.add(userName);
        }

        Integer limit = (Integer) condition.get(LIMIT);
        if (limit != null) {
            sql.append("limit ? ");
            params.add(limit);
        }

        Long offset = (Long) condition.get(OFFSET);
        if (offset != null) {
            sql.append("offset ? ");
            params.add(offset);
        }

        RowMapper<Account> rowMapper = BeanPropertyRowMapper.newInstance(Account.class);
        List<Account> result = getJdbcTemplate().query(sql.toString(), params.toArray(), rowMapper);

        return result;
    }

    @Override
    public int selectCount(Map<String, Object> condition) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(*) ")
                .append("from ").append(TABLE_NAME)
                .append(" where 1 = 1 ");


        List<Object> params = Lists.newArrayList();

        int count = getJdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
        return count;
    }

    @Override
    public int insert(Account record) {
        StringBuilder sql = new StringBuilder();
        String param = StringUtils.repeat("?", ",", INSERT_COLUMN.split(",").length);

        sql.append("insert into ").append(TABLE_NAME)
                .append(" (").append(INSERT_COLUMN).append(") ")
                .append("values (").append(param).append(") ");

        List<Object> params = Lists.newArrayList();
        params.add(record.getUserName());
        params.add(record.getPassword());

        int row = getJdbcTemplate().update(sql.toString(), params.toArray());
        return row;
    }

    @Override
    public int batchInsert(List<Account> records) {
        return 0;
    }

    @Override
    public int update(Account record) {
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(TABLE_NAME)
                .append("set user_name = ?, password = ? ")
                .append(" where 1 = 1 ");

        List<Object> params = Lists.newArrayList();
        params.add(record.getUserName());
        params.add(record.getPassword());

        sql.append("and id = ? ");
        params.add(record.getId());

        int row = getJdbcTemplate().update(sql.toString(), params.toArray());
        return row;
    }

    @Override
    public int batchUpdate(List<Account> records) {
        return 0;
    }

    @Override
    public int delete(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(TABLE_NAME)
                .append(" where 1 = 1 ");

        List<Object> params = Lists.newArrayList();
        sql.append("and id = ? ");
        params.add(id);

        int row = getJdbcTemplate().update(sql.toString(), params.toArray());
        return row;
    }

    @Override
    public int batchDelete(List<Integer> ids) {
        return 0;
    }
}
