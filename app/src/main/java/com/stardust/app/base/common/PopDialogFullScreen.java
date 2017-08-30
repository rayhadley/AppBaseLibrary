package com.stardust.app.base.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.stardust.app.base.R;

import java.lang.reflect.Field;

@SuppressLint("InflateParams")
public class PopDialogFullScreen {

	private PopupWindow pop;
	private View mView = null;
	private Context mContext;
	private int statusBarHeight = 0;
	private int screenWidth = 0;
	private int screenHeight = 0;
	private View mScreenView;
	private TextView tvTitle;
	private ImageView ivBack;
	private View titleView;
	
	public PopDialogFullScreen(Context context, View view) {
		this.mContext = context;
		this.mView = view;
		initView();
		getStatusBarHeight();
	}
	
	private void initView() {
		mScreenView = LayoutInflater.from(mContext).inflate(R.layout.pop_dialog_base, null);
		if (mView != null) {
			((LinearLayout)mScreenView.findViewById(R.id.screening_view)).addView(this.mView);
		}
		
	}
	
	public PopDialogFullScreen setTitle(String title) {
		tvTitle.setText(title);
		return this;
	}
	
	private void getStatusBarHeight() {

		DisplayMetrics  dm = new DisplayMetrics();
		((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		
		Class<?> c = null;
		 
	      Object obj = null;
	 
	      Field field = null;
	 
	      int x = 0;
	 
	try {
	 
	        c = Class.forName("com.android.internal.R$dimen");
	 
	        obj = c.newInstance();
	 
	        field = c.getField("status_bar_height");
	 
	        x = Integer.parseInt(field.get(obj).toString());
	 
	        statusBarHeight = mContext.getResources().getDimensionPixelSize(x);
	 
	} catch (Exception e1) {
	 
	 
	        e1.printStackTrace();
	}
	}
	
	@SuppressWarnings("deprecation")
	public void show() {
		if (pop == null) {
			pop = new PopupWindow(mScreenView, screenWidth,screenHeight - statusBarHeight, true);
//			pop = new PopupWindow();
//			pop.setContentView(mScreenView);
//			//设置SelectPicPopupWindow弹出窗体的宽
//			pop.setWidth(LayoutParams.FILL_PARENT);
//	        //设置SelectPicPopupWindow弹出窗体的高
//			pop.setHeight(LayoutParams.MATCH_PARENT);
//			pop.setOutsideTouchable(true);
			pop.setFocusable(true);
//			pop.setTouchable(true);
			 //实例化一个ColorDrawable颜色为半透明  
//	        ColorDrawable dw = new ColorDrawable(0xb0000000);
			ColorDrawable dw = new ColorDrawable(0x50000000);
	        //设置SelectPicPopupWindow弹出窗体的背景  
			pop.setBackgroundDrawable(dw);
			pop.setAnimationStyle(R.style.PopuDialog);


			mScreenView.setOnTouchListener(new View.OnTouchListener() {

				@Override
				@SuppressLint("ClickableViewAccessibility")
				public boolean onTouch(View v, MotionEvent event) {

					int height = mView.getMeasuredHeight();
					int y = (int) event.getY();
					if (event.getAction() == MotionEvent.ACTION_UP) {
						if (y < height) {
							pop.dismiss();
						}
					}
					return true;
				}
			});

			pop.showAtLocation(mScreenView, Gravity.BOTTOM|Gravity.CENTER, 0, 0);
		} else {
			if (pop.isShowing()) {
				pop.dismiss();
			} else {
				pop.showAtLocation(mScreenView, Gravity.BOTTOM|Gravity.CENTER, 0, 0);
			}
		}
	}

	public void showInCenter() {
		if (pop == null) {
			pop = new PopupWindow();
			pop.setContentView(mScreenView);
//			//设置SelectPicPopupWindow弹出窗体的宽
			pop.setWidth(LayoutParams.MATCH_PARENT);
//	        //设置SelectPicPopupWindow弹出窗体的高
//			pop.setHeight(DensityUtil.dip2px(mContext, 300));
			pop.setHeight(LayoutParams.MATCH_PARENT);
//			pop.setOutsideTouchable(true);
			pop.setFocusable(true);
//			pop.setTouchable(true);
			//实例化一个ColorDrawable颜色为半透明
			ColorDrawable dw = new ColorDrawable(0x50000000);
			//设置SelectPicPopupWindow弹出窗体的背景
			pop.setBackgroundDrawable(dw);
			pop.setAnimationStyle(R.style.PopuDialog);

			LinearLayout linearLayout = (LinearLayout)mScreenView.findViewById(R.id.screening_view);
			linearLayout.setGravity(Gravity.CENTER);


			mScreenView.setOnTouchListener(new View.OnTouchListener() {

				@Override
				@SuppressLint("ClickableViewAccessibility")
				public boolean onTouch(View v, MotionEvent event) {

					int height = mView.getMeasuredHeight();
					int y = (int) event.getY();
					if (event.getAction() == MotionEvent.ACTION_UP) {
						if (y < height) {
							pop.dismiss();
						}
					}
					return true;
				}
			});

			pop.showAtLocation(mScreenView, Gravity.CENTER, screenWidth/2, screenHeight/2);
		} else {
			if (pop.isShowing()) {
				pop.dismiss();
			} else {
//				pop.showAtLocation(mScreenView, Gravity.BOTTOM|Gravity.CENTER, 0, 0);
				pop.showAtLocation(mScreenView, Gravity.CENTER, screenWidth/2, screenHeight/2);
			}
		}
	}

	public void  showRightIn() {
		if (pop == null) {
			pop = new PopupWindow();
			pop.setContentView(mScreenView);
//			//设置SelectPicPopupWindow弹出窗体的宽
			pop.setWidth(LayoutParams.FILL_PARENT);
//	        //设置SelectPicPopupWindow弹出窗体的高
//			pop.setHeight(DensityUtil.dip2px(mContext, 300));
			pop.setHeight(LayoutParams.MATCH_PARENT);
//			pop.setOutsideTouchable(true);
			pop.setFocusable(true);
//			pop.setTouchable(true);
			//实例化一个ColorDrawable颜色为半透明
			ColorDrawable dw = new ColorDrawable(0xcecece);
			//设置SelectPicPopupWindow弹出窗体的背景
			pop.setBackgroundDrawable(dw);
			pop.setAnimationStyle(R.style.popwin_anim_right_in);


			mScreenView.setOnTouchListener(new View.OnTouchListener() {

				@Override
				@SuppressLint("ClickableViewAccessibility")
				public boolean onTouch(View v, MotionEvent event) {

					int height = mView.getMeasuredHeight();
					int y = (int) event.getY();
					if (event.getAction() == MotionEvent.ACTION_UP) {
						if (y < height) {
							pop.dismiss();
						}
					}
					return true;
				}
			});

			pop.showAtLocation(mScreenView, Gravity.BOTTOM|Gravity.CENTER, 0, 0);
		} else {
			if (pop.isShowing()) {
				pop.dismiss();
			} else {
				pop.showAtLocation(mScreenView, Gravity.BOTTOM|Gravity.CENTER, 0, 0);
			}
		}
	}
	
	public PopDialogFullScreen hideTitleBar() {
		titleView.setVisibility(View.GONE);
		return this;
	}

	public void showAsDropDownViewRightIn(View view) {
		if (pop == null) {
			pop = new PopupWindow(mScreenView, screenWidth,screenHeight - statusBarHeight, true);
			pop.setOutsideTouchable(true);
			pop.setFocusable(true);
			pop.setTouchable(true);
			ColorDrawable dw = new ColorDrawable(0x70000000);
			//设置SelectPicPopupWindow弹出窗体的背景
			pop.setBackgroundDrawable(dw);
			pop.setAnimationStyle(R.style.popwin_anim_right_in);//
//			pop.showAsDropDown(view, 0, 0);
			if (android.os.Build.VERSION.SDK_INT >=24) {
				int[] a = new int[2];
				view.getLocationInWindow(a);
				pop.showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0 , a[1]+view.getHeight());
			} else{
				pop.showAsDropDown(view);
			}
		} else {
			if (pop.isShowing()) {
				pop.dismiss();
			} else {
//				pop.showAsDropDown(view, 0, 0);
				if (android.os.Build.VERSION.SDK_INT >=24) {
					int[] a = new int[2];
					view.getLocationInWindow(a);
					pop.showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0 , a[1]+view.getHeight());
				} else{
					pop.showAsDropDown(view);
				}
			}
		}
	}

