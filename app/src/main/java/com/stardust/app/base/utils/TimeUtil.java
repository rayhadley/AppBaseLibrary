package com.stardust.app.base.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Time util
 * 
 * @author  
 */
public class TimeUtil {
	public final static String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";

	/**
	 * 当前时间
	 * 
	 * @return
	 */
	public static String getFormatDate(long millSeconds) {
		return String.format("%tF", millSeconds);
	}
	 
	/**
	 * 当前时间
	 * 
	 * @return
	 */
	public static String getNowDate() {
		return String.format("%tF", System.currentTimeMillis());
	}

	@SuppressLint("SimpleDateFormat") 
	public static String getTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = new Date(time);
		String t1 = format.format(d1);
		return t1;
	}
	
	@SuppressLint("SimpleDateFormat") 
	public static String getTimeForFormat(String formatStr, long time) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date d1 = new Date(time);
		String t1 = format.format(d1);
		return t1;
	}
	
	
	public static String[] get24Hours() {
		String[] hourStrings = new String[24];
		Integer[] hours=new Integer[24];
		for (int i = 0; i < 24; i++) {
            Calendar calendar=Calendar.getInstance();
             calendar.add(Calendar.HOUR_OF_DAY, i);
             hours[i]=calendar.get(Calendar.HOUR_OF_DAY);
        }
		ArrayList<String> tmp = new ArrayList<String>();
        for (int i = hours.length - 1; i >= 0; i--) {
        	tmp.add(hours[i] + ":" + "00");
        }
        for (int i = 0; i < tmp.size(); i++) {
			hourStrings[i] = tmp.get(i);
		}
		return hourStrings;
	}

	/**
	 * 根据出生年月计算年龄
	 *
	 * @param birthDay
	 * @return
	 * @throws Exception
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getAge(String birthDay) {
		if(birthDay == null){
			return "0";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			System.out.println("123--->" + birthDay);
			date = sdf.parse(birthDay);
		} catch (ParseException e) {
			e.printStackTrace();
			return "0";
		}

		Calendar cal = Calendar.getInstance();
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(date);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}

		return age + "";
	}

	@SuppressLint("SimpleDateFormat")
	public static String getTime(long time, String formatString) {
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		Date d1 = new Date(time);
		String t1 = format.format(d1);
		return t1;
	}

	public static String getNowDateTime() {
		return getDateTime(System.currentTimeMillis());
	}

	@SuppressLint("SimpleDateFormat")
	public static String getDateTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = new Date(time);
		String t1 = format.format(d1);
		return t1;
	}

	@SuppressLint("SimpleDateFormat")
	public static String getDateTimeNoSecond(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d1 = new Date(time);
		String t1 = format.format(d1);
		return t1;
	}

	public static long getDate(String timeString) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = format.parse(timeString);
			return date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public static long getDate(String timeString, String formatString) {
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		try {
			Date date = format.parse(timeString);
			return date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 整数(秒数)转换为时分秒格式(xx:xx:xx)
	 * */
	public static String secToTime(int time) {
		String timeStr = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (time <= 0)
			return "00:00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = "00:" +  unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
			}
		}
		return timeStr;
	}

    /**
     * 整数(毫秒数)转换为时分秒格式(xx:xx:xx)
     * */
    public static String milliSecondToTime(long timeMilliSecond) {
        int time = (int)(timeMilliSecond / 1000);
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00:" +  unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

	public static String unitFormat(int i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Integer.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}

	/***
	 * 获取当月第一天
	 * */
	public static Date getCurrentMonthFirstDay() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
		return  c.getTime();
	}
	/***
	 * 获取当月最后一天
	 * */
	public static Date getCurrentMonthLastDay() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return  c.getTime();
	}

	/**
	 * 获取当天 开始与结束的时间
	 * */
	public static long[] getTodayBenginAndEndTime() {
		long[] result = new long[2];
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		Date start = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.SECOND, -1);

		Date end = calendar.getTime();
		result[0] = start.getTime();
		result[1] = end.getTime();
		return result;
	}

	/**
	 * 获取n个月前的日期
	 * @param beforeMonth 前几个月
	 *  @param format (时间格式化格式 默认“yyyy-MM-dd”) 可任意设置  （如："yyyy-MM-dd  HH:mm:ss"）
	 * */
	public static String getDateBeforeMonth(int beforeMonth, String format) {

		Date dNow = new Date();   //当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		calendar.add(calendar.MONTH, -beforeMonth);  //设置为前n月
		dBefore = calendar.getTime();   //得到前3月的时间
		SimpleDateFormat sdf = null;
		if (TextUtils.isEmpty(format)) {
			sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
		} else {
			sdf = new SimpleDateFormat(format); //设置时间格式
		}

		String defaultStartDate = sdf.format(dBefore);    //格式化前n月的时间
		return defaultStartDate;
	}
	
}