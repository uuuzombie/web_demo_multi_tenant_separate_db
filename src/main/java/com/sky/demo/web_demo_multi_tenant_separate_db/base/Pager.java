package com.sky.demo.web_demo_multi_tenant_separate_db.base;

import java.util.List;
import java.util.Map;

/**
 * Created by rg on 2015/6/11.
 */
public class Pager<T> {

    private List<T> rows;           //对象记录结果集

    private long pageNumber = 1;         // 当前页
    private int pageSize = 10;      // 每页显示记录数
    private long totalRecords = 0;  // 总记录数
    private long pageCount = 1;      // 总页数

    private Map<String, Object> filterCondition;

    private boolean isFirstPage = false;        //是否为第一页
    private boolean isLastPage = false;         //是否为最后一页
    private boolean hasPreviousPage = false;    //是否有前一页
    private boolean hasNextPage = false;        //是否有下一页

    public Pager(int total, int pageNumber) {
        init(total, pageNumber, pageSize);
    }

    public Pager(long totalRecords, int pageNumber, int pageSize) {
        init(totalRecords, pageNumber, pageSize);
    }

    private void init(long totalRecords, int pageNumber, int pageSize) {
        //设置基本参数
        this.totalRecords = totalRecords;
        this.pageSize = pageSize;
        this.pageCount = (this.totalRecords - 1) / this.pageSize + 1;

        //根据输入可能错误的当前号码进行自动纠正
        if (pageNumber < 1) {
            this.pageNumber = 1;
        } else if (pageNumber > this.pageCount) {
            this.pageNumber = this.pageCount;
        } else {
            this.pageNumber = pageNumber;
        }

        //页面边界的判定
        judgePageBoundary();
    }

    private void judgePageBoundary() {
        isFirstPage = (pageNumber == 1);
        isLastPage = (pageNumber == pageCount);
        hasPreviousPage = (pageNumber > 1);
        hasNextPage = (pageNumber < pageCount);
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Map<String, Object> getFilterCondition() {
        return filterCondition;
    }

    public void setFilterCondition(Map<String, Object> filterCondition) {
        this.filterCondition = filterCondition;
    }


    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[")
                .append("totalRecords=").append(totalRecords)
                .append(",pageCount=").append(pageCount)
                .append(",pageNo=").append(pageNumber)
                .append(",pageSize=").append(pageSize)
                .append(",isFirstPage=").append(isFirstPage)
                .append(",isLastPage=").append(isLastPage)
                .append(",hasPreviousPage=").append(hasPreviousPage)
                .append(",hasNextPage=").append(hasNextPage);

        sb.append(",rows.size=").append(rows.size());
        sb.append("]");
        return sb.toString();
    }

}
