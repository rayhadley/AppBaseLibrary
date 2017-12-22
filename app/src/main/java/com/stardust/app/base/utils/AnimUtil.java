package com.stardust.app.base.utils;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import static android.view.View.VISIBLE;

/**
 * 界面切换跳转效果工具类 edit at 2012-8-30
 * 
 * @author zts
 */
public class AnimUtil {
	public static int in, out;

	/**
	 * 清空自己的自定义动画
	 */
	public static void clear(){
		AnimUtil.in = 0;
		AnimUtil.out = 0;
	}

	/**
	 * 设置滑动消失动画
	 */
	public static void getOutAnim(int from, int to, final View moveView){
		Animation translate = new TranslateAnimation(0.0f, 0.0f, (float)from, (float)to);		
		translate.setDuration(600);
		translate.setAnimationListener(new AnimationListener(){
			@Override
			public void onAnimationEnd(Animation animation) {
				moveView.setVisibility(View.GONE);			
			}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationStart(Animation animation) {}			
		});
		moveView.startAnimation(translate);
		//return translate;
	}
	
	/**
	 * 设置滑动出现动画
	 */
	public static void getInAnim(int from, int to, final View moveView){
		Animation translate = new TranslateAnimation(0.0f, 0.0f, (float)from, (float)to);		
		translate.setDuration(600);
		translate.setAnimationListener(new AnimationListener(){
			@Override
			public void onAnimationEnd(Animation animation) {
				moveView.setVisibility(VISIBLE);
			}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationStart(Animation animation) {}			
		});
		moveView.startAnimation(translate);
		//return translate;
	}
	
	/**
	 * 设置上滑进动画
	 */
	public static void pushUpIn(final View moveView, final Handler handler){
		Animation leftIn = new TranslateAnimation(0.0f, 0.0f, -moveView.getHeight(), 0.0f);
		//leftIn.setInterpolator(new AccelerateInterpolator());
		leftIn.setDuration(350);
		moveView.startAnimation(leftIn);
		leftIn.setAnimationListener(new AnimationListener(){
			@Override
			public void onAnimationEnd(Animation animation) {
				moveView.setVisibility(VISIBLE);
				if(handler != null){
					handler.sendEmptyMessage(-1);
				}			
			}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationStart(Animation animation) {}			
		});
	}
	/**
	 * 设置上滑进动画
	 */
	public static void pushUpIn(final View moveView, final int duration, final Handler handler){
		float endY = 0f;
		Animation leftIn = new TranslateAnimation(0.0f, 0.0f, -moveView.getHeight(), endY);
		//leftIn.setInterpolator(new AccelerateInterpolator());
		leftIn.setDuration(duration <= 0 ? 350:duration);
		moveView.startAnimation(leftIn);
		leftIn.setAnimationListener(new AnimationListener(){
			@Override
			public void onAnimationEnd(Animation animation) {
				moveView.setVisibility(VISIBLE);
				if(handler != null){
					handler.sendEmptyMessage(-1);
				}
			}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationStart(Animation animation) {}
		});
	}

	/**
	 * 设置上滑出动画
	 */
	public static void pushUpOut(final View moveView, final int duration, final Handler handler){
		float endY = 0f;
		Animation leftOut = new TranslateAnimation(0.0f, 0.0f, endY, -moveView.getHeight());
		//leftOut.setInterpolator(new AccelerateInterpolator());
		leftOut.setDuration(duration <= 0 ? 350:duration);
		leftOut.setAnimationListener(new AnimationListener(){
			@Override
			public void onAnimationEnd(Animation animation) {
				moveView.setVisibility(View.GONE);
				if(handler != null){
					handler.sendEmptyMessage(-2);
				}
			}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationStart(Animation animation) {}
		});
		moveView.startAnimation(leftOut);
	}

	/**
	 * 设置上滑出动画
	 */
	public static void pushUpOut(final View moveView, final Handler handler){
		Animation leftOut = new TranslateAnimation(0.0f, 0.0f, 0.0f, -moveView.getHeight());
		//leftOut.setInterpolator(new AccelerateInterpolator());
		leftOut.setDuration(350);
		leftOut.setAnimationListener(new AnimationListener(){
			@Override
			public void onAnimationEnd(Animation animation) {
				moveView.setVisibility(View.GONE);
				if(handler != null){
					handler.sendEmptyMessage(-2);
				}
			}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationStart(Animation animation) {}			
		});
		moveView.startAnimation(leftOut);
	}
	/**
	 * 设置右滑进动画
	 */
	public static void pushRightIn(final View moveView, int time, final Handler handler){
		Animation rightIn = new TranslateAnimation(moveView.getWidth(), 0.0f, 0.0f, 0.0f);
		rightIn.setInterpolator(new AccelerateInterpolator());
		rightIn.setDuration(time);
		rightIn.setAnimationListener(new AnimationListener(){
			@Override
			public void onAnimationEnd(Animation animation) {
				moveView.setVisibility(VISIBLE);
				if(handler != null){
					handler.sendEmptyMessage(-1);
				}	
			}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationStart(Animation animation) {}			
		});
		moveView.startAnimation(rightIn);
	}
	/**
	 * 设置右滑出动画
	 */
	public static void pushRightOut(final View moveView, final int time, final Handler handler){
		Animation rightOut = new TranslateAnimation(0.0f, moveView.getWidth(), 0.0f, 0.0f);
		rightOut.setInterpolator(new AccelerateInterpolator());
		rightOut.setDuration(time);
		rightOut.setAnimationListener(new AnimationListener(){
			@Override
			public void onAnimationEnd(Animation animation) {
				moveView.setVisibility(View.INVISIBLE);
				if(handler != null){
					handler.sendEmptyMessage(time);
				}				
			}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationStart(Animation animation) {}			
		});
		moveView.startAnimation(rightOut);
	}
	
