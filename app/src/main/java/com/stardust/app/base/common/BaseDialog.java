package com.stardust.app.base.common;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.stardust.app.base.R;
import com.stardust.app.base.utils.DensityUtils;


public class BaseDialog {
	
	private Dialog dialog;
	private Context mContext;
	private View view = null;
	private int screenWidth;
	private int screenHeight;

	public BaseDialog(Context context) {
//		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		dialog  = new Dialog(context, R.style.add_dialog);
		screenWidth = DensityUtils.getScreenWidth(context);
		screenHeight = DensityUtils.getScreenHeight(context);
		
	}

	public BaseDialog setView(int layoutId) {
		Window win = dialog.getWindow();
		LayoutParams params = new LayoutParams();
		win.setAttributes(params);
		win.setGravity(Gravity.CENTER);
		dialog.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog
		dialog.setContentView(layoutId);
		view = dialog.getWindow().getDecorView();

		return this;
	}
	public BaseDialog setView(View view) {
		Window win = dialog.getWindow();
		LayoutParams params = new LayoutParams();
		win.setAttributes(params);
		win.setGravity(Gravity.CENTER);
		dialog.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog
		dialog.setContentView(view);
		view = dialog.getWindow().getDecorView();
		return this;
	}
	public BaseDialog setCenterView(int layoutId) {
		Window win = dialog.getWindow();
		LayoutParams params = new LayoutParams();
		win.setAttributes(params);
		win.setGravity(Gravity.CENTER);
		dialog.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog
		dialog.setContentView(layoutId);
		view = dialog.getWindow().getDecorView();
		return this;
	}

	public BaseDialog setCenterFullScreenWidthView(int layoutId) {
		view = View.inflate(mContext, layoutId, null);
		Window win = dialog.getWindow();
		LayoutParams params = new LayoutParams();
		params.width = screenWidth;
		win.setAttributes(params);
		win.setGravity(Gravity.CENTER);
		dialog.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog
		dialog.setContentView(view, params);
		view = dialog.getWindow().getDecorView();
		return this;
	}

	public BaseDialog setCenterFullScreenWidthView(View view) {
		Window win = dialog.getWindow();
		LayoutParams params = new LayoutParams();
		params.width = screenWidth;
		win.setAttributes(params);
		win.setGravity(Gravity.CENTER);
		dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
		dialog.setContentView(view, params);
//		view = dialog.getWindow().getDecorView();
		this.view = view;
		dialog.getWindow().getDecorView().setLayoutParams(params);
		return this;
	}

	public BaseDialog setBottomView(int layoutId) {
		Window win = dialog.getWindow();
		LayoutParams params = new LayoutParams();
		win.setAttributes(params);
		win.setGravity(Gravity.BOTTOM);
		dialog.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog
		dialog.setContentView(layoutId);
		view = dialog.getWindow().getDecorView();
		return this;
	}
	public BaseDialog setBottomFullScreenWidthView(int layoutId) {

		view = View.inflate(mContext, layoutId, null);
		Window win = dialog.getWindow();
		LayoutParams params = new LayoutParams();
		params.width = screenWidth;
		win.setAttributes(params);
		win.setGravity(Gravity.BOTTOM);
		dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog

		dialog.setContentView(view, params);
//		dialog.setContentView(layoutId);
		dialog.getWindow().getDecorView().setLayoutParams(params);
//		view = dialog.getWindow().getDecorView();
		return this;
	}

	
	public View findView(int id) {
		return this.view.findViewById(id);
	}
	
	public BaseDialog setButtonListener(int buttonId, final ClickListener clickListener, final boolean close) {
		view.findViewById(buttonId).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickListener.onClick();
				if (close) dialog.dismiss();
			}
		});
		return this;
	}
	
	public void setViewVisibility(int viewId, int visibility) {
		view.findViewById(viewId).setVisibility(visibility);
	}
	
	public void setTextViewText(int textViewId, String text) {
		((TextView)view.findViewById(textViewId)).setText(text);
	}
	
	public void show() {
		dialog.show();
	}

	public void dismiss() {
		dialog.dismiss();
	}

	public Dialog getDialog() {
		return this.dialog;
	}

	public void setCancelable(boolean cancelable) {
		dialog.setCancelable(cancelable);
	}

	public interface ClickListener{
		void onClick();
	}
}
