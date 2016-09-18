package com.sky.demo.web_demo_multi_tenant_separate_db.dao.impl;

import com.google.common.collect.Lists;
import com.sky.demo.web_demo_multi_tenant_separate_db.basedb.BaseDao;
import com.sky.demo.web_demo_multi_tenant_separate_db.dao.TenantDao;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.Tenant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 16/9/18.
 */
public class TenantDaoImpl extends BaseDao implements TenantDao {

    private static final Logger logger = LoggerFactory.getLogger(TenantDaoImpl.class);

    private static final String TABLE_NAME = "tenant";
    private static final String TABLE_COLUMN = "id, name, db_name, create_time, status";
    private static final String INSERT_COLUMN = "name, db_name, create_time, status";

    @Override
    public Tenant select(Map<String, Object> condition) {
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

        String beginTime = (String) condition.get("beginTime");
        if (StringUtils.isNotEmpty(beginTime)) {
            sql.append("and create_time >= ? ");
            params.add(Timestamp.valueOf(beginTime));
        }

        String endTime = (String) condition.get("endTime");
        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("and create_time < ? ");
            params.add(Timestamp.valueOf(endTime));
        }

        RowMapper<Tenant> rowMapper = BeanPropertyRowMapper.newInstance(Tenant.class);
        Tenant result = getJdbcTemplate().queryForObject(sql.toString(), params.toArray(), rowMapper);

        return result;
    }

    @Override
    public List<Tenant> selectList(Map<String, Object> condition) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(TABLE_COLUMN)
                .append("from ").append(TABLE_NAME)
                .append(" where 1 = 1 ");

        List<Object> params = Lists.newArrayList();
        String beginTime = (String) condition.get("beginTime");
        if (StringUtils.isNotEmpty(beginTime)) {
            sql.append("and create_time >= ? ");
            params.add(Timestamp.valueOf(beginTime));
        }

        String endTime = (String) condition.get("endTime");
        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("and create_time < ? ");
            params.add(Timestamp.valueOf(endTime));
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

        logger.info("select * params:" + params);

        RowMapper<Tenant> rowMapper = BeanPropertyRowMapper.newInstance(Tenant.class);
        List<Tenant> result = getJdbcTemplate().query(sql.toString(), params.toArray(), rowMapper);

        return result;
    }

    @Override
    public int selectCount(Map<String, Object> condition) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(*) ")
                .append("from ").append(TABLE_NAME)
                .append(" where 1 = 1 ");


        List<Object> params = Lists.newArrayList();
        String beginTime = (String) condition.get("beginTime");
        if (StringUtils.isNotEmpty(beginTime)) {
            sql.append("and create_time >= ? ");
            params.add(Timestamp.valueOf(beginTime));
        }

        String endTime = (String) condition.get("endTime");
        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("and create_time < ? ");
            params.add(Timestamp.valueOf(endTime));
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

        int count = getJdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
        return count;
    }

    @Override
    public int insert(Tenant record) {
        StringBuilder sql = new StringBuilder();
        String param = StringUtils.repeat("?", ",", INSERT_COLUMN.split(",").length);

        sql.append("insert into ").append(TABLE_NAME)
                .append(" (").append(INSERT_COLUMN).append(") ")
                .append("values (").append(param).append(") ");

        List<Object> params = Lists.newArrayList();
        params.add(record.getName());
        params.add(record.getDbName());
        params.add(record.getCreateTime());
        params.add(record.getStatus());

        int row = getJdbcTemplate().update(sql.toString(), params.toArray());
        return row;
    }

    @Override
    public int batchInsert(List<Tenant> records) {
        return 0;
    }

    @Override
    public int update(Tenant record) {
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(TABLE_NAME)
                .append("set name = ?,db_name = ?,status = ? ")
                .append(" where 1 = 1 ");

        List<Object> params = Lists.newArrayList();
        params.add(record.getName());
        params.add(record.getDbName());
        params.add(record.getStatus());

        sql.append("and id = ? ");
        params.add(record.getId());

        int row = getJdbcTemplate().update(sql.toString(), params.toArray());
        return row;
    }

    @Override
    public int batchUpdate(List<Tenant> records) {
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