package com.sky.demo.web_demo_multi_tenant_separate_db.dao.impl;

import com.google.common.collect.Lists;
import com.sky.demo.web_demo_multi_tenant_separate_db.basedb.BaseDao;
import com.sky.demo.web_demo_multi_tenant_separate_db.dao.TenantUserDao;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant.TenantUserDto;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.TenantUser;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 16/9/18.
 */
@Repository
public class TenantUserDaoImpl extends BaseDao implements TenantUserDao {

    private static final Logger logger = LoggerFactory.getLogger(TenantUserDaoImpl.class);

    private static final String TABLE_NAME = "tenant_user";
    private static final String TABLE_COLUMN = "id, tenant_id, user_name, create_time, status";
    private static final String INSERT_COLUMN = "tenant_id, user_name, create_time, status";
    private static final String QUERY_COLUMN = "tba.id as id, tba.tenant_id as tenantId, tba.user_name as userName, tba.create_time as createTime, tba.status as status, "
            + "tbb.name as tenantName, tbb.token as tenantToken, tbb.db_name as tenantDbName, tbb.create_time as tenantCreateTime, tbb.status as tenantStatus";

    @Override
    public TenantUserDto select(Map<String, Object> condition) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(QUERY_COLUMN)
                .append(" from ").append(TABLE_NAME).append(" as tba, tenant as tbb ")
                .append(" where 1 = 1 ")
                .append(" and tba.tenant_id = tbb.id ");

        List<Object> params = Lists.newArrayList();
        Integer id = (Integer) condition.get("id");
        if (id != null) {
            sql.append("and tba.id = ? ");
            params.add(id);
        }

        String userName = (String) condition.get("userName");
        if (StringUtils.isNotBlank(userName)) {
            sql.append("and tba.user_name = ? ");
            params.add(userName);
        }

        String beginTime = (String) condition.get("beginTime");
        if (StringUtils.isNotEmpty(beginTime)) {
            sql.append("and tba.create_time >= ? ");
            params.add(Timestamp.valueOf(beginTime));
        }

        String endTime = (String) condition.get("endTime");
        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("and tba.create_time < ? ");
            params.add(Timestamp.valueOf(endTime));
        }

        Integer status = (Integer) condition.get("status");
        if (status != null) {
            sql.append("and tba.status = ? ");
            params.add(status);
        }

        RowMapper<TenantUserDto> rowMapper = BeanPropertyRowMapper.newInstance(TenantUserDto.class);
        TenantUserDto result = getDefaultJdbcTemplate().queryForObject(sql.toString(), params.toArray(), rowMapper);
        return result;
    }

    @Override
    public List<TenantUserDto> selectList(Map<String, Object> condition) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(QUERY_COLUMN)
                .append(" from ").append(TABLE_NAME).append(" as tba, tenant as tbb ")
                .append(" where 1 = 1 ")
                .append(" and tba.tenant_id = tbb.id ");

        List<Object> params = Lists.newArrayList();
        String userName = (String) condition.get("userName");
        if (StringUtils.isNotBlank(userName)) {
            sql.append("and tba.user_name = ? ");
            params.add(userName);
        }

        String beginTime = (String) condition.get("beginTime");
        if (StringUtils.isNotEmpty(beginTime)) {
            sql.append("and tba.create_time >= ? ");
            params.add(Timestamp.valueOf(beginTime));
        }

        String endTime = (String) condition.get("endTime");
        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("and tba.create_time < ? ");
            params.add(Timestamp.valueOf(endTime));
        }

        Integer status = (Integer) condition.get("status");
        if (status != null) {
            sql.append("and tba.status = ? ");
            params.add(status);
        }

        String ids = (String) condition.get("ids");
        if (StringUtils.isNotBlank(ids)) {
            sql.append("and tba.id in (").append(ids).append(") ");
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

        RowMapper<TenantUserDto> rowMapper = BeanPropertyRowMapper.newInstance(TenantUserDto.class);
        List<TenantUserDto> result = getDefaultJdbcTemplate().query(sql.toString(), params.toArray(), rowMapper);

        return result;
    }

    @Override
    public int selectCount(Map<String, Object> condition) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(*) ")
                .append(" from ").append(TABLE_NAME).append(" as tba, tenant as tbb ")
                .append(" where 1 = 1 ")
                .append(" and tba.tenant_id = tbb.id ");

        List<Object> params = Lists.newArrayList();
        String userName = (String) condition.get("userName");
        if (StringUtils.isNotBlank(userName)) {
            sql.append("and tba.user_name = ? ");
            params.add(userName);
        }

        String beginTime = (String) condition.get("beginTime");
        if (StringUtils.isNotEmpty(beginTime)) {
            sql.append("and tba.create_time >= ? ");
            params.add(Timestamp.valueOf(beginTime));
        }

        String endTime = (String) condition.get("endTime");
        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("and tba.create_time < ? ");
            params.add(Timestamp.valueOf(endTime));
        }

        Integer status = (Integer) condition.get("status");
        if (status != null) {
            sql.append("and tba.status = ? ");
            params.add(status);
        }

        String ids = (String) condition.get("ids");
        if (StringUtils.isNotBlank(ids)) {
            sql.append("and tba.id in (").append(ids).append(") ");
        }

        int count = getDefaultJdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
        return count;
    }

    @Override
    public int insert(TenantUser record) {
        StringBuilder sql = new StringBuilder();
        String param = StringUtils.repeat("?", ",", INSERT_COLUMN.split(",").length);

        sql.append("insert into ").append(TABLE_NAME)
                .append(" (").append(INSERT_COLUMN).append(") ")
                .append("values (").append(param).append(") ");

        List<Object> params = Lists.newArrayList();
        params.add(record.getTenantId());
        params.add(record.getUserName());
        params.add(record.getCreateTime());
        params.add(record.getStatus());

        int row = getDefaultJdbcTemplate().update(sql.toString(), params.toArray());
        return row;
    }

    @Override
    public int batchInsert(List<TenantUser> records) {
        return 0;
    }

    @Override
    public int update(TenantUser record) {
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(TABLE_NAME)
                .append(" set tenant_id = ?, user_name = ?, status = ? ")
                .append(" where 1 = 1 ");

        List<Object> params = Lists.newArrayList();
        params.add(record.getTenantId());
        params.add(record.getUserName());
        params.add(record.getStatus());

        sql.append("and id = ? ");
        params.add(record.getId());

        int row = getDefaultJdbcTemplate().update(sql.toString(), params.toArray());
        return row;
    }

    @Override
    public int batchUpdate(List<TenantUser> records) {
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

        int row = getDefaultJdbcTemplate().update(sql.toString(), params.toArray());
        return row;
    }

    @Override
    public int batchDelete(List<Integer> ids) {
        return 0;
    }
}
