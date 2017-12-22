package com.stardust.app.base.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * 权限Utils
 * @author haohua
 *
 */
public class PermissionUtils {
	final static private int REQUEST_CODE_ASK_PERMISSIONS = 123;
	public static boolean isHasReadContactPermission (final Activity context) {
		 int hasWriteContactsPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CONTACTS);
		    if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
		        if (!ActivityCompat.shouldShowRequestPermissionRationale(context,
		                Manifest.permission.WRITE_CONTACTS)) {
		        	ActivityCompat.requestPermissions(context,
                            new String[] {Manifest.permission.WRITE_CONTACTS},
                            REQUEST_CODE_ASK_PERMISSIONS);
		            return false;
		        }
		        ActivityCompat.requestPermissions(context,
		                new String[] {Manifest.permission.WRITE_CONTACTS},
		                REQUEST_CODE_ASK_PERMISSIONS);
		        return false;
		    }
		    return true;
	}

	public static boolean isReadPhonePermission (final Activity context) {

		int hasWriteContactsPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
		if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
			if (!ActivityCompat.shouldShowRequestPermissionRationale(context,
					Manifest.permission.READ_PHONE_STATE)) {

				ActivityCompat.requestPermissions(context,
						new String[] {Manifest.permission.READ_PHONE_STATE},
						REQUEST_CODE_ASK_PERMISSIONS);
				return false;
			}
			ActivityCompat.requestPermissions(context,
					new String[] {Manifest.permission.READ_PHONE_STATE},
					REQUEST_CODE_ASK_PERMISSIONS);
			return false;
		}
		return true;
	}
	public static boolean isHasGpsPermission (final Activity context) {
		
		 int hasWriteContactsPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
		    if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
		        if (!ActivityCompat.shouldShowRequestPermissionRationale(context,
		                Manifest.permission.ACCESS_FINE_LOCATION)) {

		        	ActivityCompat.requestPermissions(context,
                      new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                      REQUEST_CODE_ASK_PERMISSIONS);
		            return false;
		        }
		        ActivityCompat.requestPermissions(context,
		                new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
		                REQUEST_CODE_ASK_PERMISSIONS);
		        return false;
		    }
		    return true;
	}
	
	public static boolean isHasCameraPermission (final Activity context) {
		
		 int hasWriteContactsPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
		if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
		        if (!ActivityCompat.shouldShowRequestPermissionRationale(context,
		                Manifest.permission.CAMERA)) {
						ActivityCompat.requestPermissions(context,
								new String[] {Manifest.permission.CAMERA},
								REQUEST_CODE_ASK_PERMISSIONS);
		            return false;
		        }
				ActivityCompat.requestPermissions(context,
					new String[] {Manifest.permission.CAMERA},
					REQUEST_CODE_ASK_PERMISSIONS);
		        return false;
		}
		    return true;
	}

	public static boolean isHasAudioRecordPermission (final Activity context) {

		int hasWriteContactsPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);
		if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
			if (!ActivityCompat.shouldShowRequestPermissionRationale(context,
					Manifest.permission.RECORD_AUDIO)) {
				ActivityCompat.requestPermissions(context,
						new String[] {Manifest.permission.RECORD_AUDIO},
						REQUEST_CODE_ASK_PERMISSIONS);
				return false;
			}
			ActivityCompat.requestPermissions(context,
					new String[] {Manifest.permission.RECORD_AUDIO},
					REQUEST_CODE_ASK_PERMISSIONS);
			return false;
		}
		return true;
	}
	public static boolean isHasSystemDialogPermission (final Activity context) {

		int hasWriteContactsPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.SYSTEM_ALERT_WINDOW);
		if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
			if (!ActivityCompat.shouldShowRequestPermissionRationale(context,
					Manifest.permission.SYSTEM_ALERT_WINDOW)) {
				ActivityCompat.requestPermissions(context,
						new String[] {Manifest.permission.SYSTEM_ALERT_WINDOW},
						REQUEST_CODE_ASK_PERMISSIONS);
				return false;
			}
			ActivityCompat.requestPermissions(context,
					new String[] {Manifest.permission.SYSTEM_ALERT_WINDOW},
					REQUEST_CODE_ASK_PERMISSIONS);
			return false;
		}
		return true;
	}
	
	public static boolean isHasPhoneCallPermission (final Activity context) {
		
		 int hasWriteContactsPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
		    if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
		        if (!ActivityCompat.shouldShowRequestPermissionRationale(context,
		                Manifest.permission.CALL_PHONE)) {

		        	ActivityCompat.requestPermissions(context,
                    new String[] {Manifest.permission.CALL_PHONE},
                    REQUEST_CODE_ASK_PERMISSIONS);
		            return false;
		        }
		        ActivityCompat.requestPermissions(context,
		                new String[] {Manifest.permission.CALL_PHONE},
		                REQUEST_CODE_ASK_PERMISSIONS);
		        return false;
		    }
		    return true;
	}
	
	public static boolean isHasStoragePermission (final Activity context) {
		
		 int hasWriteContactsPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
		    if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
		        if (!ActivityCompat.shouldShowRequestPermissionRationale(context,
		                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

		        	ActivityCompat.requestPermissions(context,
                   new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                   REQUEST_CODE_ASK_PERMISSIONS);
		            return false;
		        }
		        ActivityCompat.requestPermissions(context,
		                new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
		                REQUEST_CODE_ASK_PERMISSIONS);
		        return false;
		    }
		    return true;
	}

	public static boolean isWakeLock (final Activity context) {

		int hasWriteContactsPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WAKE_LOCK);
		if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
			if (!ActivityCompat.shouldShowRequestPermissionRationale(context,
					Manifest.permission.WAKE_LOCK)) {

				ActivityCompat.requestPermissions(context,
						new String[] {Manifest.permission.WAKE_LOCK},
						REQUEST_CODE_ASK_PERMISSIONS);
				return false;
			}
			ActivityCompat.requestPermissions(context,
					new String[] {Manifest.permission.WAKE_LOCK},
					REQUEST_CODE_ASK_PERMISSIONS);
			return false;
		}
		return true;
	}

	public static boolean isDisableKeyGuard (final Activity context) {

		int hasWriteContactsPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.DISABLE_KEYGUARD);
		if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
			if (!ActivityCompat.shouldShowRequestPermissionRationale(context,
					Manifest.permission.DISABLE_KEYGUARD)) {

				ActivityCompat.requestPermissions(context,
						new String[] {Manifest.permission.DISABLE_KEYGUARD},
						REQUEST_CODE_ASK_PERMISSIONS);
				return false;
			}
			ActivityCompat.requestPermissions(context,
					new String[] {Manifest.permission.DISABLE_KEYGUARD},
					REQUEST_CODE_ASK_PERMISSIONS);
			return false;
		}
		return true;
	}


}
