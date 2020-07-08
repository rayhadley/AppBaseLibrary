package com.stardust.app.base.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * 
* @Description: (图片处理 ) 
* @author 唐飞
* @date 2015年7月10日 下午6:06:35 *
 */
public class BitmapUtils {
	/**
	 * 获取正方形图片
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getSquareBitmap(Bitmap bitmap) {
		int imageWidth = bitmap.getWidth();
		int imageHeight = bitmap.getHeight();
		int y = 0;
		Bitmap image = null;
		if (imageHeight > imageWidth) {
			y = (imageHeight - imageWidth) / 2;
			image = Bitmap.createBitmap(bitmap, 0, y, imageWidth, imageWidth);
		} else {
			y = (imageWidth - imageHeight) / 2;
			image = Bitmap.createBitmap(bitmap, y, 0, imageHeight, imageHeight);
		}
		return image;
	}

	/**
	 * 缩小图片
	 * 
	 * @param Path
	 *            文件sd卡路径
	 * @param reqWidth
	 *            缩小的宽度
	 * @param reqHeight
	 *            缩小的高度
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromPath(String Path, int reqWidth,
			int reqHeight) {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(Path, options);
		// 调用上面定义的方法计算inSampleSize值
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		// 使用获取到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(Path, options);
	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);
		// 调用上面定义的方法计算inSampleSize值
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		// 使用获取到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}
	/**
	 * 从流里获取图片
	 * 流不能使用2次，所以要把里面的数据取出来操作
	 * @param inputStream
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromStream(InputStream inputStream, int reqWidth,
			int reqHeight){
		try {
			byte[] data = getBytes(inputStream);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(data, 0, data.length, options);
			options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
			options.inJustDecodeBounds = false;
			return BitmapFactory.decodeByteArray(data, 0, data.length, options);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 把inputStrean 转成 byte
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytes(InputStream inputStream) throws IOException{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0 ;
		while((len = inputStream.read(buffer)) != -1){
			outputStream.write(buffer, 0, len);
		}
		outputStream.close();
		return outputStream.toByteArray();
	}

	/**
	 * 计算缩放尺寸
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > width) {// 竖着拍
			if (height > reqHeight || width > reqWidth) {
				// 计算出实际宽高和目标宽高的比率
				final int heightRatio = Math.round((float) height
						/ (float) reqHeight);
				final int widthRatio = Math.round((float) width
						/ (float) reqWidth);
				// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
				// 一定都会大于等于目标的宽和高。
				inSampleSize = heightRatio < widthRatio ? heightRatio
						: widthRatio;
			}
		} else {// 横这拍
			if (height > reqWidth || width > reqHeight) {
				// 计算出实际宽高和目标宽高的比率
				final int heightRatio = Math.round((float) height
						/ (float) reqWidth);
				final int widthRatio = Math.round((float) width
						/ (float) reqHeight);
				// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
				// 一定都会大于等于目标的宽和高。
				inSampleSize = heightRatio < widthRatio ? heightRatio
						: widthRatio;
			}
		}
		return inSampleSize;
	}

	/**
	 * 图片缩放
	 */
	public static Bitmap scaleImage(Bitmap bitmap, int newWidth) {
		// 获得图片的宽高
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// 计算缩放比例
		float scale = calculateScale(height, width, newWidth);
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
				true);
		return newbm;
	}

	/** 取最小缩放比例 **/
	public static float calculateScale(int realHeight, int relWidth,
			int newWidth) {
		float height = (float) newWidth / realHeight;
		float width = (float) newWidth / relWidth;
		return height < width ? width : height;
	}

	/**
	 * 根据宽度缩放
	 * 
	 * @param bitmap
	 * @param newWidth
	 * @return
	 */
	public static Bitmap scaleWidthImage(Bitmap bitmap, int newWidth) {
		// 获得图片的宽高
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// 计算缩放比例
		float scale = (float) newWidth / width;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
				true);
		return newbm;
	}

	/**
	 * 获取圆角图片
	 * @param bitmap
	 * @param pixels
	 * @return
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
	/**
	 * 图片加上水印
	 * @param src 底图
	 * @param waterMak
	 * @param title 文字
	 * @return
	 */
	public static Bitmap createBitmap(Bitmap src, Bitmap waterMak, String title) {
		if (src == null) {
			return src;
		}
		// 获取原始图片与水印图片的宽与高
		int w = src.getWidth();
		int h = src.getHeight();
		Bitmap newBitmap = Bitmap.createBitmap(w, h, Config.RGB_565);
		Canvas mCanvas = new Canvas(newBitmap);
		// 往位图中开始画入src原始图片
		mCanvas.drawBitmap(src, 0, 0, null);
		// 在src的右下角添加水印
		Paint paint = new Paint();
		if(waterMak != null){
			int ww = waterMak.getWidth();
			int wh = waterMak.getHeight();
			mCanvas.drawBitmap(waterMak, w - ww - 5, h - wh - 5, paint);			
		}
		// 开始加入文字
		if (null != title) {
			Paint textPaint = new Paint();
			textPaint.setColor(Color.RED);
			textPaint.setTextSize(16);
			String familyName = "宋体";
			Typeface typeface = Typeface.create(familyName,
					Typeface.BOLD_ITALIC);
			textPaint.setTypeface(typeface);
			textPaint.setTextAlign(Align.CENTER);
			mCanvas.drawText(title, w / 2, 25, textPaint);

		}
//		mCanvas.save(Canvas.ALL_SAVE_FLAG);
		mCanvas.save();
		mCanvas.restore();
		return newBitmap;
	}
	/**
	 * 获取网络视频的第一帧
	 * @param url
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap createVideoThumbnail(String url, int width, int height){
	    Bitmap bitmap = null;
	    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
	    int kind = MediaStore.Video.Thumbnails.MINI_KIND;
	    try {
            if(Build.VERSION.SDK_INT >= 14){
                retriever.setDataSource(url, new HashMap<String, String>());
            }else{
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{
                retriever.release();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
	    if(kind == Images.Thumbnails.MICRO_KIND && bitmap != null){
	        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
	    }
	    return bitmap;
	}

	/**
	 * 从网络获取bitmap
	 * */
	public static Bitmap getBitmapFromUrl(Context context, String urlString) {
		URL url = null;
		try {
			url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(8000);
			conn.connect();


//			BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//			DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//			bitmapOptions.inScreenDensity = metrics.densityDpi;
//			bitmapOptions.inTargetDensity =  metrics.densityDpi;
//			bitmapOptions.inDensity = DisplayMetrics.DENSITY_DEFAULT;
			Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());//加粗部分很重要，必须使用这个三参数的函数，因为该函数，还有一个只需要inputstream。
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		int i;
		int j;
		if (bmp.getHeight() > bmp.getWidth()) {
			i = bmp.getWidth();
			j = bmp.getWidth();
		} else {
			i = bmp.getHeight();
			j = bmp.getHeight();
		}

		Bitmap localBitmap = Bitmap.createBitmap(i, j, Config.RGB_565);
		Canvas localCanvas = new Canvas(localBitmap);

		while (true) {
			localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0,i, j), null);
			if (needRecycle)
				bmp.recycle();
			ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
			localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
					localByteArrayOutputStream);
			localBitmap.recycle();
			byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
			try {
				localByteArrayOutputStream.close();
				return arrayOfByte;
			} catch (Exception e) {
				//F.out(e);
			}
			i = bmp.getHeight();
			j = bmp.getHeight();
		}

	}

	/**
	 * 把Bitmap转Byte
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
}