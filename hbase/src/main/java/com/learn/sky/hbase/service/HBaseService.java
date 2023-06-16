package com.learn.sky.hbase.service;

import com.learn.sky.hbase.dto.RowKeyColumn;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author 
 * @date 2021-09-10 12:37:00
 */
@Service
@Slf4j
public class HBaseService extends AbstractHBaseService {


    /**
     * 批量查询
     *
     * @param  rcs 使用RowkeyUtil生成
     * @return long
     */
    public List<Long> batchGet(List<RowKeyColumn> rcs, String tableName, String columnFamily) {
        try {
            List<HashMap> list = select(tableName, rcs , columnFamily , null);
            log.info("batchGet tableName={}, columnFamily={}, rowKeyColumnList={} , result={}" , tableName, columnFamily, rcs, list);
            List<Long> values = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                HashMap hashMap = list.get(i);
                if (MapUtils.isEmpty(hashMap)) {
                    values.add(0L);
                } else if (Objects.isNull(hashMap.get(rcs.get(i).getColumn()))){
                    values.add(0L);
                } else {
                    values.add(Long.valueOf(String.valueOf(hashMap.get(rcs.get(i).getColumn()))));
                }
            }
            return values;
        } catch (IOException e) {
            log.warn("batchGet error" , e);
            List<Long> rList = new ArrayList<>(rcs.size());
            for (int i = 0; i < rcs.size(); i++) {
                rList.add(0L);
            }
            return rList;
        }
    }

    @PreDestroy
    private void destroy() {
        this.close();
    }

}
