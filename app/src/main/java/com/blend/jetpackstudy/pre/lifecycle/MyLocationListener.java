package com.blend.jetpackstudy.pre.lifecycle;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

//通过注解的形式，进行生命周期的添加
public class MyLocationListener implements LifecycleObserver {

    private static final String TAG = "MyLocationListener";

    public MyLocationListener(Activity activity, OnLocationChangeListener onLocationChangeListener) {
        Log.e(TAG, "MyLocationListener: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void startGetLocation() {
        Log.e(TAG, "startGetLocation: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void stopGetLocation() {
        Log.e(TAG, "stopGetLocation: ");
    }


    public interface OnLocationChangeListener {
        void onChanged(double latitude, double longitude);
    }

}
