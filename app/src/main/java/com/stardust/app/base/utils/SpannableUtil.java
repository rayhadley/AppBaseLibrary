package com.stardust.app.base.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * 界面切换跳转效果工具类 edit at 2012-8-30
 * 
 * @author zts
 */
public class SpannableUtil {
	 
	public static void setSpannable(TextView tv){
		SpannableStringBuilder builder = new SpannableStringBuilder(tv.getText().toString());  
		  
		//ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色  
		ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);  
		ForegroundColorSpan whiteSpan = new ForegroundColorSpan(Color.WHITE);  
		ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.BLUE);  
		ForegroundColorSpan greenSpan = new ForegroundColorSpan(Color.GREEN);  
		ForegroundColorSpan yellowSpan = new ForegroundColorSpan(Color.YELLOW);  
		 
		builder.setSpan(redSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
		builder.setSpan(whiteSpan, 1, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);  
		builder.setSpan(blueSpan, 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
		builder.setSpan(greenSpan, 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
		builder.setSpan(yellowSpan, 4,5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
		  
		tv.setText(builder);  
	}
	
	public static void setSpannable(int color, int start, int end, TextView tv){
		SpannableStringBuilder builder = new SpannableStringBuilder(tv.getText().toString());  
		  
		//ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色  
		ForegroundColorSpan redSpan = new ForegroundColorSpan(color);  
		 
		builder.setSpan(redSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);  
		 
		tv.setText(builder);  
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
}
