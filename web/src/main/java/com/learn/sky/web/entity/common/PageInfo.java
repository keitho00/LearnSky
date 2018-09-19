package com.learn.sky.web.entity.common;

import lombok.Data;

import java.io.Serializable;


@Data
public class PageInfo implements Serializable {

    private static final long serialVersionUID = 362498820763181265L;
    //每页记录数
    int pageSize;
    //第几页
    int pageNum;
    //记录总数
    int totalRecord;
    // 总页数
    int totalPages;

    public PageInfo() {
        pageSize = 0;
        pageNum = 0;
        totalRecord = 0;
        totalPages = 0;
    }

    public PageInfo(int pageSize, int pageNum, int recordSize, int pages) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.totalRecord = recordSize;
        this.totalPages = pages;
    }

    public PageInfo(Integer pageSize, Integer pageNum, Integer totalRecord) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.totalRecord = totalRecord;
        if (null == pageSize || pageSize == 0) {
            this.totalPages = 0;
        } else {
            this.totalPages = (int) Math.ceil(((double) totalRecord / pageSize));
        }
    }

}

