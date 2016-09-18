package com.sky.demo.web_demo_multi_tenant_separate_db.dao.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.sky.demo.web_demo_multi_tenant_separate_db.basedb.BaseDao;
import com.sky.demo.web_demo_multi_tenant_separate_db.dao.JdbcAnLogDao;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.AnLog;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 使用JdbcTemplate实现
 * Created by rg on 15/6/30.
 */
@Repository
public class JdbcAnLogDaoImpl extends BaseDao implements JdbcAnLogDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcAnLogDaoImpl.class);

    private static final String TABLE_NAME = "an_log";
    private static final String TABLE_COLUMN = "id, create_time, user_id, role_id, server_ip, client_ip, action_type," +
            " feature_type, action_info";
    private static final String INSERT_COLUMN = "create_time, user_id, role_id, server_ip, client_ip, action_type," +
            " feature_type, action_info";


    @Override
    public AnLog select(Map<String, Object> condition) {
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

        //方式一
        RowMapper<AnLog> rowMapper = BeanPropertyRowMapper.newInstance(AnLog.class);
        AnLog result = getJdbcTemplate().queryForObject(sql.toString(), params.toArray(), rowMapper);

        //方式二
//        AnLog result = getJdbcTemplate().queryForObject(sql.toString(), new ParameterizedRowMapper<AnLog>() {
//            @Override
//            public AnLog mapRow(ResultSet resultSet, int i) throws SQLException {
//                AnLog log = new AnLog();
//                log.setId(resultSet.getInt("id"));
//                log.setCreateTime(new Date(resultSet.getTimestamp("create_time").getTime()));
//                log.setUserId(resultSet.getInt("user_id"));
//                log.setRoleId(resultSet.getInt("role_id"));
//                log.setServerIp(resultSet.getString("server_ip"));
//                log.setClientIp(resultSet.getString("client_ip"));
//                log.setActionType(resultSet.getInt("action_type"));
//                log.setFeatureType(resultSet.getInt("feature_type"));
//                log.setActionInfo(resultSet.getString("action_info"));
//                return log;
//            }
//        }, id);

        //方式三
//        String sql = "select * from an_log where id = ?";
//        Map<String,Object> aMap = getJdbcTemplate().queryForMap(sql,new Object[]{id});
//
//        anLog.setId((Long) aMap.get("id"));
//        anLog.setCreateTime((Date) aMap.get("create_time"));
//        anLog.setUserId((Integer) aMap.get("user_id"));
//        anLog.setRoleId((Integer) aMap.get("role_id"));
//        anLog.setServerIp((String) aMap.get("server_ip"));
//        anLog.setClientIp((String) aMap.get("client_ip"));
//        anLog.setActionType((Integer) aMap.get("action_type"));
//        anLog.setFeatureType((Integer) aMap.get("feature_type"));
//        anLog.setActionInfo(aMap.get("action_info").toString());

        return result;
    }

    @Override
    public List<AnLog> selectList(Map<String, Object> condition) {
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

        // 方式一
        RowMapper<AnLog> rowMapper = BeanPropertyRowMapper.newInstance(AnLog.class);
        List<AnLog> result = getJdbcTemplate().query(sql.toString(), params.toArray(), rowMapper);

        //方式二
//        List<AnLog> result = Lists.newArrayList();
//        List<Map<String, Object>> queryForList = getJdbcTemplate().queryForList(sql.toString(), params.toArray());
//
//        for (Map<String,Object> aMap : queryForList) {
//            AnLog log = new AnLog();
//            log.setId((Long) aMap.get("id"));
//            log.setCreateTime((Date) aMap.get("create_time"));
//            log.setUserId((Integer) aMap.get("user_id"));
//            log.setRoleId((Integer) aMap.get("role_id"));
//            log.setServerIp((String) aMap.get("server_ip"));
//            log.setClientIp((String) aMap.get("client_ip"));
//            log.setActionType((Integer) aMap.get("action_type"));
//            log.setFeatureType((Integer) aMap.get("feature_type"));
//            log.setActionInfo(aMap.get("action_info").toString());
//
//            result.add(log);
//        }


        /*方式三
        List<AnLog> result = Lists.newArrayList();
        jdbcTemplate.query(sql.toString() ,new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                AnLog log = new AnLog();
                log.setId(rs.getLong("id"));
                log.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
                log.setUserId(rs.getInt("user_id"));
                log.setRoleId(rs.getInt("role_id"));
                log.setServerIp(rs.getString("server_ip"));
                log.setClientIp(rs.getString("client_ip"));
                log.setActionType(rs.getInt("action_type"));
                log.setFeatureType(rs.getInt("feature_type"));
                log.setActionInfo(rs.getString("action_info"));
                result.add(log);
            }
        });*/

        /*方式四
        List<AnLog> result = jdbcTemplate.query(sql.toString() ,new RowMapper<AnLog>() {
            @Override
            public AnLog mapRow(ResultSet rs, int rowNum) throws SQLException {
                AnLog log = new AnLog();
                log.setId(rs.getInt("id"));
                log.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
                log.setUserId(rs.getInt("user_id"));
                log.setRoleId(rs.getInt("role_id"));
                log.setServerIp(rs.getString("server_ip"));
                log.setClientIp(rs.getString("client_ip"));
                log.setActionType(rs.getInt("action_type"));
                log.setFeatureType(rs.getInt("feature_type"));
                log.setActionInfo(rs.getString("action_info"));
                return log;
            }
        });*/

        return result;
    }


    @Override
    public long selectCount(Map<String, Object> condition) {
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

        long count = getJdbcTemplate().queryForObject(sql.toString(), params.toArray(), Long.class);
        return count;
    }


    @Override
    public int insert(final AnLog record) {
        StringBuilder sql = new StringBuilder();
        String param = StringUtils.repeat("?", ",", INSERT_COLUMN.split(",").length);

        sql.append("insert into ").append(TABLE_NAME)
                .append(" (").append(INSERT_COLUMN).append(") ")
                .append("values (").append(param).append(") ");

        List<Object> params = Lists.newArrayList();
        params.add(record.getCreateTime());
        params.add(record.getUserId());
        params.add(record.getRoleId());
        params.add(record.getServerIp());
        params.add(record.getClientIp());
        params.add(record.getActionType());
        params.add(record.getFeatureType());
        params.add(record.getActionInfo());

        //方式一
        int row = getJdbcTemplate().update(sql.toString(), params.toArray());

        //方式二
//        int row = jdbcTemplate.update(sql.toString(), new PreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps) throws SQLException {
//                ps.setTimestamp(1, new Timestamp(record.getCreateTime().getTime()));
//                ps.setInt(2, record.getUserId());
//                ps.setInt(3, record.getRoleId());
//                ps.setString(4, record.getServerIp());
//                ps.setString(5, record.getClientIp());
//                ps.setInt(6, record.getActionType());
//                ps.setInt(7, record.getFeatureType());
//                ps.setString(8, record.getActionInfo());
//            }
//        });



        //返回自增主键
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        int row = jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                PreparedStatement ps = con.prepareStatement(sql.toString(),new String[] {"id"}); //jdbcTemplate.getDataSource().getConnection()  Statement.RETURN_GENERATED_KEYS
//                ps.setTimestamp(1, new Timestamp(record.getCreateTime().getTime()));
//                ps.setInt(2, record.getUserId());
//                ps.setInt(3, record.getRoleId());
//                ps.setString(4, record.getServerIp());
//                ps.setString(5, record.getClientIp());
//                ps.setInt(6, record.getActionType());
//                ps.setInt(7, record.getFeatureType());
//                ps.setString(8, record.getActionInfo());
//                return ps;
//            }
//        },keyHolder);
//        long generateKey = keyHolder.getKey().longValue();
//        logger.info("generate key : " + generateKey);
//        return generateKey;


//        Object[] args = new Object[]{
//                new Timestamp(record.getCreateTime().getTime()),
//                record.getUserId(),
//                record.getRoleId(),
//                record.getServerIp(),
//                record.getClientIp(),
//                record.getActionType(),
//                record.getFeatureType(),
//                record.getActionInfo()
//        };
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        int row = jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                PreparedStatement ps = con.prepareStatement(sql.toString(),new String[] {"id"}); //jdbcTemplate.getDataSource().getConnection()  Statement.RETURN_GENERATED_KEYS
//                for (int i = 0;i < args.length ; ++i) {
//                    ps.setObject(i + 1, args[i]);
//                }
//                return ps;
//            }
//        },keyHolder);
//        long generateKey = keyHolder.getKey().longValue();
//        logger.info("generate key : " + generateKey);
//        return generateKey;

        return row;
    }

    @Override
    public int batchInsert(final List<AnLog> recordList) {
        StringBuilder sql = new StringBuilder();
        String param = StringUtils.repeat("?", ",", INSERT_COLUMN.split(",").length);

        sql.append("insert into ").append(TABLE_NAME)
                .append(" (").append(INSERT_COLUMN).append(") ")
                .append("values (").append(param).append(") ");


        //方式一
        BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setTimestamp(1, new Timestamp(recordList.get(i).getCreateTime().getTime()));
                ps.setInt(2, recordList.get(i).getUserId());
                ps.setInt(3, recordList.get(i).getRoleId());
                ps.setString(4, recordList.get(i).getServerIp());
                ps.setString(5, recordList.get(i).getClientIp());
                ps.setInt(6, recordList.get(i).getActionType());
                ps.setInt(7, recordList.get(i).getFeatureType());
                ps.setString(8, recordList.get(i).getActionInfo());
            }

            @Override
            public int getBatchSize() {
                return recordList.size();
            }
        };

        int[] rows = getJdbcTemplate().batchUpdate(sql.toString(), setter);


        //方式二
