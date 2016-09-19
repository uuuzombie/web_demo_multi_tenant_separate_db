package com.sky.demo.web_demo_multi_tenant_separate_db.dto;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * Created by rg on 8/2/15.
 */
public class BaseQueryRequest implements Serializable{

    private static final long serialVersionUID = -6458680899002899804L;

    private int pageNumber = 1;
    private int pageSize = 10;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("pageNumber", pageNumber)
                .add("pageSize", pageSize)
                .toString();
    }
}
