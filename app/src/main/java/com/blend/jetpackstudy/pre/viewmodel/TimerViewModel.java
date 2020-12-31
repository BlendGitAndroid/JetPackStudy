package com.blend.jetpackstudy.pre.viewmodel;


import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * ViewModel是一个抽象类，其中只有一个onCleared()方法。当ViewModel不再被需要，即与之相关的Activity都被销毁时，该方
 * 法会被系统调用。可以在该方法中执行一些资源释放的相关操作。注意，由于屏幕旋转而导致的Activity重建，并不会调用该方法。
 */

public class TimerViewModel extends ViewModel {

    private static final String TAG = "TimerViewModel";

    private Timer mTimer;
    private int currentSecond;
    private OnTimeChangeListener mChangeListener;

    public void startTiming() {
        if (mTimer == null) {
            currentSecond = 0;
            mTimer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    currentSecond++;
                    if (mChangeListener != null) {
                        mChangeListener.OnTimeChanged(currentSecond);
                    }
                }
            };
            mTimer.schedule(timerTask, 1000, 1000);
        }
    }

    public void setChangeListener(OnTimeChangeListener listener) {
        mChangeListener = listener;
    }

    public interface OnTimeChangeListener {
        void OnTimeChanged(int second);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.e(TAG, "onCleared: ");
        mTimer.cancel();
    }
}
