package com.blend.jetpackstudy.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.OnLifecycleEvent;

public class MyLifecycleOwner implements LifecycleOwner {

    private LifecycleRegistry mLifecycleRegistry;

    public MyLifecycleOwner() {
        //将LifecycleOwner作为LifecycleRegistry的弱引用
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
    }


    public void onStart() {
        mLifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
        getLifecycle().addObserver(new LifecycleObserver() {

            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            public void onCreate() {

            }
        });

        //其实所有的Observer最后都会被包装成LifecycleEventObserver
        getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {

            }
        });

        //java8之后应该这样使用
        getLifecycle().addObserver(new DefaultLifecycleObserver() {

            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {

            }

            @Override
            public void onStart(@NonNull LifecycleOwner owner) {

            }
        });

    }

    public void onStop() {
        mLifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
    }


    @NonNull
    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }
}
