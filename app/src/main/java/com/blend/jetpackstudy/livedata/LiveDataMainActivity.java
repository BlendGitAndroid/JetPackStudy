package com.blend.jetpackstudy.livedata;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;

import com.blend.jetpackstudy.R;

/**
 * LiveData是一个可被观察的数据容器类。具体说来，可以将LiveData理解为一个数据的容器，它将数据包装起来，使数据成为被观察者，
 * 当该数据发生变化时，观察者能够获得通知。我们不需要自己去实现观察者模式，LiveData内部已经默认实现好了，只要使用就可以了。
 * <p>
 * LiveData和ViewModel的关系？
 * ViewModel用于存放页面所需要的各种数据，不仅如此，我们还可以在其中放一些与数据相关的业务逻辑。例如，我们可以在ViewModel
 * 中进行数据的加工、获取等操作。因此，ViewModel中的数据可能会随着业务的变化而变化。对页面来说，它并不关心ViewModel中的业
 * 务逻辑，它只关心需要展示的数据是什么，并且希望在数据发生变化时，能及时得到通知并做出更新。LiveData的作用就是，在ViewModel
 * 中的数据发生变化时通知页面。因此，LiveData通常被放在ViewModel中使用，用于包装ViewModel中那些需要被外界观察的数据。我们
 * 从LiveData（实时数据）这个名字，也能大概推测出它的特性与作用。
 * <p>
 * ViewModel+LiveData实现Fragment间通信。
 * Fragment可以被看作Activity的子页面，即一个Activity中可以包含多个Fragment。这些Fragment彼此独立，但是又都属于同一个Activity。
 * 基于ViewModel和Fragment组件的这些特性，我们可以巧妙地利用LiveData，实现同一个Activity中的不同Fragment间的通信。
 */
public class LiveDataMainActivity extends AppCompatActivity {

    private static final String LIVE_DATA_TEST = "live_data_test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data_main);
        //获取到ViewModel
        TimerWithLiveDataViewModel timerWithLiveDataViewModel = new ViewModelProvider(this).get(TimerWithLiveDataViewModel.class);
        //得到ViewModel中的LiveData
        final MutableLiveData<Integer> liveData = timerWithLiveDataViewModel.getCurrentSecond();
        /**
         * 从源码可以看出，observe()方法接收的第1个参数是一个LifecycleOwner对象，在本例中为Activity。第2个参数是一个Observer对象。方法中的最后
         * 一行代码将Observer与Activity的生命周期关联在一起。因此，LiveData能够感知页面的生命周期。它可以检测页面当前的状态是否为激活状态，或者页
         * 面是否被销毁。只有在页面处于激活状态（Lifecycle.State.ON_STARTED或Lifecycle.State.ON_RESUME）时，页面才能收到来自LiveData的通知，
         * 若页面被销毁（Lifecycle.State.ON_DESTROY），那么LiveData会自动清除与页面的关联，从而避免可能引发的内存泄漏问题。
         */
        liveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                ((TextView) findViewById(R.id.timeLiveDataTv)).setText("Time：" + integer);
            }
        });
        findViewById(R.id.timeLiveDataBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveData.setValue(0);
            }
        });
        // timerWithLiveDataViewModel.startTiming();

        findViewById(R.id.liveDataBusBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveDataBusX.getInstance().with(LIVE_DATA_TEST, String.class).setValue("Blend Test");
            }
        });
        LiveDataBusX.getInstance().with(LIVE_DATA_TEST, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(LiveDataMainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

        //Transformations.map的原理是利用MediatorLiveData，将X类型的LiveData经过转换后变成了Y类型的LiveData，
        //这个更加注重的是值的变化，根据旧的LiveData的值生成新的LiveData的值
        LiveData<String> liveDataMap = Transformations.map(liveData, new Function<Integer, String>() {
            @Override
            public String apply(Integer input) {
                return input + "ss";
            }
        });

        //Transformations.switchMap的原理也是利用MediatorLiveData，将X类型的LiveData转换成Y类型的LiveData，
        //但是这个的返回对象是一个LiveData，这个数据更加注重的是触发，用旧的LiveData来生成新的LiveData
        LiveData<String> liveDataSwitchMap = Transformations.switchMap(liveData, new Function<Integer, LiveData<String>>() {
            @Override
            public LiveData<String> apply(Integer input) {
                return new MutableLiveData<String>("ss");
            }
        });

        //MediatorLiveData，用于监测当LiveData数据改变的时候，有可能这个LiveData在一个异步回调中，那么需要MediatorLiveData
        //来检测监测这个异步回调，这个注重的是值的回调
        MediatorLiveData<String> mediatorLiveData = new MediatorLiveData<String>();
        mediatorLiveData.addSource(liveData, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

            }
        });
    }
}