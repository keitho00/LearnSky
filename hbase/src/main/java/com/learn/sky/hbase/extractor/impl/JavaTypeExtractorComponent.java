package com.learn.sky.hbase.extractor.impl;


import com.learn.sky.hbase.extractor.JavaTypeExtractor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 
 * @date 2021/9/10 17:25
 */
public class JavaTypeExtractorComponent {

    private List<JavaTypeExtractor> javaTypeExtractorList = new ArrayList<>();

    public JavaTypeExtractorComponent(){
        javaTypeExtractorList.add(new StringJavaTypeExtractor());
        javaTypeExtractorList.add(new LongJavaTypeExtractor());
        javaTypeExtractorList.add(new IntJavaTypeExtractor());
    }

    public byte[] toBytes(Class javaType , Object value) {
        return find(javaType).toBytes(value);
    }

    public Object toValue(Class javaType , final byte[] bytes, int off, int len) {
        return find(javaType).toValue(bytes , off , len);
    }

    private JavaTypeExtractor find(Class type){
        for (JavaTypeExtractor javaTypeExtractor : javaTypeExtractorList){
            if(javaTypeExtractor.supportType(type)){
                return javaTypeExtractor;
            }
        }
        throw new UnsupportedOperationException(type.getTypeName());
    }
}