	/**
	 * 拖动item切换页面松手后的动画
	 * 
	 * @param view
	 */
	public static void startDownAnimation(View view, final int what, final Handler handler) {
		AnimationSet set = new AnimationSet(true);

		AlphaAnimation alpha = new AlphaAnimation(0.1f, 1.0f);
		alpha.setDuration(420);

		ScaleAnimation scale = new ScaleAnimation(1.2f, 1.0f, 1.2f, 1.0f);
		scale.setDuration(420);

		set.addAnimation(alpha);
		set.addAnimation(scale);

		view.startAnimation(set);
		set.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(what);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {}

			@Override
			public void onAnimationStart(Animation animation) {}
		});
	}
	
	/**
	 * 渐现
	 * @param view
	 */
	public static void AlphaIn(final View view){
		AlphaAnimation alpha = new AlphaAnimation(0.1f, 1.0f);
		alpha.setDuration(400);
		alpha.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				view.setVisibility(VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationStart(Animation animation) {}
		});
		view.startAnimation(alpha);
	}
	
//	/**
//	 * 渐消失
//	 * @param view
//	 */
//	public static void AlphaOut(final View view){
//		AlphaAnimation alpha = new AlphaAnimation(0.1f, 1.0f);
//		alpha.setDuration(200);
//		alpha.setAnimationListener(new AnimationListener() {
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				// TODO Auto-generated method stub
//				view.setVisibility(View.VISIBLE);
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {}
//			@Override
//			public void onAnimationStart(Animation animation) {}
//		});
//		view.startAnimation(alpha);
//	}
	/**
	 * 向下滑动显示
	 * @param view 需要显示的view
	 * @param duration 动画时长 0 或者少于0 默认200毫秒
	 * */
	public static void downShow(final View view, final int duration) {
		view.setVisibility(VISIBLE);
		view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				int panelHeight = view.getHeight();
				ObjectAnimator.ofFloat(view, "translationY", -panelHeight, 0).setDuration(duration > 0 ? duration:200).start();
			}
		});
	}
	/**
	 * 向上滑动隐藏
	 * @param view 需要显示的view
	 * @param duration 动画时长 0 或者少于0 默认200毫秒
	 * */
	public static void upHide(final View view, final int duration) {
//		view.setVisibility(View.GONE);
		int panelHeight = view.getHeight();
		ObjectAnimator.ofFloat(view, "translationY", 0, -panelHeight).setDuration(duration > 0 ? duration:200).start();
	}

	/**
	 * 向上滑动显示
	 * @param view 需要显示的view
	 * @param duration 动画时长 0 或者少于0 默认200毫秒
	 * */
	public static void upShow(final View view, final int duration) {
		view.setVisibility(VISIBLE);
		view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				int panelHeight = view.getHeight();
				ObjectAnimator.ofFloat(view, "translationY", panelHeight, 0).setDuration(duration > 0 ? duration:200).start();
			}
		});
	}
	/**
	 * 向下滑动隐藏
	 * @param view 需要显示的view
	 * @param duration 动画时长 0 或者少于0 默认200毫秒
	 * */
	public static void downHide(final View view, final int duration) {
//		view.setVisibility(View.GONE);
		int panelHeight = view.getHeight();
		ObjectAnimator.ofFloat(view, "translationY", 0, -panelHeight).setDuration(duration > 0 ? duration:200).start();
	}


	/**
	 * 旋转箭头向上
	 * @param iv 箭头ImageView
	 * */
	public static void rotateArrowUpAnimation(final ImageView iv) {
		if (iv == null) return;
		RotateAnimation animation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(200);
		animation.setFillAfter(true);
		iv.startAnimation(animation);
	}

	/**
	 * 旋转箭头向下
	 * @param iv 箭头ImageView
	 * */
	public static void rotateArrowDownAnimation(final ImageView iv) {
		if (iv == null) return;
		RotateAnimation animation = new RotateAnimation(180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(200);
		animation.setFillAfter(true);
		iv.startAnimation(animation);
	}

}
