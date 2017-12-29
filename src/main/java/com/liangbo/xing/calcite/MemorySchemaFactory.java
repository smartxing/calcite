package com.liangbo.xing.calcite;

import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaFactory;
import org.apache.calcite.schema.SchemaPlus;

import java.util.Map;

/**
 * SchemaFactory , 用于构建数据源的Schema
 * 
 * @author hdfs
 */
public class MemorySchemaFactory implements SchemaFactory {
	/**
	 * 建立Connection的时候，即通过读取定义的元数据文件，获取指定的SchemaFatory
	 * 然后通过这个SchemaFactory来创建Schema。
	 */
	public Schema create(SchemaPlus parentSchema, String name,
                         Map<String, Object> operand) {
		System.out.println("param1 : " + operand.get("param1"));
		System.out.println("param2 : " + operand.get("param2"));
		System.out.println("Get database " + name);
		return new MemorySchema(name);
	}
}
