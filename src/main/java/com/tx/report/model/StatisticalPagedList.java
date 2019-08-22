package com.tx.report.model;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by XRX on 2017/04/06.
 */
public class StatisticalPagedList<T> /*extends PagedList<T> */{
    private Integer count;
    private List<T> list;
    private int pageIndex;
    private int pageSize;
    private int queryPageSize;


    //当前页面统计记录
    private T currentPageStatisticalRecord;

    //全部统计记录
    private T totalStatisticalRecord;

    public StatisticalPagedList(PageInfo<T> pagedList) {
        setCount((int)pagedList.getTotal());
        setList(pagedList.getList());
        setPageIndex(pagedList.getPageNum());
        setPageSize(pagedList.getPageSize());
        setQueryPageSize(pagedList.getPageSize());
    }


    public T getCurrentPageStatisticalRecord() {
        return currentPageStatisticalRecord;
    }

    public void setCurrentPageStatisticalRecord(T currentPageStatisticalRecord) {
        this.currentPageStatisticalRecord = currentPageStatisticalRecord;
    }

    public T getTotalStatisticalRecord() {
        return totalStatisticalRecord;
    }

    public void setTotalStatisticalRecord(T totalStatisticalRecord) {
        this.totalStatisticalRecord = totalStatisticalRecord;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getQueryPageSize() {
        return queryPageSize;
    }

    public void setQueryPageSize(int queryPageSize) {
        this.queryPageSize = queryPageSize;
    }
}
