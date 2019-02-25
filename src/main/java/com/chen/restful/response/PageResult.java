package com.chen.restful.response;

import com.github.pagehelper.Page;
import com.chen.restful.base.BasePageRequest;

import java.util.List;

/**
 * Created by chen on 2017/9/18.
 */
public class PageResult extends SuccessResult {

    private Integer totalSize;

    private Integer currentPage;

    private Integer pageSize;

    private Integer pages;

    public static PageResult INS(List result, Integer totalSize, BasePageRequest request) {

        return new PageResult(result, totalSize, request.getPage(), request.getPageSize());
    }

    public static PageResult INS(Page page, BasePageRequest request) {

        return new PageResult(page, (int) page.getTotal(), request.getPage(), request.getPageSize());
    }

    public PageResult(Object result, Integer totalSize, Integer currentPage, Integer pageSize) {
        super(result);
        this.totalSize = totalSize;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        pages = totalSize / pageSize + (totalSize % pageSize > 0 ? 1 : 0);
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
