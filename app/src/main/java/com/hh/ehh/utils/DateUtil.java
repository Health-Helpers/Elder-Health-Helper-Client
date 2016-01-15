package com.hh.ehh.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static Date getDateFromString(String strDate){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = formatter.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String getStringFromDate(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = "";
		strDate = formatter.format(date);
		return strDate;	
		}
	
	public static String getTimestampStringFromDate(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String strDate = "";
		strDate = formatter.format(date);
		return strDate;	
		}
	
	
}
