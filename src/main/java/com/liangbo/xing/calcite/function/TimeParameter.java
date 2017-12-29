package com.liangbo.xing.calcite.function;

import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.FunctionParameter;
import org.apache.calcite.sql.type.SqlTypeName;

public class TimeParameter implements FunctionParameter {
	private int orinal = 0;
	private String name ;
	
	public TimeParameter(String name, int ordinal) {
		this.orinal = ordinal;
		this.name = name;
	}
	
	@Override
	public int getOrdinal() {
		return this.orinal;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public RelDataType getType(RelDataTypeFactory typeFactory) {
		return typeFactory.createSqlType(SqlTypeName.INTEGER);
	}

	@Override
	public boolean isOptional() {
		return false;
	}
}
