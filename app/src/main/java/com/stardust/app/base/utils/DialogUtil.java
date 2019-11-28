package com.stardust.app.base.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.stardust.app.base.R;

/**
 * dialog工具类 edit at 2012-8-30
 * 
 * @author zts
 */
@SuppressLint("InflateParams")
public class DialogUtil {
	/*
	 * 初始化设置dialog
	 *
	 */
	public Dialog sureDialog(Context context, String msg, OnClickListener listener) {
		View v = LayoutInflater.from(context).inflate(R.layout.dialog_sure_view, null);
		TextView tv = v.findViewById(R.id.dialog_tv_title);
		tv.setText(msg);
		v.findViewById(R.id.dialog_tv_sure).setOnClickListener(listener);
		
		Dialog dialog = new Dialog(context, R.style.CustomDialog);
		dialog.setContentView(v);
		dialog.setCanceledOnTouchOutside(true);
		
		//Window window = dialog.getWindow();
		//setDialogWindowParams(window);
		
		return dialog;
	}
	
	/**
	 * 设置dialog的窗体属性
	 * 
	 * @param window
	 *            since v6.0
	 */
	private void setDialogWindowParams(Window window) {
		window.setWindowAnimations(R.style.AnimBottom);
		WindowManager.LayoutParams wml = window.getAttributes();
		wml.width = LayoutParams.MATCH_PARENT;
		wml.height = LayoutParams.WRAP_CONTENT;
		wml.gravity = Gravity.BOTTOM;
	}
	 
	/*
	 * 相册选择dialog
	 */
	public Dialog picDialog(Context context, OnClickListener listener) {
		View v = LayoutInflater.from(context).inflate(R.layout.dialog_public_view, null);
		v.findViewById(R.id.dialog_public_tv_cancel).setOnClickListener(listener);
		v.findViewById(R.id.dialog_public_tv_camera).setOnClickListener(listener);
		v.findViewById(R.id.dialog_public_tv_album).setOnClickListener(listener);
		
		Dialog dialog = new Dialog(context, R.style.CustomDialog);
		dialog.setContentView(v);
		dialog.setCanceledOnTouchOutside(true);
		
		Window window = dialog.getWindow();
		setDialogWindowParams(window);
		
		return dialog;
	}

	/*
	 * 加载对话框dialog
	 */
	public Dialog loadingDialog(Context context, OnClickListener listener) {
		View v = LayoutInflater.from(context).inflate(R.layout.loading_view, null);

		Dialog dialog = new Dialog(context, R.style.CustomDialog);
		dialog.setContentView(v);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);

		//Window window = dialog.getWindow();
		//  setDialogWindowParams(window);

		return dialog;
	}

	public Dialog progressDialog(Context context, String noteMessage, OnClickListener listener) {
		View v = LayoutInflater.from(context).inflate(R.layout.progress_dialog_view, null);
		TextView textView = v.findViewById(R.id.tvMessage);
		if (TextUtils.isEmpty(noteMessage)) {
			textView.setText("");
		} else {
			textView.setText(noteMessage);
		}
		Dialog dialog = new Dialog(context, R.style.CustomDialog);
		dialog.setContentView(v);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);

		return dialog;
	}
	 
	/**
	 * 显示弹窗样式(下面进入 在中间显示)
	 */
	public Dialog bottomDialog(View view, Context context) {			 
		  
		Dialog dialog = new Dialog(context, R.style.PopuDialog);
		
		dialog.setContentView(view);	 
		dialog.setCanceledOnTouchOutside(true);
		
		Window window = dialog.getWindow();
		
		//setDialogWindowParams(window);
		window.setWindowAnimations(R.style.AnimBottom);
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		WindowManager.LayoutParams wml = window.getAttributes();
		wml.width = LayoutParams.MATCH_PARENT;
		wml.height = LayoutParams.WRAP_CONTENT;
		 
		return dialog;
	}
	/**
	 * 显示弹窗样式（在下面显示）
	 */
	public Dialog bottomDialog2(View view, Context context) {

		Dialog dialog = new Dialog(context, R.style.PopuDialog);

		dialog.setContentView(view);
		dialog.setCanceledOnTouchOutside(true);

		Window window = dialog.getWindow();

		setDialogWindowParams(window);

		return dialog;
	}
	 
	/*
	 * 显示弹窗样式
	 */
	public Dialog popuWinDialog(View view, Context context) {			 
		  
		Dialog dialog = new Dialog(context, R.style.PopuDialog);
		 
		dialog.setContentView(view);	 
		dialog.setCanceledOnTouchOutside(true);
		 
		Window window = dialog.getWindow();
		WindowManager.LayoutParams wml = window.getAttributes();
		 
		wml.width = LayoutParams.MATCH_PARENT;
		wml.height = LayoutParams.WRAP_CONTENT;
		wml.gravity = Gravity.CENTER;
		 
		return dialog;
	}
}
