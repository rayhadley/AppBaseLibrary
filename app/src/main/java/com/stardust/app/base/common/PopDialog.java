package com.stardust.app.base.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.stardust.app.base.R;
import com.stardust.app.base.utils.DensityUtils;

import java.lang.reflect.Field;

@SuppressLint("InflateParams")
public class PopDialog {

    private PopupWindow pop;
    private View mView = null;
    private Context mContext;
    private int statusBarHeight = 0;
    private int screenWidth = 0;
    private int screenHeight = 0;
//	private View mScreenView;
//	private TextView tvTitle;
//	private ImageView ivBack;
//	private View titleView;

    public PopDialog(Context context, View view) {
        this.mContext = context;
        this.mView = view;
        getStatusBarHeight();
    }

    private void getStatusBarHeight() {

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
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
            pop = new PopupWindow(mView, screenWidth, screenHeight - statusBarHeight, true);
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
            ColorDrawable dw = new ColorDrawable(0x00000000);
            //设置SelectPicPopupWindow弹出窗体的背景
            pop.setBackgroundDrawable(dw);
            pop.setAnimationStyle(R.style.popwin_anim_style);


            mView.setOnTouchListener(new View.OnTouchListener() {

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

            pop.showAtLocation(mView, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
        } else {
            if (pop.isShowing()) {
                pop.dismiss();
            } else {
                pop.showAtLocation(mView, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
            }
        }
    }

    public void showRightIn() {
        if (pop == null) {
            pop = new PopupWindow();
            pop.setContentView(mView);
//			//设置SelectPicPopupWindow弹出窗体的宽
            pop.setWidth(LayoutParams.FILL_PARENT);
//	        //设置SelectPicPopupWindow弹出窗体的高
//			pop.setHeight(DensityUtil.dip2px(mContext, 300));
            pop.setHeight(LayoutParams.MATCH_PARENT);
//			pop.setOutsideTouchable(true);
            pop.setFocusable(true);
//			pop.setTouchable(true);
            //实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0x0000000);
            //设置SelectPicPopupWindow弹出窗体的背景
            pop.setBackgroundDrawable(dw);
            pop.setAnimationStyle(R.style.popwin_anim_right_in);


            mView.setOnTouchListener(new View.OnTouchListener() {

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

            pop.showAtLocation(mView, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
        } else {
            if (pop.isShowing()) {
                pop.dismiss();
            } else {
                pop.showAtLocation(mView, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
            }
        }
    }

    public void showAsDropDownView(View view, int width, int height) {
        if (pop == null) {

            pop = new PopupWindow();
            pop.setContentView(this.mView);
            if (width == 0) {
                width = DensityUtils.getViewMeasuredWidth(this.mView);
            } else {
                width = DensityUtils.dp2px(mContext, width);
            }
            if (height == 0) {
                height = DensityUtils.getViewMeasuredHeight(this.mView);
            } else {
                height = DensityUtils.dp2px(mContext, height);
            }
            pop.setWidth(width);
            pop.setHeight(height);


            pop.setOutsideTouchable(true);
            pop.setFocusable(true);
            pop.setTouchable(true);
            ColorDrawable dw = new ColorDrawable(0x00000000);
            //设置SelectPicPopupWindow弹出窗体的背景
            pop.setBackgroundDrawable(dw);
//            pop.showAsDropDown(view);
            if (android.os.Build.VERSION.SDK_INT >=24) {
                Rect visibleFrame = new Rect();
                view.getGlobalVisibleRect(visibleFrame);
                int h = view.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
                pop.setHeight(h);
                pop.showAsDropDown(view, 0, 0);
            } else{
                pop.showAsDropDown(view);
            }
        } else {
            if (pop.isShowing()) {
                pop.dismiss();
            } else {
//                pop.showAsDropDown(view);
                if (android.os.Build.VERSION.SDK_INT >=24) {
                    Rect visibleFrame = new Rect();
                    view.getGlobalVisibleRect(visibleFrame);
                    int h = view.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
                    pop.setHeight(h);
                    pop.showAsDropDown(view, 0, 0);
                } else{
                    pop.showAsDropDown(view);
                }
            }
        }
    }

    public void showAsDropDownViewInCenter(View view, int width, int height, int marginTop, int marginLeft) {
        if (pop == null) {

            pop = new PopupWindow();
            pop.setContentView(this.mView);
            if (width == 0) {
                width = DensityUtils.getViewMeasuredWidth(this.mView);
            } else {
                width = DensityUtils.dp2px(mContext, width);
            }
            if (height == 0) {
                height = DensityUtils.getViewMeasuredHeight(this.mView);
            } else {
                height = DensityUtils.dp2px(mContext, height);
            }

            marginTop = DensityUtils.dp2px(mContext, marginTop);
            marginLeft = DensityUtils.dp2px(mContext,marginLeft);
            pop.setWidth(width);
            pop.setHeight(height);


            pop.setOutsideTouchable(true);
            pop.setFocusable(true);
            pop.setTouchable(true);
            ColorDrawable dw = new ColorDrawable(0x00000000);
            //设置SelectPicPopupWindow弹出窗体的背景
            pop.setBackgroundDrawable(dw);
//            pop.showAsDropDown(view);
            if (android.os.Build.VERSION.SDK_INT >=24) {
                int[] a = new int[2];
                view.getLocationInWindow(a);
                int x = a[0] + (view.getWidth()/2-width/2) + marginLeft;
                pop.showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.NO_GRAVITY, x , a[1]+view.getHeight() + marginTop);
            } else{
//                pop.showAsDropDown(view);
                int[] a = new int[2];
                view.getLocationInWindow(a);
                int x = a[0] + (view.getWidth()/2-width/2) + marginLeft;
                pop.showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.NO_GRAVITY, x , a[1]+view.getHeight() + marginTop);
            }
        } else {
            if (pop.isShowing()) {
                pop.dismiss();
            } else {
//                pop.showAsDropDown(view);
                if (android.os.Build.VERSION.SDK_INT >=24) {
                    int[] a = new int[2];
                    view.getLocationInWindow(a);
                    int x = a[0] + (view.getWidth()/2-width/2) + marginLeft;
                    pop.showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.NO_GRAVITY, x , a[1]+view.getHeight() + marginTop);
                } else{
//                    pop.showAsDropDown(view);
                    int[] a = new int[2];
                    view.getLocationInWindow(a);
                    int x = a[0] + (view.getWidth()/2-width/2) + marginLeft;
                    pop.showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.NO_GRAVITY, x , a[1]+view.getHeight() + marginTop);
                }
            }
        }
    }

    public void showAsDropDownViewRightIn(View view) {
        if (pop == null) {
            pop = new PopupWindow(mView, screenWidth, screenHeight - statusBarHeight, true);
            pop.setOutsideTouchable(true);
            pop.setFocusable(true);
            pop.setTouchable(true);
            ColorDrawable dw = new ColorDrawable(0x00000000);
            //设置SelectPicPopupWindow弹出窗体的背景
            pop.setBackgroundDrawable(dw);
            pop.setAnimationStyle(R.style.popwin_anim_right_in);//
//            pop.showAsDropDown(view, 0, 0);
            if (android.os.Build.VERSION.SDK_INT >=24) {
                Rect visibleFrame = new Rect();
                view.getGlobalVisibleRect(visibleFrame);
                int height = view.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
                pop.setHeight(height);
                pop.showAsDropDown(view, 0, 0);
            } else{
                pop.showAsDropDown(view);
            }
        } else {

            if (pop.isShowing()) {
                pop.dismiss();
            } else {
//                pop.showAsDropDown(view, 0, 0);
                if (android.os.Build.VERSION.SDK_INT >=24) {
                    Rect visibleFrame = new Rect();
                    view.getGlobalVisibleRect(visibleFrame);
                    int height = view.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
                    pop.setHeight(height);
                    pop.showAsDropDown(view, 0, 0);
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

    public PopDialog setButtonListener(int buttonId, final ClickListener clickListener, final boolean close) {
        this.mView.findViewById(buttonId).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                clickListener.onClick();
                if (close) close();
            }
        });
        return this;
    }

    public PopDialog setTextViewText(int id, String content) {
        ((TextView) this.mView.findViewById(id)).setText(content);
        return this;
    }

    public interface ClickListener {
        void onClick();
    }
}
