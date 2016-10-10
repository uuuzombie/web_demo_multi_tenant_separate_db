package com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant;

import com.google.common.base.Objects;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.Tenant;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by user on 16/9/18.
 */
public class TenantForm implements Serializable {

    private static final long serialVersionUID = 5802983685287791418L;
    private int id;
    private String name;
    private String clientId;
    private String deviceId;
    private String deviceToken;
    private String dbName;
    private String createTime;
    private Tenant.Status status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Tenant.Status getStatus() {
        return status;
    }

    public void setStatus(Tenant.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("clientId", clientId)
                .add("deviceId", deviceId)
                .add("deviceToken", deviceToken)
                .add("dbName", dbName)
                .add("createTime", createTime)
                .add("status", status)
                .toString();
    }
}
