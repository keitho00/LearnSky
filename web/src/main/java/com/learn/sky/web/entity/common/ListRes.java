package com.learn.sky.web.entity.common;

import java.io.Serializable;
import java.util.List;

/**
 * 列表返回结果
 */
public class ListRes<T> implements Serializable{

    private static final long serialVersionUID = -1405912378594953967L;
    //分页信息
    private PageInfo page;
    //记录
    private List<T> result;

    public ListRes(Integer pageSize, Integer pageNum, Integer totalRecord, List<T> result) {
        this.page = new PageInfo(pageSize,pageNum,totalRecord);
        this.result = result;
    }

    public ListRes(PageParam pageParam, Long totalRecord, List<T> result) {
        if(null != pageParam) {
            this.page = new PageInfo(pageParam.getPageSize(), pageParam.getPageNum(), totalRecord.intValue());
        }
        this.result = result;
    }

    public ListRes(PageParam pageParam, Integer totalRecord, List<T> result) {
        if(null != pageParam){
            this.page = new PageInfo(pageParam.getPageSize(),pageParam.getPageNum(),totalRecord);
        }
        this.result = result;
    }

    public ListRes(List<T> result) {
        if(null != result){
            this.page = new PageInfo();
            this.page.setTotalRecord(result.size());
        }
        this.result = result;
    }

    public PageInfo getPage() {
        return page;
    }

    public List<T> getResult() {
        return result;
    }
}
