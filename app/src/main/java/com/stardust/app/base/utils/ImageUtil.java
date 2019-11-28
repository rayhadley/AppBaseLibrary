package com.stardust.app.base.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.stardust.app.base.R;
import com.stardust.app.base.http.AuthImageDownloader;
import com.stardust.app.base.view.CircleDrawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 图片工具类
 *
 * @author zts edit at 2013-01-17
 */
public class ImageUtil {

    /***
     * 要使用工具需要先在application中调用该初始化方法  http 显示
     */
    public static void init(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .threadPoolSize(3) // 设置线程数量为3
                .threadPriority(Thread.NORM_PRIORITY - 2)// 设置线程的优先级
                .denyCacheImageMultipleSizesInMemory()// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                .discCacheFileNameGenerator(new Md5FileNameGenerator())// 设置缓存文件的名字
                .discCacheFileCount(160)// 缓存文件的最大个数
                .memoryCacheExtraOptions(200, 200)  // 设定缓存在内存的图片大小最大为200x200
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .tasksProcessingOrder(QueueProcessingType.LIFO)// 设置图片下载和显示的工作队列排序
                .build();
        ImageLoader.getInstance().init(config);
    }

    /***
     * 要使用工具需要先在application中调用该初始化方法  https 显示
     */
    public static void initHttps(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .threadPoolSize(3) // 设置线程数量为3
                .threadPriority(Thread.NORM_PRIORITY - 2)// 设置线程的优先级
                .denyCacheImageMultipleSizesInMemory()// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                .discCacheFileNameGenerator(new Md5FileNameGenerator())// 设置缓存文件的名字
                .discCacheFileCount(160)// 缓存文件的最大个数
                .memoryCacheExtraOptions(200, 200)  // 设定缓存在内存的图片大小最大为200x200
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .tasksProcessingOrder(QueueProcessingType.LIFO)// 设置图片下载和显示的工作队列排序
                .imageDownloader(new AuthImageDownloader(context))       //替换允许Https的图片加载
                .build();
        ImageLoader.getInstance().init(config);
    }

    //----------------------------------------------------------------------------------------------------------------------------
    private static DisplayImageOptions options;
    private static ImageLoader mImageLoader;
    private static ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public static void displayImage(String url, ImageView imageView) {
        displayImage(url, imageView, null);
    }

    public static void displayImage(String url, ImageView imageView, final ImageView.ScaleType scaleType) {
        displayImage(url, imageView, 0, 0, scaleType);
    }

