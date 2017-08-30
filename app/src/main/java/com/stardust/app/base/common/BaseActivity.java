package com.stardust.app.base.common;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import com.stardust.app.base.utils.ConnectionUtil;
import com.stardust.app.base.utils.Debug;
import com.stardust.app.base.utils.DialogUtil;
import com.stardust.app.base.R;

/**
 * 基类
 * 统一处理网络加载处理和标题栏
 *
 * @author 07shou
 */
public abstract class BaseActivity extends AppCompatActivity implements OnClickListener, ReloadInterface{
	protected RelativeLayout rlTitle;
	protected ImageView ivBack, ivRight;
	protected TextView tvTitle, tvRight, tvRightNum, tvReload;
	protected FrameLayout content;
	protected LayoutInflater inflater;
	protected Dialog loadingDialog;
	protected SystemBarTintManager tintManager;
	protected Context mContext;
	protected DialogUtil dialogUtil;
	protected Dialog dialog;

	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.mContext = this;
		initDynamicStatusBar();
		setContentView(R.layout.base_activity);
		loadingDialog = new DialogUtil().loadingDialog(this,null);
		dialogUtil = new DialogUtil();
		initView();
	}
	protected abstract void initValue();

	protected abstract void initUI();

	/**
	 *动态状态栏 背景色
	 * */
	protected void initDynamicStatusBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setNavigationBarTintEnabled(true);
			tintManager.setStatusBarTintResource(R.color.color_base);//通知栏所需颜色
		}
	}

	protected void setStatusBarColor(int colorId) {
		tintManager.setStatusBarTintResource(colorId);//通知栏所需颜色
	}

	protected void setTitleBarColor(int colorId) {
		rlTitle.setBackgroundResource(colorId);
	}

	protected void setTitleBarVisable(boolean visable) {
		rlTitle.setVisibility(visable ? View.VISIBLE:View.GONE);
	}

	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	protected void initView() {
		rlTitle = (RelativeLayout) findViewById(R.id.base_in_title);
		ivBack = (ImageView) findViewById(R.id.title_view_iv_left);
		ivRight = (ImageView) findViewById(R.id.title_view_iv_right);
		tvTitle = (TextView) findViewById(R.id.title_view_tv_text);
		tvRight = (TextView) findViewById(R.id.title_view_tv_right);
		tvRightNum = (TextView) findViewById(R.id.title_view_tv_num);
		content = (FrameLayout) findViewById(R.id.base_content);
		tvReload = (TextView) findViewById(R.id.tvReload);
		inflater = LayoutInflater.from(this);
		ivBack.setOnClickListener(this);
		tvReload.setOnClickListener(this);

	}

	protected void addContentView(int resId) {
		View view = inflater.inflate(resId, null);
		content.addView(view);
	}

	/*
	 * 显示加载提示
	 */
	protected void showLoading() {
		if (loadingDialog != null) {
			loadingDialog.show();
		}
	}

	/*
	 * 隐藏加载提示
	 */
	protected void dismissLoading() {
		if (loadingDialog != null) {
			loadingDialog.dismiss();
		}
	}

	protected void showProgressDialog() {
		showProgressDialog("");
	}
	protected void showProgressDialog(String message) {
		if (dialog == null) {
			dialog = dialogUtil.progressDialog(this, message, null);
		}
		dialog.show();
	}
	protected void dismissProgressDialog() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.title_view_iv_left) {
			doFinish();
		} else if (id == R.id.tvReload) {
			reload();
		}
	}
	
	protected void doFinish() {
		finish();
		overridePendingTransition(R.anim.push_right_in,
				R.anim.push_right_out);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			doFinish();
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}

	protected  void showToast(String content) {
		Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
	}
	protected  void showToastLong(String content) {
		Toast.makeText(mContext, content, Toast.LENGTH_LONG).show();
	}
	protected  void showToastLong(String content, int second) {
		Toast.makeText(mContext, content, second).show();
	}

	protected void showReloadView() {
		if (tvReload!= null) {
			tvReload.setVisibility(View.VISIBLE);
		}
	}
	protected void hideReloadView() {
		if (tvReload!= null) {
			tvReload.setVisibility(View.GONE);
		}
	}
	protected void showLog(String msg){
		Debug.show(msg);
	}

	protected boolean isConnection() {
		if (ConnectionUtil.isConnectingToInternet(mContext)) {
			return true;
		}
		return false;
	}

	@Override
	public void reload() {
		hideReloadView();
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		super.onPrepareDialog(id, dialog, args);
	}
}
