package com.learn.sky.hbase;

import com.google.common.collect.Sets;
import com.learn.sky.hbase.dto.RowKeyColumn;
import com.learn.sky.hbase.extractor.CellMapper;
import com.learn.sky.hbase.extractor.impl.CellMapperResultCellListExtractor;
import com.learn.sky.hbase.service.HBaseService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(value = "dev")
public class HbaseTest {

    /**
     * hbase 表名
     */
    private static final String TABLE_NAME = "hbase-demo";

    /**
     * 列簇
     */
    private static final String CF_DEFAULT = "cf";

    /**
     * testb rowkey define
     */
    private static final String rowkey1 = "32125102:wzqa:1";
    private static final String rowkey2 = "32125102:wzqa:2";
    private static final String rowkey3 = "32125102:wzqa:3";

    @Resource
    private HBaseService hBaseService;

    /**
     * 创建表跟对应的列簇
     *
     * 可创建多列簇
     */
    @Test
    public void createTable() {
        try {
            hBaseService.createTable(TABLE_NAME , Sets.newHashSet(CF_DEFAULT));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入数据
     *
     * 查询数据
     */
    @Test
    public void testConnect() throws Exception{
        try {
            String rowkey = rowkey1;

            // insert one by one
            hBaseService.insertOne(TABLE_NAME, rowkey, CF_DEFAULT, "a", "100");
            hBaseService.insertOne(TABLE_NAME, rowkey, CF_DEFAULT, "b", "200");
            hBaseService.insertOne(TABLE_NAME, rowkey, CF_DEFAULT, "c", "300");
            hBaseService.insertOne(TABLE_NAME, rowkey, CF_DEFAULT, "d", 300);

            // insert bean
            Abc bean = new Abc();
            bean.setA("1000");
            hBaseService.insertAll(TABLE_NAME, rowkey, CF_DEFAULT, bean);

            // json object or hash map
            Object select = hBaseService.select(TABLE_NAME, rowkey, new CellMapperResultCellListExtractor<Object>(new CellMapper(){
                    @Override
                    public Object mapperCell(Result result, Cell cell, String cellQualifier , Field field) {
                        if(cellQualifier.equals("d")) {
                            return Bytes.toInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                        }
                        if(cellQualifier.equals("count")){
                            return Bytes.toLong(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                        }
                    return Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                }
            }){});
            log.info("select = " + select);

            // to bean
            Abc abc = hBaseService.select(TABLE_NAME, rowkey, new CellMapperResultCellListExtractor<Abc>(new CellMapper(){

                @Override
                public Object mapperCell(Result result, Cell cell, String cellQualifier , Field field) {
                    if(cellQualifier.equals("d") || cellQualifier.equals("e")) {
                        return Bytes.toInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    }
                    if(cellQualifier.equals("count") || cellQualifier.equals("f")){
                        return Bytes.toLong(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    }
                    return Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                }
            }){});
            log.info("abc = " + abc);

            // auto to bean
            Abc abc2 = hBaseService.select(TABLE_NAME, rowkey , Abc.class);
            log.info("abc2 = " + abc2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Data
    public static class Abc {
        private String a;
        private String b;
        private String c;
        private int d = 520;
        private long count = 1314L;
        private int e = 66;
        private long f = 88L;
    }

    /**
     * increment
     *
     * increment 要求列值必须是 long
     * @throws IOException
     */
    @Test
    public void increment()throws IOException{
        hBaseService.increment(TABLE_NAME , rowkey1 ,CF_DEFAULT , "count" , 1);
    }

    /**
     * batch select
     *
     * 测试 increment 之后查询结果
     *
     * 会出现乱码 #select#toObject#Bytes.toString 导致，由于实际值是long ，需要使用 Bytes.toLong
     * @throws Exception
     */
    @Test
    public void batchSelect()throws Exception{
        List<RowKeyColumn> q = new ArrayList();
        q.add(new RowKeyColumn(rowkey1 , "count"));
        q.add(new RowKeyColumn(rowkey1 , "count"));
        List<HashMap> list = hBaseService.select(TABLE_NAME, q, CF_DEFAULT, new CellMapperResultCellListExtractor<HashMap>(new CellMapper(){
            @Override
            public Object mapperCell(Result result, Cell cell, String cellQualifier , Field field) {
                return Bytes.toLong(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
            }
        }){});
        for (int i = 0; i < list.size(); i++) {
            HashMap hashMap = list.get(i);
            log.info(String.valueOf(hashMap.get("count")));
            log.info("long , " + Long.valueOf(String.valueOf(hashMap.get("count"))));
        }
        log.info("select :{}" , list);
    }

    /**
     * truncate
     */
    @Test
    public void truncate() {
        try {
            hBaseService.truncate("wsd_typhon_collector_host_exp_td");
        } catch (Exception e) {
            log.error("hbase truncate error");
        }
    }

}