    public static void displayImage(String url, ImageView imageView, int width, int height, final ImageView.ScaleType scaleType) {
        //以后处理

        if (url != null && !url.trim().equals("")) {

            Log.i("JLShop", "处理前:" + url);
            if (width > 0 && height > 0) {
                String tmp1 = url.substring(0, url.lastIndexOf("."));
                String tmp2 = url.substring(url.lastIndexOf("."));
                StringBuilder sb = new StringBuilder();
                sb.append(tmp1)
                        .append("_")
                        .append(width)
                        .append("x")
                        .append(height)
                        .append(tmp2);
                url = sb.toString();
            }
            url = url.trim();
        }
        Log.i("JLShop", "处理后:" + url);
        if (scaleType == null) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView.setScaleType(scaleType);
        }
        ImageLoader.getInstance().displayImage(url, imageView, getDisplayImageOptions(), animateFirstListener);
    }

    public static void displayCircleImage(final Context context, String url, final ImageView imageView) {
        ImageLoader.getInstance().loadImage(url, getDisplayImageOptions(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//FIT_CENTER
                if (bitmap == null) {
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_empty);
                }
                CircleDrawable drawable = new CircleDrawable(bitmap);
                imageView.setImageDrawable(drawable);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }

    public static void loadImage(final Context context, String url) {
        if (url != null) {
            url = url.trim();
        }
        ImageLoader.getInstance().loadImage(url, getDisplayImageOptions(), animateFirstListener);
    }

//	private static ImageLoader getImageLoader(Activity context) {
//		if (mImageLoader == null) {
//			int maxWidth = DensityUtil.getScreenWidth(context);
//			int maxHeight = DensityUtil.getScreenHeight(context);
//			File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader/Cache");
//			ImageLoaderConfiguration config = new ImageLoaderConfiguration
//			          .Builder(context)
////			          .memoryCacheExtraOptions(480, 800) // maxwidth, max height，即保存的每个缓存文件的最大长宽
//			          .memoryCacheExtraOptions(maxWidth, maxHeight) // maxwidth, max height，即保存的每个缓存文件的最大长宽
//			          .threadPoolSize(3)//线程池内加载的数量
//			          .threadPriority(Thread.NORM_PRIORITY -2)
//			          .denyCacheImageMultipleSizesInMemory()
//			           .memoryCache(new UsingFreqLimitedMemoryCache(2* 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
//			           .memoryCacheSize(2 * 1024 * 1024)
//			          .discCacheSize(50 * 1024 * 1024)
////			          .discCacheFileNameGenerator(newMd5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
//			           .tasksProcessingOrder(QueueProcessingType.LIFO)
//			           .discCacheFileCount(100) //缓存的文件数量
//			           .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
//			           .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
//			           .imageDownloader(new BaseImageDownloader(context,5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
//			           .writeDebugLogs() // Remove for releaseapp
//			          .build();//开始构建
//			mImageLoader = ImageLoader.getInstance();
//			mImageLoader.init(config);
//		}
//		return mImageLoader;
//	}

    private static DisplayImageOptions getDisplayImageOptions() {
        if (options == null) {
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.ic_empty)            //加载图片时的图片
                    .showImageForEmptyUri(R.drawable.ic_empty)         //没有图片资源时的默认图片
                    .showImageOnFail(R.drawable.ic_empty)              //加载失败时的图片
                    .cacheInMemory(true)                               //启用内存缓存
                    .cacheOnDisk(true)                                 //启用外存缓存
                    .considerExifParams(true)                          //启用EXIF和JPEG图像格式
                    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
//			          .displayer(new RoundedBitmapDisplayer(2))         //设置显示风格这里是圆角矩形(设置这里会导致图片拉伸变形)
//			          .displayer(new SimpleBitmapDisplayer())         //设置显示风格
                    .build();
        }
        return options;
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
    //-------------------------------------------------------------------------------------

//	/**
//	 * 获取想要的固定长和宽
//	 * @param mWidth
//	 * @param mHeight
//	 * @param path
//	 * @return
//	 */
//	public static Bitmap getSizedBitmap(int mWidth, int mHeight, String path) {
//		Bitmap bmp = BitmapFactory.decodeFile(path);
//		bmp = zoomBitmap(bmp, mWidth, mHeight);
//		return bmp;
//	}

    /**
     * 按等比例获取图片
     *
     * @param mWidth
     * @param mHeight
     * @return
     */
    public static Bitmap getSizedBitmap(int mWidth, int mHeight, String path) {
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(path, bmpFactoryOptions);
        float heightRatio = (float) Math.ceil(bmpFactoryOptions.outHeight
                / mHeight);
        float widthRatio = (float) (Math.ceil(bmpFactoryOptions.outWidth
                / mWidth));

        // 判断是否要进行缩放
        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                // 高度变化大,按高度缩放
                bmpFactoryOptions.inSampleSize = (int) heightRatio;
            } else {
                // 宽度变化大,按宽度缩放
                bmpFactoryOptions.inSampleSize = (int) widthRatio;
            }
        }
        bmpFactoryOptions.inJustDecodeBounds = false;
        bmp = zoomBitmap(BitmapFactory.decodeFile(path, bmpFactoryOptions), mWidth, mHeight);
        return bmp;
    }

    public static Bitmap getSizedBitmap(int mWidth, int mHeight, Bitmap b) {
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        byte[] arr = Bitmap2Bytes(b, true);
        Bitmap bmp = BitmapFactory.decodeByteArray(arr, 0, arr.length,
                bmpFactoryOptions);
        //Bitmap bmp = BitmapFactory.decodeFile(path, bmpFactoryOptions);
        float heightRatio = (float) Math.ceil(bmpFactoryOptions.outHeight
                / mHeight);
        float widthRatio = (float) (Math.ceil(bmpFactoryOptions.outWidth
                / mWidth));

        // 判断是否要进行缩放
        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                // 高度变化大,按高度缩放
                bmpFactoryOptions.inSampleSize = (int) heightRatio;
            } else {
                // 宽度变化大,按宽度缩放
                bmpFactoryOptions.inSampleSize = (int) widthRatio;
            }
        }
        bmpFactoryOptions.inJustDecodeBounds = false;
        bmp = zoomBitmap(BitmapFactory.decodeByteArray(arr, 0, arr.length,
                bmpFactoryOptions), mWidth, mHeight);
        return bmp;
    }


    /**
     * 按等比例获取图片
     *
     * @param mWidth
     * @param mHeight
     * @param bytes
     * @return
     */
    public static Bitmap getSizedBitmap(int mWidth, int mHeight, byte[] bytes) {
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                bmpFactoryOptions);
        float heightRatio = (float) Math.ceil(bmpFactoryOptions.outHeight
                / mHeight);
        float widthRatio = (float) (Math.ceil(bmpFactoryOptions.outWidth
                / mWidth));

        // 判断是否要进行缩放
        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                // 高度变化大,按高度缩放
                bmpFactoryOptions.inSampleSize = (int) heightRatio;
            } else {
                // 宽度变化大,按宽度缩放
                bmpFactoryOptions.inSampleSize = (int) widthRatio;
            }
        }
        bmpFactoryOptions.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                bmpFactoryOptions);
        return bmp;
    }

    /**
     * 取图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Drawable getDrawableById(Context context, int resId) {
        if (context == null) {
            return null;
        }
        return context.getResources().getDrawable(resId);
    }

    /**
     * 取位图
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap getBitmapById(Context context, int resId) {
        if (context == null) {
            return null;
        }
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }

    /**
     * 为Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawable2bitmap(Drawable drawable) {
        if (null == drawable) {
            return null;
        }
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    /**
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidht, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        return newbmp;
    }

    /**
     * 根据长宽比的大小对bitmap进行压缩，filePath 是图片的路径
     */
    public static Bitmap getSizedBitmap1(int width, int height, String filePath) {
        if (null != filePath && !filePath.equals("")) {
            BitmapFactory.Options opts = null;
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(filePath, opts);
                // 计算图片缩放比例
                final int minSideLength = Math.min(width, height);
                opts.inSampleSize = computeSampleSize(opts, minSideLength,
                        width * height);
                opts.inJustDecodeBounds = false;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
                opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
            }
            try {

                return BitmapFactory.decodeFile(filePath, opts);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将图片旋转
     * width  ：压缩的宽
     * height : 压缩的高
     */
    public static Bitmap loadBitmap(int width, int height, String imgpath, boolean equal) {
        Bitmap bm = null;
        if (equal) {
            bm = ImageUtil.getSizedBitmap(width, height, imgpath);
        } else {
            bm = ImageUtil.getSizedBitmap1(width, height, imgpath);
        }

        int digree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imgpath);
        } catch (IOException e) {
            e.printStackTrace();
            exif = null;
        }
        if (exif != null) {
            // 读取图片中相机方向信息
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            // 计算旋转角度
            switch (ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    digree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    digree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    digree = 270;
                    break;
                default:
                    digree = 0;
                    break;
            }
        }
        //System.out.println("digree->"+digree);
        if (digree != 0) {
            // 旋转图片
            Matrix m = new Matrix();
            m.postRotate(digree);
            bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(),
                    m, true);
        }
        return bm;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    // 将Bitmap转换成InputStream
    public static InputStream Bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.JPEG, 100, baos); // 压缩处理 100表示不压缩
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    /**
     * @param bmp
     * @param needRecycle
     * @return
     */
    public static byte[] Bitmap2Bytes(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @param bitmap
     * @return
     */
    public static String imgToBase64(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, 100, out);

            out.flush();
            out.close();

            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            // TODO Auto-generated catch block  
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();
            }
        }
    }

    /**
     * @param base64Data
     * @param imgName
     * @param imgFormat  图片格式
     */
    public static void base64ToBitmap(String base64Data, String imgName, String imgFormat) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        File myCaptureFile = new File(FileUtil.getSDPath(), imgName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myCaptureFile);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        }
        boolean isTu = bitmap.compress(CompressFormat.JPEG, 100, fos);
        if (isTu) {
            // fos.notifyAll();  
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();
            }
        } else {
            try {
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();
            }
        }
    }

    /**
     *从视频url中获取第一正Bitmap
     * @param videoUrl
     * @param width 图片宽
     * @param height 图片高
     */
    public static Bitmap getFirstBitmapFromVideoUrl(String videoUrl, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(videoUrl, new HashMap<String, String>());
            } else {
                retriever.setDataSource(videoUrl);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (width > 0 && height > 0) {
            if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
                bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                        ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
            }
        }

        return bitmap;
    }

    /**
     *从视频url中获取第一正Bitmap
     * @param videoUrl

     */
    public static Bitmap getFirstBitmapFromVideoUrl(String videoUrl) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoUrl, MediaStore.Video.Thumbnails.MICRO_KIND);
        return bitmap;
    }

    public static Bitmap getVideoThumbnail(String videoPath, int width, int height) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Video.Thumbnails.MICRO_KIND);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }


    /**
     *从视频url中获取第一正Bitmap
     * @param videoPath

     */
    public static Bitmap getFirstBitmapFromVideoPath(String videoPath) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        Bitmap bitmap = media.getFrameAtTime();
        return bitmap;
    }

    /**
     * 获取媒体文件时长(时间String  hh:mm:ss)
     * */
    public static String getMediaFileTime(String mediaPath) {
        String duration = "";
        MediaPlayer player = new MediaPlayer();
        try {
            player.setDataSource(mediaPath);  //recordingFilePath（）为音频文件的路径
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        double durationTime= player.getDuration();//获取音频的时间
        duration = TimeUtil.secToTime((int)durationTime/1000);
        Log.d("ACETEST", "### duration: " + duration);
        player.release();//记得释放资源
        return duration;
    }

    /**
     * 获取媒体文件时长(时间String  hh:mm:ss)
     * */
    public static String getMediaFileTime(Context context, String mediaPath) {
        String duration = "";
        Uri uri = Uri.fromFile(new File(mediaPath));
        String[] videoCursorCols = new String[] { MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.TITLE, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.ARTIST, MediaStore.Video.Media.ALBUM, MediaStore.Video.Media.RESOLUTION, MediaStore.Video.Media.MIME_TYPE, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DATA };

        Cursor cursor = context.getContentResolver().query(uri, videoCursorCols, null, null, null);
        L.show("cursor==null" + (cursor == null));
        double durationTime= 0l;
        if (cursor != null) {
            cursor.moveToFirst();
            durationTime = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));//获取音频的时间
            durationTime = cursor.getLong(3);
        }

        duration = TimeUtil.secToTime((int)durationTime/1000);
        Log.d("ACETEST", "### duration: " + duration);
        return duration;
    }

    /**
     * 获取媒体文件时长(秒数)
     * */
    public static long getMediaFileTimeSecond(String mediaPath) {
        String duration = "";
//        MediaMetadataRetriever media = new MediaMetadataRetriever();
//        media.setDataSource(mediaPath);
//        duration = media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        MediaPlayer player = new MediaPlayer();
        try {
            player.setDataSource(mediaPath);  //recordingFilePath（）为音频文件的路径
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        double durationTime= player.getDuration();//获取音频的时间
//        duration = TimeUtil.secToTime((int)durationTime/1000);
//        Log.d("ACETEST", "### duration: " + duration);
        player.release();//记得释放资源
        return (long)durationTime;
    }

    public static int getImageDigree(String imgpath) {
        int digree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imgpath);
        } catch (IOException e) {
            e.printStackTrace();
            exif = null;
        }
        if (exif != null) {
            // 读取图片中相机方向信息
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            // 计算旋转角度
            switch (ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    digree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    digree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    digree = 270;
                    break;
                default:
                    digree = 0;
                    break;
            }
        }
        return digree;
    }
}
