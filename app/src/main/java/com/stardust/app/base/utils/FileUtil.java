package com.stardust.app.base.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {
	private static final String TAG = "FileUtil";
	private static final String CACHE_DIR = "img_cs"; 
	public static final String SAVE_PATH = "/delivery/logo/";

	public static File getCacheFile(String imageUri){
		File cacheFile = null;
		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				File sdCardDir = Environment.getExternalStorageDirectory();
				String fileName = getFileName(imageUri);
				File dir = new File(sdCardDir.getCanonicalPath()
						+ CACHE_DIR);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				cacheFile = new File(dir, fileName);
				Log.i(TAG, "exists:" + cacheFile.exists() + ",dir:" + dir + ",file:" + fileName);
			}  
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "getCacheFileError:" + e.getMessage());
		}
		
		return cacheFile;
	}
	
	public static String getFileName(String path) {
		int index = path.lastIndexOf("/");
		return path.substring(index + 1);
	}
	
	/** 
     * 保存图片的方法 保存到sdcard 
     * zts 2015-01-04 
     */  
    public static void saveImage(Bitmap bitmap, String imageName) {  
        String filePath = isExistsFilePath();  
        FileOutputStream fos = null;  
        File file = new File(filePath, imageName);  
        try {  
            fos = new FileOutputStream(file);  
            if (null != fos) {  
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);  
                fos.flush();  
                fos.close();  
            }  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 获取sd卡的缓存路径， 一般在卡中sdCard就是这个目录 
     *  
     * @return SDPath 
     */  
    public static String getSDPath() {  
        File sdDir = null;  
        boolean sdCardExist = Environment.getExternalStorageState().equals(  
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {  
            sdDir = Environment.getExternalStorageDirectory();// 获取根目录  
        } else {  
            Log.e("ERROR", "没有内存卡");  
        }  
        return sdDir.toString();  
    }  
  
    /** 
     * 获取缓存文件夹目录 如果不存在创建 否则则创建文件夹 
     *  
     * @return filePath 
     */  
    public static String isExistsFilePath() {  
        String filePath = getSDPath() + SAVE_PATH;  
        File file = new File(filePath);  
        if (!file.exists()) {  
            file.mkdirs();  
        }  
        return filePath;  
    }  
    
    /**
	 * 写文件
	 */
	public static void saveFile(String path, String filename, byte[] content){
		try {
            File file = new File(path);
            if(!file.exists()){
            	file.mkdirs();
            }
            file = new File(path + filename);
            if(file.exists()){ //如果存在删除下
            	file.delete();
            }
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

    /**
     * 读文件
     * */
    public static String readFile(File file) {
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

}
