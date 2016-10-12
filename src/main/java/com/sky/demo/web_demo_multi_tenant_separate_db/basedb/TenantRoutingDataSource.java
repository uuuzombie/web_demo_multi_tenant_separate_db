package com.sky.demo.web_demo_multi_tenant_separate_db.basedb;

import com.sky.demo.web_demo_multi_tenant_separate_db.context.DBContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by user on 16/10/12.
 */
public class TenantRoutingDataSource extends AbstractRoutingDataSource{


    @Override
    protected Object determineCurrentLookupKey() {
        return DBContext.getDbKey();
    }


    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = super.getConnection();
        changeTenant(connection);
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection connection = super.getConnection(username, password);
        changeTenant(connection);
        return connection;
    }

    /**
     * 切换租户数据库  使用 \c tenant_db
     * @param connection
     */
    private void changeTenant(Connection connection) {

    }


}