	public void showAsDropDownView(View view) {
		if (pop == null) {
			pop = new PopupWindow();
			pop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            pop.setContentView(mScreenView);
			pop.setOutsideTouchable(true);
			pop.setFocusable(true);
			pop.setTouchable(true);
			ColorDrawable dw = new ColorDrawable(0x70000000);
			//设置SelectPicPopupWindow弹出窗体的背景
			pop.setBackgroundDrawable(dw);
			mScreenView.setOnTouchListener(new View.OnTouchListener() {

				@Override
				@SuppressLint("ClickableViewAccessibility")
				public boolean onTouch(View v, MotionEvent event) {

					int height = mScreenView.getMeasuredHeight();
					int y = (int) event.getY();
					if (event.getAction() == MotionEvent.ACTION_UP) {
						if (y < height) {
							pop.dismiss();
						}
					}
					return true;
				}
			});
			if (android.os.Build.VERSION.SDK_INT >=24) {
				int[] a = new int[2];
				view.getLocationInWindow(a);
				pop.showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0 , a[1]+view.getHeight());
			} else{
				pop.showAsDropDown(view);
			}
//			pop.showAsDropDown(view, 0, 0);
		} else {
			if (pop.isShowing()) {
				pop.dismiss();
			} else {
//				pop.showAsDropDown(view, 0, 0);
				if (android.os.Build.VERSION.SDK_INT >=24) {
					int[] a = new int[2];
					view.getLocationInWindow(a);
					pop.showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0 , a[1]+view.getHeight());
				} else{
					pop.showAsDropDown(view);
				}
			}
		}
	}

	public void close() {
		if (pop != null && pop.isShowing()) {
			pop.dismiss();
		}
	}

	public boolean isOpen() {
        return pop.isShowing();
    }

	public PopDialogFullScreen setButtonListener(int buttonId, final ClickListener clickListener, final boolean close) {
		this.mView.findViewById(buttonId).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				clickListener.onClick();
				if (close) close();
			}
		});
		return this;
	}

	public PopDialogFullScreen setTextViewText(int id, String content) {
		((TextView)this.mView.findViewById(id)).setText(content);
		return this;
	}
	
	public interface ClickListener {
		void onClick();
	}
}
