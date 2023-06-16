package com.learn.sky.hbase.extractor.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.learn.sky.hbase.extractor.CellMapper;
import com.learn.sky.hbase.extractor.ResultCellListExtractor;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 
 * @date 2021/9/10 14:48
 */
public abstract class CellMapperResultCellListExtractor<T> implements ResultCellListExtractor<T> {

    private final CellMapper cellMapper;

    protected final Type _type;

    private final List<Field> fields;

    protected CellMapperResultCellListExtractor(CellMapper cellMapper){
        this.cellMapper = cellMapper;

        Type superClass = this.getClass().getGenericSuperclass();
        /* 22-Dec-2008, tatu: Not sure if this case is safe -- I suspect
         *   it is possible to make it fail?
         *   But let's deal with specific
         *   case when we know an actual use case, and thereby suitable
         *   workarounds for valid case(s) and/or error to throw
         *   on invalid one(s).
         */
        _type = ((ParameterizedType) superClass).getActualTypeArguments()[0];

        fields = getDeclaredFields(_type);
    }

    protected CellMapperResultCellListExtractor(CellMapper cellMapper , Class<T> tClass){
        this.cellMapper = cellMapper;

        _type = tClass;

        fields = getDeclaredFields(_type);
    }

    @Override
    public T extractData(Result result) {
        if (result == null) {
            return null;
        }
        List<Cell> ceList = result.listCells();
        if (CollectionUtils.isEmpty(ceList)) {
            return null;
        }
        JSONObject obj = new JSONObject();

        // cell process
        for (Cell cell : ceList) {
            // 列名
            String qualifier = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());

            // byte to value
            Object cellValue = cellMapper.mapperCell(result , cell , qualifier , findField(fields , qualifier));
            obj.put(qualifier, cellValue);
        }

        T item = JSON.parseObject(obj.toJSONString(), _type);
        return item;
    }

    /**
     * 获取类属性
     * @param _type
     * @return
     */
    private List<Field> getDeclaredFields(Type _type){
        List<Field> fields = new ArrayList<>();
        ReflectionUtils.doWithFields((Class<?>) _type , field -> {
            fields.add(field);
        });
        return fields;
    }

    /**
     * 根据类名找到对应的属性
     * @param fields
     * @param qualifier
     * @return
     */
    private Field findField(List<Field> fields , String qualifier){
        if(fields.size() == 0){
            return null;
        }
        for(Field field : fields){
            if(field.getName().equals(qualifier)){
                return field;
            }
        }
        return null;
    }

}
