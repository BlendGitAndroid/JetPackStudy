package com.blend.jetpackstudy;

import androidx.lifecycle.ProcessLifecycleOwner;

import com.blend.appbase.BaseApplication;
import com.blend.jetpackstudy.lifecycle.ApplicationObserver;

import dagger.hilt.android.HiltAndroidApp;

/**
 * 我们想知道应用程序当前处在前台还是后台，或者当应用程序从后台回到前台时，我们能够得到通知。LifeCycle提供了一个名为
 * ProcessLifecycleOwner的类，以方便我们知道整个应用程序的生命周期情况。使用方式与Activity、Fragment和Service
 * 是类似的，其本质也是观察者模式。由于我们要观察的是整个应用程序，因此，需要在Application中进行相关代码的编写。
 */
@HiltAndroidApp
public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new ApplicationObserver());
    }
}
