package com.learn.sky.hbase.service;

import com.learn.sky.hbase.config.HBaseClient;
import com.learn.sky.hbase.dto.RowKeyColumn;
import com.learn.sky.hbase.extractor.ResultCellListExtractor;
import com.learn.sky.hbase.extractor.impl.CellMapperResultCellListExtractor;
import com.learn.sky.hbase.extractor.impl.JavaTypeCellMapper;
import com.learn.sky.hbase.extractor.impl.JavaTypeExtractorComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Slf4j
abstract public class AbstractHBaseService {

    @Resource
    protected HBaseClient hBaseClient;

    private Connection getConnection() {
        return hBaseClient.getConnection();
    }


    private Admin getAdmin() {
        return hBaseClient.getAdmin();
    }


    /**
     * 创建数据表
     *
     * @param tableName
     * @param columnFamilies
     * @throws IOException
     */
    public boolean createTable(String tableName, Set<String> columnFamilies) throws IOException {
        log.info("begin_create:{},{}", tableName, columnFamilies);
        try {
            HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
            columnFamilies.forEach((c) -> {
                tableDescriptor.addFamily(new HColumnDescriptor(c));
            });
            getAdmin().createTable(tableDescriptor);
            return true;
        } catch (TableExistsException ee) {
            log.error("TableExistsException:{}", tableName);
        } catch (Exception e) {
            log.error("createTable_err", e);
        }
        return false;
    }

    /**
     * 禁用表
     *
     * @param tableName
     * @throws IOException
     */
    public void disableTable(String tableName) throws IOException {
        TableName name = TableName.valueOf(tableName);
        if (!getAdmin().isTableDisabled(name)) {
            getAdmin().disableTable(name);
        }
    }

    /**
     * 清空表
     *
     * @param tableName
     */
    public void truncate(String tableName) throws IOException {

        TableName name = TableName.valueOf(tableName);
        disableTable(tableName);
        getAdmin().truncateTable(name, true);

    }

    /**
     * 删除表
     *
     * @param tableName
     * @throws IOException
     */
    public void deleteTable(String tableName) throws IOException {
        TableName name = TableName.valueOf(tableName);
        if (getAdmin().isTableDisabled(name)) {
            getAdmin().deleteTable(name);
        }
    }

    /**
     * 获取数据表列表
     *
     * @return
     * @throws IOException
     */
    public List<TableDescriptor> listTables() throws IOException {
        List<TableDescriptor> tableDescriptors = getAdmin().listTableDescriptors();
        return tableDescriptors;
    }

    /**
     * 插入一条记录
     *
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param column
     * @param value
     * @throws IOException
     */
    public void insertOne(String tableName, String rowKey, String columnFamily, String column, String value) throws IOException {
        insertOne(tableName, rowKey, columnFamily, column, Bytes.toBytes(value));
    }

    /**
     * 插入一条记录
     *
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param column
     * @param value
     * @throws IOException
     */
    public void insertOne(String tableName, String rowKey, String columnFamily, String column, int value) throws IOException {
        insertOne(tableName, rowKey, columnFamily, column, Bytes.toBytes(value));
    }

    /**
     * 插入一条记录
     *
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param column
     * @param value
     * @throws IOException
     */
    public void insertOne(String tableName, String rowKey, String columnFamily, String column, long value) throws IOException {
        insertOne(tableName, rowKey, columnFamily, column, Bytes.toBytes(value));
    }

