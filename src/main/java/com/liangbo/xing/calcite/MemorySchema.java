package com.liangbo.xing.calcite;

import java.util.HashMap;
import java.util.Map;

import org.apache.calcite.schema.Function;
import org.apache.calcite.schema.ScalarFunction;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;
import org.apache.calcite.schema.impl.ScalarFunctionImpl;

import com.liangbo.xing.calcite.MemoryData.Database;
import com.liangbo.xing.calcite.function.TimeOperator;
import com.liangbo.xing.calcite.table.MemoryTable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

/**
 * 数据源的schema，DataSource的元数据
 *
 * @author hdfs
 */
public class MemorySchema extends AbstractSchema {
    private String dbName;
    public MemorySchema(String dbName) {
        this.dbName = dbName;
    }

    /**
     * 通过schema实现从数据源自己的概念（DataBase及Table）向Calcite的概念(MemoryTable)进行转换的过程。
     */
    @Override
    public Map<String, Table> getTableMap() {
        Map<String, Table> tables = new HashMap<String, Table>();
        Database database = MemoryData.MAP.get(this.dbName);
        if(database == null)
            return tables;
        for(MemoryData.Table table : database.tables) {
            tables.put(table.tableName, new MemoryTable(table));
        }
        return tables;
    }

    /**
     * 获取操作函数中所有操作函数，将这些函数转换为Calcite中的概念。
     */
    protected Multimap<String, Function> getFunctionMultimap() {
        ImmutableMultimap<String,ScalarFunction> funcs = ScalarFunctionImpl.createAll(TimeOperator.class);
        Multimap<String, Function> functions = HashMultimap.create();
        for(String key : funcs.keySet()) {
            for(ScalarFunction func : funcs.get(key)) {
                functions.put(key, func);
            }
        }
        return functions;
    }
}
