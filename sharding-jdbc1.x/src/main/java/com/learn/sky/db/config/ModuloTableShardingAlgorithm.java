package com.learn.sky.db.config;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

/**
 * @Author: JiuBuKong
 * @Date: 2018/11/2 下午1:45
 */
public class ModuloTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Long> {
    @Override
    public String doEqualSharding(Collection<String> tableNames, ShardingValue<Long> shardingValue) {
        for (String each : tableNames) {
            if (each.endsWith(getMonth(shardingValue.getValue()))) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Collection<String> doInSharding(Collection<String> tableNames, ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(tableNames.size());
        for (Long value : shardingValue.getValues()) {
            for (String tableName : tableNames) {
                if (tableName.endsWith(getMonth(value))) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> tableNames, ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(tableNames.size());
        Range<Long> range = (Range<Long>) shardingValue.getValueRange();
        for (Long i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : tableNames) {
                if (each.endsWith(getMonth(i))) {
                    result.add(each);
                }
            }
        }
        return result;
    }

    private String getMonth(Long value) {
        String format = "yyyy-MM-dd";

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String data = sdf.format(new Date(Long.valueOf(value)));
        String[] strings = data.split("-");
        return String.valueOf(Long.parseLong(strings[1]) % 4);

    }

}
