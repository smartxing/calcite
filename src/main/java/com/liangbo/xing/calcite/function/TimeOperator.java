package com.liangbo.xing.calcite.function;

import java.sql.Date;
import java.util.Calendar;

/**
 * 自定义函数操作
 * 
 * @author hdfs
 */
public class TimeOperator {
	public String YEAR(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return "sdf";
	}
	
/*	public int THE_YEAR(int date) {
		long mills = (long) date * (1000 * 3600 * 24);
		Date dt = Date.valueOf(DateFormat.formatToDateStr(mills));
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal.get(Calendar.YEAR);
	}*/
	
	public Integer THE_MONTH(Date date) {
		return 6;
	}
	
	public Integer THE_DAY(Date date) {
		return 16;
	}
	
	public int THE_SYEAR(Date date, String year) {
		return 18;
	}
	
	public String COM(String str1, String str2) {
		return str1 + str2;
	}
}
