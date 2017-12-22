package com.stardust.app.base.utils;

import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

/**
 * 计时器
 * Created by bestw on 2017/9/24.
 */

public class TimerCount {

    private long countUp;
    private Chronometer chronometer;
    public TimerCount(Chronometer chronometer) {
        this.chronometer = chronometer;
    }

    public void startTimer() {

        final long startTime = SystemClock.elapsedRealtime();
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer arg0) {
                countUp = (SystemClock.elapsedRealtime() - startTime) / 1000;
//                if (countUp % 2 == 0) {
//                    mBinding.chronoRecordingImage.setVisibility(View.VISIBLE);
//                } else {
//                    mBinding.chronoRecordingImage.setVisibility(View.INVISIBLE);
//                }
                String asText = String.format("%02d", countUp / 60) + ":" + String.format("%02d", countUp % 60);
                chronometer.setText(asText);
            }
        });
        chronometer.start();
    }

    public void stopTimer() {
        chronometer.stop();
    }
}
