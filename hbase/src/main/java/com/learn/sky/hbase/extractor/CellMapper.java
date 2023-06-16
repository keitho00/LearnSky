package com.learn.sky.hbase.extractor;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;

import java.lang.reflect.Field;


public interface CellMapper {

    Object mapperCell(Result result , Cell cell , String cellQualifier , Field field);

}
