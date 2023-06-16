package com.learn.sky.hbase.extractor;

import org.apache.hadoop.hbase.client.Result;

/**
 * @author 
 * @date 2021/9/10 13:42
 */
public interface ResultCellListExtractor<T> {

    /**
     * extract hbase Datas
     * @param result
     * @return
     */
    T extractData(Result result);

}
