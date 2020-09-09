package com.blend.jetpackstudy.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.blend.jetpackstudy.R;

/**
 * LifeCycle可以帮助开发者创建可感知生命周期的组件。这样，组件便能够在其内部管理自己的生命周期，从而降低模块间的耦合
 * 度，并降低内存泄漏发生的可能性。LifeCycle不只对Activity/Fragment有用，在Service和Application中也能大显身手。
 * LifeCycle是如何解决这个问题的呢？Jetpack为我们提供了两个类：LifecycleOwner（被观察者）和LifecycleObserver（观察者）。
 * 即通过观察者模式，实现对页面生命周期的监听。
 * Activity已经默认实现了LifecycleOwner接口。LifecycleOwner接口中只有一个getLifecycle（LifecycleObserver observer）
 * 方法，LifecycleOwner正是通过该方法实现观察者模式的。
 *
 * 同样，Fragment也实现了LifecycleOwner接口
 */
public class LifeCycleMainActivity extends AppCompatActivity {

    private MyLocationListener mLocationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle__main);
        mLocationListener = new MyLocationListener(this, new MyLocationListener.OnLocationChangeListener() {
            @Override
            public void onChanged(double latitude, double longitude) {

            }
        });

        //注册观察者
        getLifecycle().addObserver(mLocationListener);
    }
}