package com.learn.sky.hbase.extractor;

/**
 * @author 
 * @date 2021/9/10 17:13
 */
public interface JavaTypeExtractor<T> {

    /**
     * supportType
     * @param type
     * @return
     */
    boolean supportType(Class type);

    /**
     * toBytes
     * @param value
     * @return
     */
    byte[] toBytes(T value);

    /**
     * toValue
     * @param bytes
     * @return
     */
    T toValue(final byte[] bytes, int off, int len);

}
