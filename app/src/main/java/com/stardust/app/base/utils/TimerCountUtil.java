package com.stardust.app.base.utils;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

/**
 * Created by bestw on 2017/8/22.
 */

public class TimerCountUtil extends CountDownTimer {
    private TextView textView;// 按钮

    // 在这个构造方法里需要传入三个参数，一个是Activity，一个是总的时间millisInFuture，一个是countDownInterval，然后就是你在哪个按钮上做这个是，就把这个按钮传过来就可以了
    public TimerCountUtil(long millisInFuture,
                         long countDownInterval, View view, TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        textView.setEnabled(false);// 设置不能点击
    }

    @SuppressLint("NewApi")
    @Override
    public void onTick(long millisUntilFinished) {

        if (millisUntilFinished / 1000 > -1) {
            textView.setText(millisUntilFinished / 1000 + "秒后重新获取");// 设置倒计时时间
        }

    }

    @SuppressLint("NewApi")
    @Override
    public void onFinish() {
        changState();

    }

    @SuppressLint("NewApi")
    public void changState() {
        textView.setText("重新获取");
        textView.setEnabled(true);// 重新获得点击
    }
}
