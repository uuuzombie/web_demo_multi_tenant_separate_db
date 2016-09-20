package com.sky.demo.web_demo_multi_tenant_separate_db.dto.anlog;

import java.util.List;

import com.google.common.base.Objects;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.ActionType;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.FeatureType;

/**
 * Created by rg on 2015/6/30.
 */
public class AnLogUpdateRequest {

    private long id;
    private ActionType actionType;
    private FeatureType featureType;
    private List<BaseAnActionInfo> actionInfo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
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
                .add("id", id)
                .add("actionType", actionType)
                .add("featureType", featureType)
                .add("actionInfo", actionInfo)
                .toString();
    }
}
