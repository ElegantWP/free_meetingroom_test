package com.myweb.app.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String format(Timestamp ts) {
		String ret = "";
		if (ts != null) {
			Date date = new Date(ts.getTime());
			ret = sdf.format(date);
		}
		return ret;
	}
	
	/**
	 * 获取标准格式当前时间
	 * @return
	 */
	public static String gainStandardCurrentDateTime(){
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		return DateUtil.format(ts);
	}
	
	/**
	 * 字符串转时间戳
	 * @param arg
	 * @return
	 */
	public static Timestamp String2Ts(String arg){
		return Timestamp.valueOf(arg);
	}
	
	public static void main(String[] args){
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		System.out.println(DateUtil.format(ts));
	}
}