    /**
     * 插入一条记录
     *
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param column
     * @param value
     * @throws IOException
     */
    public void insertOne(String tableName, String rowKey, String columnFamily, String column, byte[] value) throws IOException {
        //得到 table
        Table table = getConnection().getTable(TableName.valueOf(tableName));

        try {
            Put put = new Put(Bytes.toBytes(rowKey));
            //下面三个分别为，列族，列名，列值
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), value);
            //执行插入
            table.put(put);
        } finally {
            table.close();
        }

    }

    private JavaTypeExtractorComponent javaTypeExtractorComponent = new JavaTypeExtractorComponent();

    /**
     * 插入一条记录
     *
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param obj
     * @throws IOException
     */
    public <T> void insertAll(String tableName, String rowKey, String columnFamily, T obj) throws IOException, IllegalAccessException {
        //得到 table
        Table table = getConnection().getTable(TableName.valueOf(tableName));

        try {
            List<Put> puts = new ArrayList<>();

            ReflectionUtils.doWithFields(obj.getClass(), field -> {
                field.setAccessible(true);
                Object val = field.get(obj);
                if (val != null) {
                    Put put = new Put(Bytes.toBytes(rowKey));
                    //下面三个分别为，列族，列名，列值
                    put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(field.getName()), javaTypeExtractorComponent.toBytes(val.getClass(), val));
                    puts.add(put);
                }
            });

            //执行插入
            table.put(puts);
        } finally {
            table.close();
        }

    }


    /**
     * 单行多列多值-单列簇
     *
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param map
     * @throws IOException
     */
    public void insertAll(String tableName, String rowKey, String columnFamily, Map<String, Object> map) throws IOException {
        Table table = getConnection().getTable(TableName.valueOf(tableName));
        List<Put> puts = new ArrayList<>();

        try {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Object value = entry.getValue();
                if (value == null) {
                    continue;
                }
                Put put = new Put(Bytes.toBytes(rowKey));
                put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(entry.getKey()), javaTypeExtractorComponent.toBytes(value.getClass(), value));
                puts.add(put);
            }
            table.put(puts);
        } finally {
            table.close();
        }

    }

    /**
     * 更新数据
     *
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param column
     * @param value
     * @throws IOException
     */

    public void update(String tableName, String rowKey, String columnFamily, String column, String value) throws IOException {
        Table table = getConnection().getTable(TableName.valueOf(tableName));
        try {
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
            table.put(put);
        } finally {
            table.close();
        }
    }

    /**
     * 删除单行单列
     *
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param column
     * @throws IOException
     */
    public void delete(String tableName, String rowKey, String columnFamily, String column) throws IOException {
        Table table = getConnection().getTable(TableName.valueOf(tableName));
        try {
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            delete.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
            table.delete(delete);
        } finally {
            table.close();
        }
    }

    /**
     * 自增单行单列
     *
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param column
     * @throws IOException
     */
    public void increment(String tableName, String rowKey, String columnFamily, String column, long amount) throws IOException {
        Table table = getConnection().getTable(TableName.valueOf(tableName));
        try {
            Increment increment = new Increment(Bytes.toBytes(rowKey));
            increment.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), amount);
            table.increment(increment);
        } finally {
            table.close();
        }


    }

    /**
     * 删除单行多列
     *
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param columnList
     * @throws IOException
     */
    public void delete(String tableName, String rowKey, String columnFamily, String... columnList) throws IOException {
        Table table = getConnection().getTable(TableName.valueOf(tableName));
        try {
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            for (String column : columnList) {
                delete.addColumns(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
            }
            table.delete(delete);
        } finally {
            table.close();
        }
    }

    /**
     * 删除单行单列簇
     *
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @throws IOException
     */
    public void delete(String tableName, String rowKey, String columnFamily) throws IOException {
        Table table = getConnection().getTable(TableName.valueOf(tableName));
        try {
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            delete.addFamily(Bytes.toBytes(columnFamily));
            table.delete(delete);
        } finally {
            table.close();
        }
    }

    /**
     * 删除单行
     *
     * @param tableName
     * @param rowKey
     * @throws IOException
     */
    public void delete(String tableName, String rowKey) throws IOException {
        Table table = getConnection().getTable(TableName.valueOf(tableName));
        try {
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            table.delete(delete);
        } finally {
            table.close();
        }
    }

    /**
     * 删除多行
     *
     * @param tableName
     * @param rowKeyList
     * @throws IOException
     */
    public void delete(String tableName, String... rowKeyList) throws IOException {
        Table table = getConnection().getTable(TableName.valueOf(tableName));
        try {
            ArrayList<Delete> deleteList = new ArrayList<>();
            for (String rowKey : rowKeyList) {
                Delete delete = new Delete(Bytes.toBytes(rowKey));
                deleteList.add(delete);
            }
            table.delete(deleteList);
        } finally {
            table.close();
        }

    }

    /**
     * 查询表
     *
     * @param tableName
     * @param rowKey
     * @return
     * @throws IOException
     */
    public <T> T select(String tableName, String rowKey, Class<T> tClass) throws IOException {
        return select(tableName, rowKey, new CellMapperResultCellListExtractor<T>(new JavaTypeCellMapper(), tClass) {
        });
    }

    /**
     * 查询表
     *
     * @param tableName
     * @param rowKey
     * @return
     * @throws IOException
     */
    public <T> T select(String tableName, String rowKey, ResultCellListExtractor<T> extractor) throws IOException {
        Table table = getConnection().getTable(TableName.valueOf(tableName));
        try {
            return toObject(table.get(new Get(Bytes.toBytes(rowKey))), extractor);
        } finally {
            table.close();
        }
    }

    /**
     * 批量查询表
     *
     * @param tableName
     * @param rowKeyList
     * @return
     * @throws IOException
     */
    public <T> List<T> select(String tableName, List<String> rowKeyList, ResultCellListExtractor<T> extractor) throws IOException {
        Table table = getConnection().getTable(TableName.valueOf(tableName));

        try {
            List<Get> gets = new ArrayList<>();
            rowKeyList.forEach(rowKey -> gets.add(new Get(Bytes.toBytes(rowKey))));
            return toObject(table.get(gets), extractor);
        } finally {
            table.close();
        }

    }

    /**
     * 批量查询表
     *
     * @param tableName
     * @param rowKeyColumn
     * @return
     * @throws IOException
     */
    public <T> T select(String tableName, RowKeyColumn rowKeyColumn, String columnFamily, ResultCellListExtractor<T> extractor) throws IOException {
        Table table = getConnection().getTable(TableName.valueOf(tableName));
        try {
            Get get = new Get(Bytes.toBytes(rowKeyColumn.getRowKey()));
            get.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(rowKeyColumn.getColumn()));
            return toObject(table.get(get), extractor);
        } finally {
            table.close();
        }
    }

    /**
     * 批量查询表
     *
     * @param tableName
     * @param rowKeyColumnList
     * @return
     * @throws IOException
     */
    public <T> List<T> select(String tableName, List<RowKeyColumn> rowKeyColumnList, String columnFamily, ResultCellListExtractor<T> extractor) throws IOException {
        Table table = getConnection().getTable(TableName.valueOf(tableName));
        try {
            List<Get> gets = new ArrayList<>();
            rowKeyColumnList.forEach(rowKeyColumn -> {
                        Get get = new Get(Bytes.toBytes(rowKeyColumn.getRowKey()));
                        get.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(rowKeyColumn.getColumn()));
                        gets.add(get);
                    }
            );
            return toObject(table.get(gets), extractor);
        } finally {
            table.close();
        }
    }

    public <T> List<T> toObject(Result[] results, ResultCellListExtractor<T> extractor) {
        List<T> list = new ArrayList<>();
        Arrays.stream(results).forEach(result -> list.add(toObject(result, extractor)));
        return list;
    }

    public <T> T toObject(Result result, ResultCellListExtractor<T> extractor) {
        return extractor.extractData(result);
    }

    /**
     * 全表扫描
     *
     * @param tableName
     * @return
     * @throws IOException
     */
    public ResultScanner scan(String tableName) throws IOException {
        Table table = getConnection().getTable(TableName.valueOf(tableName));
        try {
            Scan scan = new Scan();
            ResultScanner scanner = table.getScanner(scan);
            return scanner;
        } finally {
            table.close();
        }
    }

    /**
     * 全表扫描-列簇
     *
     * @param tableName
     * @param columnFamily
     * @return
     * @throws IOException
     */
    public ResultScanner scan(String tableName, String columnFamily) throws IOException {
        Table table = getConnection().getTable(TableName.valueOf(tableName));
        try {
            Scan scan = new Scan();
            scan.addFamily(Bytes.toBytes(columnFamily));
            ResultScanner scanner = table.getScanner(scan);

            return scanner;
        } finally {
            table.close();
        }
    }

    /**
     * 全表扫描-列
     *
     * @param tableName
     * @param columnFamily
     * @param column
     * @return
     * @throws IOException
     */
    public ResultScanner scan(String tableName, String columnFamily, String column) throws IOException {
        Table table = getConnection().getTable(TableName.valueOf(tableName));
        try {
            Scan scan = new Scan();
            scan.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
            ResultScanner scanner = table.getScanner(scan);

            return scanner;
        } finally {
            table.close();
        }
    }

    /**
     * 全表扫描-过滤器
     *
     * @param tableName
     * @param filter
     * @return
     * @throws IOException
     */
    public ResultScanner scan(String tableName, Filter filter) throws IOException {

        Table table = getConnection().getTable(TableName.valueOf(tableName));
        try {
            Scan scan = new Scan();
            scan.setFilter(filter);
            ResultScanner scanner = table.getScanner(scan);

            return scanner;
        } finally {
            table.close();
        }
    }

    /**
     * 分页全表扫描-过滤器
     *
     * @param tableName
     * @param filter
     * @return
     * @throws IOException
     */
    public ResultScanner scan(String tableName, Filter filter, String startRowKey) throws IOException {

        Table table = getConnection().getTable(TableName.valueOf(tableName));
        try {
            Scan scan = new Scan();
            scan.setFilter(filter);
            scan.withStartRow(Bytes.toBytes(startRowKey));
            ResultScanner scanner = table.getScanner(scan);

            return scanner;
        } finally {
            table.close();
        }
    }

    /**
     * 获取分页过滤器
     *
     * @param size
     * @return
     */
    public Filter pagetFilter(long size) {

        return new PageFilter(size);

    }

    /**
     * singleColumnValueFilter
     *
     * @param columnFamily
     * @param column
     * @param compareOperator
     * @param value
     * @return
     */
    public Filter singleColumnValueFilter(String columnFamily, String column, CompareOperator compareOperator, String value) {
        return new SingleColumnValueFilter(Bytes.toBytes(columnFamily), Bytes.toBytes(column), compareOperator, Bytes.toBytes(value));
    }

    /**
     * rowFilter
     *
     * @param compareOperator
     * @param rowComparator
     * @return
     */
    public Filter rowFilter(CompareOperator compareOperator, ByteArrayComparable rowComparator) {
        return new RowFilter(compareOperator, rowComparator);
    }

    /**
     * columnPrefixFilter
     *
     * @param prefix
     * @return
     */
    public Filter columnPrefixFilter(String prefix) {
        return new ColumnPrefixFilter(Bytes.toBytes(prefix));
    }

    /**
     * 过滤器集合
     *
     * @param filterList
     * @return
     */
    public FilterList filterListPassAll(Filter... filterList) {
        FilterList list = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        for (Filter filter : filterList) {
            list.addFilter(filter);
        }
        return list;
    }

    /**
     * 过滤器集合
     *
     * @param
     * @return
     */
    public FilterList filterListPassOne(Filter... filterList) {
        FilterList list = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        for (Filter filter : filterList) {
            list.addFilter(filter);
        }
        return list;
    }


    /**
     * 关闭连接
     *
     * @throws IOException
     */
    public void close() {
        try {
            getConnection().close();
        } catch (IOException e) {
            log.error("clone error", e);
        } finally {

        }
    }
}
