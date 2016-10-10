package com.sky.demo.web_demo_multi_tenant_separate_db.model;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 16/9/18.
 */
public class Tenant implements Serializable {

    private static final long serialVersionUID = -1394462897506118206L;
    private int id;
    private String name;
    private String clientId;
    private String deviceId;
    private String deviceToken;
    private String dbName;
    private Date createTime;
    private int status;

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
                .add("name", name)
                .add("clientId", clientId)
                .add("deviceId", deviceId)
                .add("deviceToken", deviceToken)
                .add("dbName", dbName)
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

        public static Status getStatusByCode(int code) {
            for (Status status : Status.values()) {
                if (code == status.getCode()) {
                    return status;
                }
            }
            return null;
        }
    }
}
