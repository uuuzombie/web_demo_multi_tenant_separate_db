package com.sky.demo.web_demo_multi_tenant_separate_db.model;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 16/9/18.
 */
public class TenantUser implements Serializable {

    private static final long serialVersionUID = 2358425942143481342L;
    private int id;
    private int tenantId;
    private String userName;
    private String password;
    private Date createTime;
    private int status;

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


    public enum Status {

        NORMAL(1, "NORMAL"),
        DELETED(2, "DELETED"),
        ;

        private int code;
        private String desc;

        Status(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("code", code)
                    .add("desc", desc)
                    .toString();
        }
    }
}
