package com.learn.sky.hbase.extractor.impl;

import com.learn.sky.hbase.extractor.JavaTypeExtractor;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author 
 * @date 2021/9/10 17:15
 */
public class LongJavaTypeExtractor implements JavaTypeExtractor<Long> {
    @Override
    public boolean supportType(Class type) {
        return type == long.class || type == Long.class;
    }

    @Override
    public byte[] toBytes(Long value) {
        return Bytes.toBytes(value);
    }

    @Override
    public Long toValue(final byte[] bytes, int off, int len) {
        return Bytes.toLong(bytes , off , len);
    }
}
