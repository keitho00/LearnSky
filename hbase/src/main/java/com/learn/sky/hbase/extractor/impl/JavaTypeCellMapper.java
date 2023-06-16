package com.learn.sky.hbase.extractor.impl;

import com.learn.sky.hbase.extractor.CellMapper;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;

import java.lang.reflect.Field;

/**
 * @author 
 * @date 2021/9/10 15:49
 */
public class JavaTypeCellMapper implements CellMapper {

    private JavaTypeExtractorComponent javaTypeExtractorComponent;

    public JavaTypeCellMapper(){
        javaTypeExtractorComponent = new JavaTypeExtractorComponent();
    }

    @Override
    public Object mapperCell(Result result, Cell cell, String cellQualifier , Field field) {
        Class<?> type = field.getType();
        return javaTypeExtractorComponent.toValue(type , cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
    }

}
