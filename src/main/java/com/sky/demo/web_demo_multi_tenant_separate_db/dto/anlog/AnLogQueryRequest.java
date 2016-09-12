package com.sky.demo.web_demo_multi_tenant_separate_db.dto.anlog;

import com.google.common.base.Objects;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.BaseQueryRequest;

/**
 * Created by rg on 2015/6/30.
 */
public class AnLogQueryRequest extends BaseQueryRequest{

    private String beginDate;
    private String endDate;

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
                .add("beginDate", beginDate)
                .add("endDate", endDate)
                .toString();
    }
}
