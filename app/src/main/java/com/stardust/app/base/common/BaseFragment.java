package com.stardust.app.base.common;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.stardust.app.base.R;
import com.stardust.app.base.utils.ConnectionUtil;
import com.stardust.app.base.utils.Debug;
import com.stardust.app.base.utils.DialogUtil;

/**
 * 基 Fragment类
 * @author zts
 *
 */
public abstract class BaseFragment extends Fragment {
	protected ViewGroup vgLoading; // 网络加载，网络错误
	protected ViewGroup vgContainer;
	protected LayoutInflater inflater;
	protected Context mContext;
	private boolean firstVisibleToUser = true;
	protected DialogUtil dialogUtil;
	protected Dialog dialog;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		vgContainer = (ViewGroup) inflater.inflate(R.layout.base_fragment,
				container, false);
		vgLoading = (ViewGroup) vgContainer.findViewById(R.id.base_in_loading);
		dialogUtil = new DialogUtil();
		onContentCreate(inflater, container, savedInstanceState);
		initUI(vgContainer);
		return vgContainer;
//		return super.onCreateView(inflater, vgContainer, savedInstanceState);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		this.mContext = context;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initValue();
	}

//	@Override
//	public void setUserVisibleHint(boolean isVisibleToUser) {
//		super.setUserVisibleHint(isVisibleToUser);
//		if (isVisibleToUser) {
//			if (firstVisibleToUser) {
//				initValue();
//				firstVisibleToUser = false;
//			}
//
//		}
//	}

	protected abstract void onContentCreate(LayoutInflater inflater,
											ViewGroup container, Bundle savedInstanceState);
	
	public void addContentView(int layoutResId) {
		inflater.inflate(layoutResId, vgContainer, true);
	}

	protected abstract void initValue();

	protected abstract void initUI(View view);
	
	/*
	 * 显示加载提示
	 */
	protected void showLoading() {
		if (vgLoading != null) {
			vgLoading.bringToFront();
			vgLoading.setVisibility(View.VISIBLE);
		}
	}

	/*
	 * 隐藏加载提示
	 */
	protected void dismissLoading() {
		if (vgLoading != null) {
			vgLoading.setVisibility(View.GONE);
		}
	}

	protected void showProgressDialog() {
		showProgressDialog("");
	}
	protected void showProgressDialog(String message) {
		if (dialog == null) {
			dialog = dialogUtil.progressDialog(mContext, message, null);
		}
		dialog.show();
	}
	protected void dismissProgressDialog() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	protected  void showToast(String content) {
		Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
	}
	protected  void showToastLong(String content) {
		Toast.makeText(mContext, content, Toast.LENGTH_LONG).show();
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
}
