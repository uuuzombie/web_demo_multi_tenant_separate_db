package com.sky.demo.web_demo_multi_tenant_separate_db.dto.anlog;

import java.io.Serializable;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.ActionType;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.FeatureType;
import com.sky.demo.web_demo_multi_tenant_separate_db.util.json.JsonUtil;

/**
 * Created by rg on 2015/6/30.
 */
public class AnLogInsertRequest implements Serializable{

    private static final long serialVersionUID = 2484108793572247915L;

    private int userId;         //管理员的user id
    private int roleId;         //管理员的role id
    private String clientIp;    //客户端ip
    private ActionType actionType;     //操作类型
    private FeatureType featureType;    //模块类型
    private List<BaseAnActionInfo> actionInfo;  //操作内容

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
    }

    public List<BaseAnActionInfo> getActionInfo() {
        return actionInfo;
    }

    public void setActionInfo(List<BaseAnActionInfo> actionInfo) {
        this.actionInfo = actionInfo;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("userId", userId)
                .add("roleId", roleId)
                .add("clientIp", clientIp)
                .add("actionType", actionType)
                .add("featureType", featureType)
                .add("actionInfo", actionInfo)
                .toString();
    }

    public static void main(String[] args) {
        AnLogInsertRequest insertRequest = new AnLogInsertRequest();
        insertRequest.setUserId(1);
        insertRequest.setRoleId(1);
        insertRequest.setClientIp("172.22.100.1");
        insertRequest.setActionType(ActionType.LOG_OFF);
        insertRequest.setFeatureType(FeatureType.LOGOFF);

        List<BaseAnActionInfo> anActionInfos = Lists.newArrayList();
        BaseAnActionInfo actionInfo = new BaseAnActionInfo();
        actionInfo.setElement("user");
        actionInfo.setPrevious("login");
        actionInfo.setCurrent("logoff");
        anActionInfos.add(actionInfo);
        insertRequest.setActionInfo(anActionInfos);

        String json = JsonUtil.writeValueAsString(insertRequest);
        System.out.println(json);
    }
}
