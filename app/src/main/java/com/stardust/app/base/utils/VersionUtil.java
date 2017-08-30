package com.stardust.app.base.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 版本工具，用于获取程序版本号、版本名称和版本检测
 * 
 * @author shou
 * 
 */
public class VersionUtil {
	public static int getCurrentVersionCode(String packageName, Context context) {
		try {
			return context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
		} catch (NameNotFoundException e) {
			return -1;
		}
	}
	public static int getCurrentVersionCode(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			return -1;
		}
	}

	public static String getCurrentVersionName(String packageName,
			Context context) {
		try {
			return context.getPackageManager().getPackageInfo(packageName, 0).versionName;
		} catch (NameNotFoundException e) {
			return "";
		}
	}

	public static String getCurrentVersionName(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "1.0.0";
		}
	}
}