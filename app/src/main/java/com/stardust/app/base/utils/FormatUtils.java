package com.stardust.app.base.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
* @Description: (保留2位小数) 
* @author 唐飞
* @date 2015年11月27日 下午4:17:03 *
 */
public class FormatUtils {
	public void m1(double f) {
		BigDecimal bg = new BigDecimal(f);
		double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println(f1);
	}

	/**
	 * DecimalFormat转换最简便
	 */
	public String m2(double f) {
		DecimalFormat df = new DecimalFormat("#0.00");//保留0的两位有效数字
		//df.setRoundingMode(RoundingMode.UP);
		return df.format(f);
	}
	
	public String m2m(double f){
		DecimalFormat df = new DecimalFormat("#0.00");
		df.setRoundingMode(RoundingMode.UP);
		return df.format(f);
	}
	/**
	 * String.format打印最简便
	 */
	public void m3(double f) {
		System.out.println(String.format("%.2f", f));
	}

	public void m4(double f) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		System.out.println(nf.format(f));
	}
	
	public static String getDateByString(String longString){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(Long.valueOf(longString)*1000);
		String format = sdf.format(date);
		return format;
	}
	public static String getDatetimeByString(String longString){
		if(Integer.valueOf(longString) == 0){
			return "未知";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(Long.valueOf(longString)*1000);
			String format = sdf.format(date);
			return format;
		}
	}
}
