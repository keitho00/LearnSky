package com.learn.sky.hbase.extractor.impl;

import com.learn.sky.hbase.extractor.JavaTypeExtractor;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author 
 * @date 2021/9/10 17:16
 */
public class StringJavaTypeExtractor implements JavaTypeExtractor<String> {
    @Override
    public boolean supportType(Class type) {
        return type == String.class;
    }

    @Override
    public byte[] toBytes(String value) {
        return Bytes.toBytes(value);
    }

    @Override
    public String toValue(final byte[] bytes, int off, int len) {
        return Bytes.toString(bytes , off , len);
    }
}
