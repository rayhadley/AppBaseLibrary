package com.stardust.app.base.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.stardust.app.base.utils.Debug;

/**
 * 适应手机屏幕
 * haohua on 2016/4/16.
 */
public class CustomWebView extends WebView{

    public Activity context;
    private OnPageLoadFinished onPageLoadFinished;

    public CustomWebView(Context context) {
        super(context);
        init(context);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void load(String data, OnPageLoadFinished onPageLoadFinished) {
        this.onPageLoadFinished = onPageLoadFinished;
        this.loadData(getHtmlData(data), "text/html; charset=utf-8", "utf-8");
    }

    public void loadUrl(String url, OnPageLoadFinished onPageLoadFinished) {
        this.onPageLoadFinished = onPageLoadFinished;
        this.loadUrl(url);
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    private void init(Context context) {
        if (this.isInEditMode()) {
            return;
        }
        this.setInitialScale(60);//这里一定要设置，数值可以根据各人的需求而定，我这里设置的是50%的缩放
        this.context = (Activity) context;
        final WebSettings webSettings =   this .getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }

        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);// 这个很关键

        if (Build.VERSION.SDK_INT >=19) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }

        this.addJavascriptInterface(this, "App");

        this.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Debug.show("newProgress:" + newProgress);
                if (newProgress == 100) {
                    Debug.show("(onPageLoadFinished == null):" + (onPageLoadFinished == null));
                    if (onPageLoadFinished != null) {
                        onPageLoadFinished.onLoadingFinish();
                    }
                }
                super.onProgressChanged(view, newProgress);
            }

        });

        //防止 加载url时自动跳转到自带浏览器
        this.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }
        });

    }

    @JavascriptInterface
    public void resize(final WebView webView,  final float height) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(getActivity(), height + "", Toast.LENGTH_LONG).show();
                webView.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
            }
        });
    }

    public interface OnPageLoadFinished {
        void onLoadingFinish();
    }
}
