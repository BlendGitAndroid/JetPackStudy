package com.blend.jetpackstudy.lifecycle;


import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 于LifecycleService是Service的直接子类，所以使用起来与普通Service没有差别。
 *
 * 当Service启动和销毁时，MyServiceObserver里面的两个注解方法会自动调用
 */
public class MyService extends LifecycleService {

    private MyServiceObserver mServiceObserver;

    public MyService() {
        mServiceObserver = new MyServiceObserver();
        getLifecycle().addObserver(mServiceObserver);
    }

}

class MyServiceObserver implements LifecycleObserver {
    private static final String TAG = "MyServiceObserver";

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void start() {
        Log.e(TAG, "start: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void stop() {
        Log.e(TAG, "stop: ");
    }

}
