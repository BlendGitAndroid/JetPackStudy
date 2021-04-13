package com.blend.jetpackstudy.lifecycle;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

//通过注解的形式，进行生命周期的添加
public class MyLocationListener implements LifecycleObserver {

    private static final String TAG = "MyLocationListener";

    public MyLocationListener(Activity activity, OnLocationChangeListener onLocationChangeListener) {
        Log.e(TAG, "MyLocationListener: ");
    }

    //可以接受一个参数LifecycleOwner
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void startGetLocation(LifecycleOwner owner) {
        Log.e(TAG, "startGetLocation: ");
        //拥有者的生命周期至少是started的时候，才开始执行下面的代码
        if (owner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {

        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void stopGetLocation() {
        Log.e(TAG, "stopGetLocation: ");
    }

    //ANY注解，可以接受两个参数，第二个参数用于区分是什么事件
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    private void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }


    public interface OnLocationChangeListener {
        void onChanged(double latitude, double longitude);
    }

}
