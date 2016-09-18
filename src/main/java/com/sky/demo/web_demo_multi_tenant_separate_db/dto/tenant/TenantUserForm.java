package com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant;

import com.google.common.base.Objects;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.TenantUser;

import java.io.Serializable;

/**
 * Created by user on 16/9/18.
 */
public class TenantUserForm implements Serializable {

    private static final long serialVersionUID = 69462514897448375L;
    private int id;
    private int tenantId;
    private String userName;
    private String password;
    private String createTime;
    private TenantUser.Status status;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public TenantUser.Status getStatus() {
        return status;
    }

    public void setStatus(TenantUser.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("tenantId", tenantId)
                .add("userName", userName)
                .add("password", password)
                .add("createTime", createTime)
                .add("status", status)
                .toString();
    }
}
