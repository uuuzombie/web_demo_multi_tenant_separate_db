package com.sky.demo.web_demo_multi_tenant_separate_db.dto.tenant;

import com.google.common.base.Objects;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.BaseQueryRequest;

/**
 * Created by user on 16/9/19.
 */
public class TenantUserQueryRequest extends BaseQueryRequest {

    private String userName;
    private String beginDate;
    private String endDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("userName", userName)
                .add("beginDate", beginDate)
                .add("endDate", endDate)
                .toString();
    }
}
