package com.learn.sky.hbase.extractor.impl;

import com.learn.sky.hbase.extractor.JavaTypeExtractor;
import org.apache.hadoop.hbase.util.Bytes;

public class IntJavaTypeExtractor implements JavaTypeExtractor<Integer> {
    @Override
    public boolean supportType(Class type) {
        return type == int.class || type == Integer.class;
    }

    @Override
    public byte[] toBytes(Integer value) {
        return Bytes.toBytes(value);
    }

    @Override
    public Integer toValue(final byte[] bytes, int off, int len) {
        return Bytes.toInt(bytes , off , len);
    }
}
