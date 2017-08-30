package com.stardust.app.base.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class TakePictureUtil {
	
	/**
	 * 启动相机拍照, 请在onActivityResult中获取， 
	 * requestCode == 1，intent里面的 数据是个 file
	 */
	public File startCamera(Activity activity) {
		File tempFile;

		try {
			String status = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(status)) {
				File dataDir = Environment.getExternalStorageDirectory();
				File myDir = new File(dataDir, "/DCIM/Camera");
				myDir.mkdirs();
				String mDirectoryName = dataDir.toString() + "/DCIM/Camera";
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd-hhmmss", Locale.SIMPLIFIED_CHINESE);
				tempFile = new File(mDirectoryName, sdf.format(new Date())
						+ ".jpg");
				if (tempFile.isFile()) {
					tempFile.delete();
				}
				Uri imageFile = Uri.fromFile(tempFile);
				Intent cameraIntent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFile);
				activity.startActivityForResult(cameraIntent, 1);
				
				return tempFile;
			} else {
				Toast.makeText(activity.getApplicationContext(), "请插入存储卡",
						Toast.LENGTH_SHORT).show();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(activity.getApplicationContext(), "拍照程序启动失败",
					Toast.LENGTH_SHORT).show();
			return null;
		}
	}

	/**
	 * 开启图片查看器选择图片
	 * 在onActivityResult 返回的数据是图片 Uri，
	 * requestCode:需返回的 requestCode
	 */
	public void selectPictrue(Activity activity) {
		try {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			activity.startActivityForResult(intent, 2);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(activity.getApplicationContext(), "图片文件查看程序启动失败",
					Toast.LENGTH_SHORT).show();
			return;
		}
	}
	
	/**
	 * 处理照相机返回的图片数据
	 * @param tempFile :是启动照相机时生成的那个file
	 */
	public HashMap<String, Object> onStartCammeraResult(Activity activity, File tempFile,
			int width, int height){
		if (tempFile == null) {
			Toast.makeText(activity.getApplicationContext(), "操作失败",
					Toast.LENGTH_SHORT).show();
			return null;
		}
		HashMap<String, Object> hm = new HashMap<String, Object>();
		String tempPath = tempFile.getPath();
		Bitmap tempBitmap = ImageUtil.getSizedBitmap(width, height, tempPath);
		if(tempBitmap == null) {
			return null;
		}
		hm.put("bitmap", tempBitmap);
		hm.put("path", tempPath);
		return hm;
	}
	
	/**
	 * 处理图库返回的图片数据
	 * @param data
	 * since v6.0
	 */
	public HashMap<String, Object> onStartPicDepotResult(Activity activity, Intent data,
			int width, int height){
		if (data == null) {
			Toast.makeText(activity.getApplicationContext(), "图片获取失败",
					Toast.LENGTH_SHORT).show();
			return null;
		}
		HashMap<String, Object> hm = null;
		Bitmap bitmap = null;
		String tempFilePath = null;
		Uri select_image_uri = data.getData();
		try {
			ContentResolver resolver = activity.getContentResolver();
			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cur = resolver.query(select_image_uri, projection, null,
					null, null);
			if (cur != null) {
				int index = cur
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cur.moveToFirst();
				tempFilePath = cur.getString(index); // 保存图片的路径
			}else {    //当cursor为空时，表明是从本地file获取， 可以看下getDataString()是不是有图片路径的
				String path = data.getDataString();
				int i = path.lastIndexOf('.');
				String sub = path.substring(i, path.length());
				if (".png".equalsIgnoreCase(sub)
						|| ".jpg".equalsIgnoreCase(sub) || ".jpeg".equalsIgnoreCase(sub)) {
					path = path.substring(path.indexOf("//") + 2);
					bitmap = ImageUtil.getSizedBitmap(width, height,
							path);
					if (bitmap != null) {
						hm = new HashMap<String, Object>();
						hm.put("bitmap", bitmap);
						hm.put("path", path);
						return hm;
					}else {
						return null;
					}
				}else {
					return null;
				}
			}
			if (!"".equals(tempFilePath)) {
				int i = tempFilePath.lastIndexOf('.');
				String sub = tempFilePath.substring(i, tempFilePath.length());
				if (".png".equalsIgnoreCase(sub)
						|| ".jpg".equalsIgnoreCase(sub) || ".jpeg".equalsIgnoreCase(sub)) {

					bitmap = ImageUtil.getSizedBitmap(width, height,
							tempFilePath);
					if (bitmap != null) {
						hm = new HashMap<String, Object>();
						hm.put("bitmap", bitmap);
						hm.put("path", tempFilePath);
						return hm;
					}else {
						return null;
					}
				} else {				
					Toast.makeText(activity.getApplicationContext(), "请选择png或jpg格式的图片",
							Toast.LENGTH_SHORT).show();
					return null;
				}
			} else {
				Toast.makeText(activity.getApplicationContext(), "图片获取失败",
						Toast.LENGTH_SHORT).show();
				return null;
			}
		} catch (Exception e) {
			Toast.makeText(activity.getApplicationContext(), "图片获取失败",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 进入截图选择
	 * action 类型：
	 * 1.拍照进入com.android.camera.action.CROP
	 * 2.相册选择进入：Intent.ACTION_GET_CONTENT
	 */
	public void startCropImage(int actionType, Activity activity, Uri inputUri, Uri outputUri){
		Intent intent = new Intent();
		System.out.println("cropImageTop data->"+ (inputUri == null));
		if(actionType == 1){
			intent.setAction("com.android.camera.action.CROP");
			intent.setDataAndType(inputUri, "image/*"); //来源	
			//intent.putExtra("crop", "true");
		}else {
			intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");		//当是图片选择时直接进入
			//intent.putExtra("crop", "circle");
		}
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 4); //裁剪框比例
		intent.putExtra("aspectY", 4);
		intent.putExtra("outputX", 250); //输入大小
		intent.putExtra("outputY", 250);
		intent.putExtra("scale", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri); //裁截后输出到uri
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//输出格式
		intent.putExtra("noFaceDetection", true); // no face detection
		activity.startActivityForResult(intent, 3);
	}	
	
	/**
	 * 生成文件名
	 * @return
	 */
	public File getFileName(){
		String fileName = Environment.getExternalStorageDirectory() + "/fixjob/temp/";
		
		File f = new File(fileName);
		if(!f.exists()){
			f.mkdirs();
		}
		fileName = fileName + TimeUtil.getTimeForFormat(
				"yyyyMMddHHmmss", System.currentTimeMillis()) + ".jpg";
		f = new File(fileName);
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return f;
	}
}
