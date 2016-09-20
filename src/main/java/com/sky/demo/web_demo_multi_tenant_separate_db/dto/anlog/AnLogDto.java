package com.sky.demo.web_demo_multi_tenant_separate_db.dto.anlog;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 16/9/18.
 */
public class AnLogDto implements Serializable {

    private static final long serialVersionUID = -2108101602187539283L;
    private long id;
    private Date createTime;
    private String userName;
    private String roleName;
    private String serverIp;
    private String clientIp;
    private int actionType;
    private int featureType;
    private String actionInfo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public int getFeatureType() {
        return featureType;
    }

    public void setFeatureType(int featureType) {
        this.featureType = featureType;
    }

    public String getActionInfo() {
        return actionInfo;
    }

    public void setActionInfo(String actionInfo) {
        this.actionInfo = actionInfo;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("createTime", createTime)
                .add("userName", userName)
                .add("roleName", roleName)
                .add("serverIp", serverIp)
                .add("clientIp", clientIp)
                .add("actionType", actionType)
                .add("featureType", featureType)
                .add("actionInfo", actionInfo)
                .toString();
    }
}
