package com.stardust.app.base.http;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.stardust.app.base.utils.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;


/**
 * http://blog.csdn.net/lmj623565791/article/details/47911083
 * okHttp请求工具类
 * OKHttp 2.7.5
 */
public class OkHttpClientManager
{
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Gson mGson;


    private static final String TAG = "OkHttpClientManager";

    private OkHttpClientManager()
    {
        mOkHttpClient = new OkHttpClient();
        //cookie enabled

        //mOkHttpClient.networkInterceptors().add(new StethoInterceptor());
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mDelivery = new Handler(Looper.getMainLooper());
        mGson = new Gson();

        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
//            mOkHttpClient.setSocketFactory(  sslContext.getSocketFactory());
            mOkHttpClient.setSslSocketFactory(sslContext.getSocketFactory());
            mOkHttpClient.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    L.show("hostname:" + hostname);
                    return true;
                }
            });

        } catch (NoSuchAlgorithmException e) {
            L.show("NoSuchAlgorithmException:" + e.getMessage());
            e.printStackTrace();
        } catch (KeyManagementException e) {
            L.show("KeyManagementException:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static OkHttpClientManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (OkHttpClientManager.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 同步的Get请求
     *
     * @param url
     * @return Response
     */
    private Response _getAsyn(String url) throws IOException
    {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        return execute;
    }

    /**
     * 同步的Get请求
     *
     * @param url
     * @return 字符串
     */
    private String _getAsString(String url) throws IOException
    {
        Response execute = _getAsyn(url);
        return execute.body().string();
    }


    /**
     * 异步的get请求
     *
     * @param url
     * @param callback
     */
    private void _getAsyn(String url, final ResultCallback callback)
    {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        deliveryResult(callback, request);
    }


    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return
     */
    private Response _post(String url, Param... params) throws IOException
    {
        Request request = buildPostRequest(url, params);
        Response response = mOkHttpClient.newCall(request).execute();
        return response;
    }


    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return 字符串
     */
    private String _postAsString(String url, Param... params) throws IOException
    {
        Response response = _post(url, params);
        return response.body().string();
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsyn(String url, final ResultCallback callback, Param... params)
    {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsyn(String url, final ResultCallback callback, Map<String, String> params)
    {
        Param[] paramsArr = map2Params(params);
        Request request = buildPostRequest(url, paramsArr);
        deliveryResult(callback, request);
    }

    /**
     * 同步基于post的文件上传
     *
     * @param params
     * @return
     */
    private Response _post(String url, File[] files, String[] fileKeys, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        return mOkHttpClient.newCall(request).execute();
    }

    private Response _post(String url, File file, String fileKey) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        return mOkHttpClient.newCall(request).execute();
    }

    private Response _post(String url, File file, String fileKey, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 异步基于post的文件上传
     *
     * @param url
     * @param callback
     * @param files
     * @param fileKeys
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File[] files, String[] fileKeys, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传 带进度显示
     *
     * @param url
     * @param callback
     * @param files
     * @param fileKeys
     * @throws IOException
     */
    private void _postAsynProgress(String url, ResultCallback callback, File[] files, String[] fileKeys, FileProgressRequestBody.ProgressListener listener, Param... params) throws IOException
    {
        Request request = buildMultipartFormProgressRequest(url, files, fileKeys, listener, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件不带参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File file, String fileKey) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件不带参数  带进度  上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @throws IOException
     */
    private void _postAsynProgress(String url, ResultCallback callback, File file, String fileKey, FileProgressRequestBody.ProgressListener listene) throws IOException
    {
        Request request = buildMultipartFormProgressRequest(url, new File[]{file}, new String[]{fileKey}, listene, null);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件且携带其他form参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @param params
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File file, String fileKey, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件且携带其他form参数上传  带进度
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @param params
     * @throws IOException
     */
    private void _postAsynProgress(String url, ResultCallback callback, File file, String fileKey, FileProgressRequestBody.ProgressListener listene, Param... params) throws IOException
    {
        Request request = buildMultipartFormProgressRequest(url, new File[]{file}, new String[]{fileKey}, listene, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步下载文件
     *
     * @param url
     * @param destFileDir 本地文件存储的文件夹
     * @param callback
     */
    private void _downloadAsyn(final String url, final String destFileDir, final ResultCallback callback)
    {
        getInstance()._downloadAsyn(url, destFileDir, callback, null);
    }

    /**
     * 异步下载文件
     *
     * @param url
     * @param destFileDir 本地文件存储的文件夹
     * @param callback
     */
    private void _downloadAsyn(final String url, final String destFileDir, final ResultCallback callback, final ProgressInterface progressInterface)
    {
        getInstance()._downloadAsyn(url, destFileDir, getFileName(url), callback, progressInterface);
//        final Request request = new Request.Builder()
//                .url(url)
//                .build();
//        final Call call = mOkHttpClient.newCall(request);
//        call.enqueue(new Callback()
//        {
//            @Override
//            public void onFailure(final Request request, final IOException e)
//            {
//                sendFailedStringCallback(request, e, callback);
//            }
//
//            @Override
//            public void onResponse(Response response)
//            {
//                InputStream is = null;
//                byte[] buf = new byte[2048];
//                int len = 0;
//                FileOutputStream fos = null;
//                try
//                {
//                    is = response.body().byteStream();
//                    File file = new File(destFileDir, getFileName(url));
//                    fos = new FileOutputStream(file);
//                    long total = response.body().contentLength();
//                    L.show("total:" +total);
//                    long curSize = 0;
//                    while ((len = is.read(buf)) != -1)
//                    {
//                        fos.write(buf, 0, len);
//                        curSize+= len;
//                        if (progressInterface != null) {
//                            progressInterface.progress(curSize, total);
//                        }
//
//                    }
//                    fos.flush();
//                    //如果下载文件成功，第一个参数为文件的绝对路径
//                    sendSuccessResultCallback(file.getAbsolutePath(), callback);
//                } catch (IOException e)
//                {
//                    sendFailedStringCallback(response.request(), e, callback);
//                } finally
//                {
//                    try
//                    {
//                        if (is != null) is.close();
//                    } catch (IOException e)
//                    {
//                    }
//                    try
//                    {
//                        if (fos != null) fos.close();
//                    } catch (IOException e)
//                    {
//                    }
//                }
//
//            }
//        });
    }

    /**
     * 异步下载文件
     *
     * @param url
     * @param destFileDir 本地文件存储的文件夹
     * @param callback
     */
    private void _downloadAsyn(final String url, final String destFileDir, final String saveFileName, final ResultCallback callback, final ProgressInterface progressInterface)
    {
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept-Encoding", "identity")
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(final Request request, final IOException e)
            {
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(Response response)
            {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try
                {
                    is = response.body().byteStream();
                    File file = new File(destFileDir, saveFileName);
                    fos = new FileOutputStream(file);
                    long total = response.body().contentLength();
                    L.show("total:" +total);
                    long curSize = 0;
                    while ((len = is.read(buf)) != -1)
                    {
                        fos.write(buf, 0, len);
                        curSize+= len;
                        if (progressInterface != null) {
                            progressInterface.progress(curSize, total, call);
                        }

                    }
                    fos.flush();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    sendSuccessResultCallback(file.getAbsolutePath(), callback);
                } catch (IOException e)
                {
                    sendFailedStringCallback(response.request(), e, callback);
                } finally
                {
                    try
                    {
                        if (is != null) is.close();
                    } catch (IOException e)
                    {
                    }
                    try
                    {
                        if (fos != null) fos.close();
                    } catch (IOException e)
                    {
                    }
                }

            }
        });
    }

    /**
     * 获取下载长度 同步方法
     *
     * @param downloadUrl
     * @return
     */
    public long getContentLength(String downloadUrl) {
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                return contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取下载长度 异步方法
     *
     * @param downloadUrl
     * @return
     */
    public void getContentLengthAsyn(String downloadUrl, final ResultCallback callback) {
        final Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(final Request request, final IOException e)
            {
            }

            @Override
            public void onResponse(Response response)
            {
                try {

                    if (response != null && response.isSuccessful()) {
                        long contentLength = response.body().contentLength();
                        if (callback != null) {
                            callback.onResponse("" + contentLength);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private String getFileName(String path)
    {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    private void setErrorResId(final ImageView view, final int errorResId)
    {
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                view.setImageResource(errorResId);
            }
        });
    }


    //*************对外公布的方法************


    public static Response getAsyn(String url) throws IOException
    {
        return getInstance()._getAsyn(url);
    }


    public static String getAsString(String url) throws IOException
    {
        return getInstance()._getAsString(url);
    }

    public static void getAsyn(String url, ResultCallback callback)
    {
        getInstance()._getAsyn(url, callback);
    }

    public static Response post(String url, Param... params) throws IOException
    {
        return getInstance()._post(url, params);
    }

    public static String postAsString(String url, Param... params) throws IOException
    {
        return getInstance()._postAsString(url, params);
    }

    public static void postAsyn(String url, final ResultCallback callback, Param... params)
    {
        getInstance()._postAsyn(url, callback, params);
    }


    public static void postAsyn(String url, final ResultCallback callback, Map<String, String> params)
    {
        getInstance()._postAsyn(url, callback, params);
    }


    public static Response post(String url, File[] files, String[] fileKeys, Param... params) throws IOException
    {
        return getInstance()._post(url, files, fileKeys, params);
    }

    public static Response post(String url, File file, String fileKey) throws IOException
    {
        return getInstance()._post(url, file, fileKey);
    }

    public static Response post(String url, File file, String fileKey, Param... params) throws IOException
    {
        return getInstance()._post(url, file, fileKey, params);
    }

    public static void postAsyn(String url, ResultCallback callback, File[] files, String[] fileKeys, Param... params) throws IOException
    {
        getInstance()._postAsyn(url, callback, files, fileKeys, params);
    }


    public static void postAsyn(String url, ResultCallback callback, File file, String fileKey) throws IOException
    {
        getInstance()._postAsyn(url, callback, file, fileKey);
    }


    public static void postAsyn(String url, ResultCallback callback, File file, String fileKey, Param... params) throws IOException
    {
        getInstance()._postAsyn(url, callback, file, fileKey, params);
    }


    public static void uploadProgress(String url, ResultCallback callback, File[] files, String[] fileKeys, FileProgressRequestBody.ProgressListener listener, Param... params) throws IOException
    {
        getInstance()._postAsynProgress(url, callback, files, fileKeys, listener, params);
    }


    public static void uploadProgress(String url, ResultCallback callback, File file, String fileKey, FileProgressRequestBody.ProgressListener listener) throws IOException
    {
        getInstance()._postAsynProgress(url, callback, file, fileKey, listener);
    }


    public static void uploadProgress(String url, ResultCallback callback, File file, String fileKey, FileProgressRequestBody.ProgressListener listener, Param... params) throws IOException
    {
        getInstance()._postAsynProgress(url, callback, file, fileKey, listener, params);
    }

    public static void downloadAsyn(String url, String destDir, ResultCallback callback)
    {
        getInstance()._downloadAsyn(url, destDir, callback);
    }

    public static void downloadAsyn(String url, String destDir, String saveFileName, ResultCallback callback)
    {
        getInstance()._downloadAsyn(url, destDir, saveFileName, callback, null);
    }

    public static void downloadAsyn(String url, String destDir, ResultCallback callback, ProgressInterface progressInterface)
    {
        getInstance()._downloadAsyn(url, destDir, callback, progressInterface);
    }

    public static void downloadAsyn(String url, String destDir, String saveFileName, ResultCallback callback, ProgressInterface progressInterface)
    {
        getInstance()._downloadAsyn(url, destDir, saveFileName, callback, progressInterface);
    }
    //****************************


    /**
     * 文件上传请求（没有进度）
     * **/
    private Request buildMultipartFormRequest(String url, File[] files,
                                              String[] fileKeys, Param[] params)
    {
        params = validateParam(params);

        MultipartBuilder builder = new MultipartBuilder()
                .type(MultipartBuilder.FORM);

        for (Param param : params)
        {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                    RequestBody.create(null, param.value));
        }
        if (files != null)
        {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++)
            {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }


    /**
     * 文件上传请求（有进度反馈）
     * **/
    private Request buildMultipartFormProgressRequest(String url, File[] files,
                                              String[] fileKeys, FileProgressRequestBody.ProgressListener listener, Param[] params)
    {
        params = validateParam(params);

        MultipartBuilder builder = new MultipartBuilder()
                .type(MultipartBuilder.FORM);

        for (Param param : params)
        {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                    RequestBody.create(null, param.value));
        }
        if (files != null)
        {
            FileProgressRequestBody fileBody = null;
            for (int i = 0; i < files.length; i++)
            {
                File file = files[i];
                String fileName = file.getName();
//                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                fileBody = new FileProgressRequestBody(file,"application/octet-stream",listener);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    private String guessMimeType(String path)
    {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null)
        {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    private Param[] validateParam(Param[] params)
    {
        if (params == null)
            return new Param[0];
        else return params;
    }

    private Param[] map2Params(Map<String, String> params)
    {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries)
        {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }

    private static final String SESSION_KEY = "Set-Cookie";
    private static final String mSessionKey = "JSESSIONID";

    private Map<String, String> mSessions = new HashMap<String, String>();

    private void deliveryResult(final ResultCallback callback, Request request)
    {
        mOkHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(final Request request, final IOException e)
            {
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(final Response response)
            {
                try
                {
                    final String string = response.body().string();
                    JSONObject object = new JSONObject(string);
                    if (callback.mType == String.class)
                    {
                        sendSuccessResultCallback(string, callback);
                    } else
                    {
                        Object o = mGson.fromJson(string, callback.mType);
                        sendSuccessResultCallback(o, callback);
                    }


                } catch (IOException e)
                {
                    sendFailedStringCallback(response.request(), e, callback);
                } catch (com.google.gson.JsonParseException e)//Json解析的错误
                {
                    sendFailedStringCallback(response.request(), e, callback);
                } catch (JSONException e) {
                    sendFailedStringCallback(response.request(), e, callback);
                }

            }
        });
    }

    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback)
    {
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                if (callback != null)
                    callback.onError(request, e);
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final ResultCallback callback)
    {
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                if (callback != null)
                {
                    callback.onResponse(object);
                }
            }
        });
    }

    private Request buildPostRequest(String url, Param[] params)
    {
        if (params == null)
        {
            params = new Param[0];
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params)
        {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }


    public static abstract class ResultCallback<T>
    {
        Type mType;

        public ResultCallback()
        {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass)
        {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class)
            {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);
    }

    public static class Param
    {
        public Param()
        {
        }

        public Param(String key, String value)
        {
            this.key = key;
            this.value = value;
        }

        String key;
        String value;
    }


}
