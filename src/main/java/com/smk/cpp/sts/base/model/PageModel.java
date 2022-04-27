package com.smk.cpp.sts.base.model;

import com.smk.cpp.sts.base.constant.PageConstants;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年12月31日 14:59
 * @Description:
 */
public class PageModel<T> {
    
    /**
     * 数据总条数
     */
    private int totalCount = PageConstants.DEFAULT_PAGE_TOTAL_COUNT;

    /**
     * 分页大小
     */
    private int pageSize = PageConstants.DEFAULT_PAGE_SIZE;

    /**
     * 当前页数
     */
    private int currentPageNo = PageConstants.DEFAULT_PAGE_NUM;

    private List<T> dataList;

    public PageModel() {}

    public PageModel(int totalCount, int pageSize, int currentPageNo) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currentPageNo = currentPageNo;
    }

    public PageModel(int totalCount, int pageSize, int currentPageNo, List<T> dataList) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currentPageNo = currentPageNo;
        this.dataList = dataList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