//        List<Object[]> args = Lists.newArrayList();
//        for (AnLog record : recordList) {
//            Object[] obj = new Object[] {
//                    new Timestamp(record.getCreateTime().getTime()),
//                    record.getUserId(),
//                    record.getRoleId(),
//                    record.getServerIp(),
//                    record.getClientIp(),
//                    record.getActionType(),
//                    record.getFeatureType(),
//                    record.getActionInfo()
//            };
//            args.add(obj);
//        }
//        int[] rows = getJdbcTemplate().batchUpdate(sql.toString(),args);

        return rows.length;
    }

    @Override
    public int update(final AnLog record) {
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(TABLE_NAME)
                .append("set action_type = ?,feature_type = ?,action_info = ? ")
                .append(" where 1 = 1 ");

        List<Object> params = Lists.newArrayList();
        params.add(record.getActionType());
        params.add(record.getFeatureType());
        params.add(record.getActionInfo());

        sql.append("and id = ? ");
        params.add(record.getId());

        //方式一
        int row = getJdbcTemplate().update(sql.toString(), params.toArray());

        //方式二
//        int row = getJdbcTemplate().update(sql.toString(), new PreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps) throws SQLException {
//                ps.setInt(1, record.getActionType());
//                ps.setInt(2, record.getFeatureType());
//                ps.setString(3, record.getActionInfo());
//                ps.setLong(4, record.getId());
//            }
//        });

        return row;
    }

    @Override
    public int batchUpdate(List<AnLog> records) {
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(TABLE_NAME)
                .append("set action_type = ?,feature_type = ?,action_info = ? ")
                .append(" where 1 = 1 ");

        BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setTimestamp(1, new Timestamp(records.get(i).getCreateTime().getTime()));
                ps.setInt(2, records.get(i).getUserId());
                ps.setInt(3, records.get(i).getRoleId());
                ps.setString(4, records.get(i).getServerIp());
                ps.setString(5, records.get(i).getClientIp());
                ps.setInt(6, records.get(i).getActionType());
                ps.setInt(7, records.get(i).getFeatureType());
                ps.setString(8, records.get(i).getActionInfo());
            }

            @Override
            public int getBatchSize() {
                return records.size();
            }
        };

        int[] rows = getJdbcTemplate().batchUpdate(sql.toString(), setter);
        return rows.length;
    }


    @Override
    public int delete(final Long id) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(TABLE_NAME)
                .append(" where 1 = 1 ");

        List<Object> params = Lists.newArrayList();
        sql.append("and id = ? ");
        params.add(id);

        //方式一
        int row = getJdbcTemplate().update(sql.toString(), params.toArray());

        //方式二
//        int row = getJdbcTemplate().update(sql,new PreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps) throws SQLException {
//                ps.setObject(1,id);
//            }
//        });

        return row;
    }

    @Override
    public int batchDelete(List<Long> ids) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(TABLE_NAME)
                .append(" where 1 = 1 ");

        String strIds = Joiner.on(",").skipNulls().join(ids);
        sql.append("and id in (").append(strIds).append(") ");

        int row = getJdbcTemplate().update(sql.toString());
        return row;
    }

}
