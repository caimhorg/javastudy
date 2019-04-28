package com.javastudy.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author cmh
 *
 */
public class DateUtils {

	/**
	 * date转string
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		String dateStr = sf.format(date);
		return dateStr;
	}
	
	/**
	 * string转date
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date stringToDate(String dateStr, String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sf.parse(format);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * yyyy-MM-dd HH:mm:ss转str
	 * @param timeStamp
	 * @return
	 */
	public static String TimestampToStr(Date timeStamp) {
		
		return dateToString(timeStamp, "yyyy-MM-dd HH:mm:ss");
		
	}
}
