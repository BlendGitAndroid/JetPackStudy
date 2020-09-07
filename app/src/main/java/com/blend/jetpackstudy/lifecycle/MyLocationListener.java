package com.blend.jetpackstudy.lifecycle;

import android.app.Activity;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

class MyLocationListener implements LifecycleObserver {

    public MyLocationListener(Activity activity, OnLocationChangeListener onLocationChangeListener) {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void startGetLocation() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void stopGetLocation() {

    }


    public interface OnLocationChangeListener {
        void onChanged(double latitude, double longitude);
    }

}
