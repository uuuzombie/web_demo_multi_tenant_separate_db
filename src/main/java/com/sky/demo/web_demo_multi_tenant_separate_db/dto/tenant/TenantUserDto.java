package com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 16/9/23.
 */
public class TenantUserDto implements Serializable {

    private static final long serialVersionUID = -1812985166632918032L;
    private int id;
    private int tenantId;
    private String userName;
    private Date createTime;
    private int status;

    private String tenantClientId;
    private String tenantDeviceId;
    private String tenantDeviceToken;
    private String tenantName;
    private String tenantDbName;
    private Date tenantCreateTime;
    private int tenantStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTenantClientId() {
        return tenantClientId;
    }

    public void setTenantClientId(String tenantClientId) {
        this.tenantClientId = tenantClientId;
    }

    public String getTenantDeviceId() {
        return tenantDeviceId;
    }

    public void setTenantDeviceId(String tenantDeviceId) {
        this.tenantDeviceId = tenantDeviceId;
    }

    public String getTenantDeviceToken() {
        return tenantDeviceToken;
    }

    public void setTenantDeviceToken(String tenantDeviceToken) {
        this.tenantDeviceToken = tenantDeviceToken;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantDbName() {
        return tenantDbName;
    }

    public void setTenantDbName(String tenantDbName) {
        this.tenantDbName = tenantDbName;
    }

    public Date getTenantCreateTime() {
        return tenantCreateTime;
    }

    public void setTenantCreateTime(Date tenantCreateTime) {
        this.tenantCreateTime = tenantCreateTime;
    }

    public int getTenantStatus() {
        return tenantStatus;
    }

    public void setTenantStatus(int tenantStatus) {
        this.tenantStatus = tenantStatus;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("tenantId", tenantId)
                .add("userName", userName)
                .add("createTime", createTime)
                .add("status", status)
                .add("tenantClientId", tenantClientId)
                .add("tenantDeviceId", tenantDeviceId)
                .add("tenantDeviceToken", tenantDeviceToken)
                .add("tenantName", tenantName)
                .add("tenantDbName", tenantDbName)
                .add("tenantCreateTime", tenantCreateTime)
                .add("tenantStatus", tenantStatus)
                .toString();
    }
}
