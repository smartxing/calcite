package com.liangbo.xing.calcite.table;

import com.liangbo.xing.calcite.MemoryData;
import com.liangbo.xing.calcite.MemoryEnumerator;
import org.apache.calcite.DataContext;
import org.apache.calcite.linq4j.AbstractEnumerable;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Enumerator;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.ScannableTable;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.type.SqlTypeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Calcite中表的抽象，实际上就是实现从原始数据源中的Table概念到Calcite中Table概念之间的转换过程。
 * 1、原始数据源列类型转换为Calcite中的类型。
 * 2、原始数据源中数据转换为我们所需要的数据
 * 
 * @author hdfs
 */
public class MemoryTable extends AbstractTable implements ScannableTable {
	// 自定义的数据表，就是我们所定义的数据源的表
    private MemoryData.Table sourceTable;
    // 引用数据类型
    private RelDataType dataType;
    
    public MemoryTable(MemoryData.Table table) {
        this.sourceTable = table;
        dataType = null;
    }

    
    private static int[] identityList(int n) {
        int[] integers = new int[n];
        for (int i = 0; i < n; i++) {
            integers[i] = i;
        }
        return integers;
    }
    
    // 实际上就是对Java 类的封装，将原始数据源中的Column概念(列数据类型等)转换为Calcite中的Column概念(RelDataType)
	public RelDataType getRowType(RelDataTypeFactory typeFactory) {
		if(dataType == null) {
            RelDataTypeFactory.FieldInfoBuilder fieldInfo = typeFactory.builder();
            for (MemoryData.Column column : this.sourceTable.columns) { // 保存每列列名与该列数据类型之间映射关系
                RelDataType sqlType = typeFactory.createJavaType(
                        MemoryData.JAVATYPE_MAPPING.get(column.type));
                sqlType = SqlTypeUtil.addCharsetAndCollation(sqlType, typeFactory);
                fieldInfo.add(column.name, sqlType); // 保存每一个列列名与这个列的数据类型之间的映射关系
            }
            this.dataType = typeFactory.createStructType(fieldInfo);
        }
        return this.dataType; // 将所有列的数据类型合并为一种数据类型的数据结构
	}

	/**
	 * 调用scan函数构建emurator用于读取数据，使用Enumerator来读取数据。再Enumerator中会实现从原始数据源的数据到实际我们需要的数据之间的转换过程。
	 */
	public Enumerable<Object[]> scan(DataContext root) {
		final List<String> types = new ArrayList<String>(sourceTable.columns.size());
		for(MemoryData.Column column : sourceTable.columns) {
			types.add(column.type);
		}
        final int[] fields = identityList(this.dataType.getFieldCount());
        return new AbstractEnumerable<Object[]>() {
            public Enumerator<Object[]> enumerator() {
            	// 这里的data就是从数据源中查询获得的数据，因此对于其他数据源，数据的接入应该是在这里进行的。
                return new MemoryEnumerator<Object[]>(fields, types, sourceTable.data);
            }
        };
	}

	
}
