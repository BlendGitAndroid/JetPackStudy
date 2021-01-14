package com.blend.jetpackstudy.viewmodel;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.blend.jetpackstudy.R;

/**
 * ViewModel：它是介于View（视图）和Model（数据模型）之间的一个东西。它起到了桥梁的作用，使视图和数据既能够分离开，也能够保持通信。
 * 是专门用于存放应用程序页面所需的数据。ViewModel独立于配置变化。这意味着，屏幕旋转所导致的Activity重建，并不会影响ViewModel的
 * 生命周期。ViewModel并没有和任何UI想绑定，也不会和任何生命周期相绑定。
 * <p>
 * 如果希望在ViewModel中使用Context，该怎么办呢？
 * 可以使用AndroidViewModel类，它继承自ViewModel，并接收Application作为Context。这意味着，它的生命周期和Application是一样的，
 * 那么这就不算是一个内存泄漏了。
 * <p>
 * 对于页面数据的保存与恢复，也许会有这样的疑问，onSaveInstanceState()方法同样可以解决屏幕旋转带来的数据丢失问题，那么是不是没有必要
 * 使用ViewModel了呢？
 * 请注意，onSaveInstanceState()方法只能保存少量的、能支持序列化的数据，而ViewModel没有这个限制。ViewModel能支持页面中所有的数据。
 * 同样需要注意的是，ViewModel不支持数据的持久化，当界面被彻底销毁时，ViewModel及其持有的数据就不存在了，但是onSaveInstanceState()
 * 方法没有这个限制，它可以持久化页面的数据。可见，onSaveInstanceState()方法有其特殊的用途，二者不可混淆。
 */
public class ViewModelMainActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_model_main);
        mTextView = findViewById(R.id.timeTv);

        /**
         * ViewModelProvider接收一个ViewModelStoreOwner对象作为参数。在以上示例代码中该参数是this，指代当前的Activity。
         * 这是因为我们的Activity继承自FragmentActivity，而在androidx依赖包中，FragmentActivity默认实现了ViewModelStoreOwner接口。
         * 从ViewModelStore的源码可以看出，ViewModel实际上是以HashMap<String，ViewModel>的形式被缓存起来了。ViewModel与页面之间没有
         * 直接的关联，它们通过ViewModelProvider进行关联。当页面需要ViewModel时，会向ViewModelProvider索要，ViewModelProvider检查
         * 该ViewModel是否已经存在于缓存中，若存在，则直接返回，若不存在，则实例化一个，并保存到缓存中。因此，Activity由于配置变化导致的销
         * 毁重建并不会影响ViewModel，ViewModel是独立于页面而存在的。也正因为此，我们在使用ViewModel时，需要特别注意，不要向ViewModel中
         * 传入任何类型的Context或带有Context引用的对象，这可能会导致页面无法被销毁，从而引发内存泄漏。
         */
        TimerViewModel viewModel = new ViewModelProvider(this).get(TimerViewModel.class);

        viewModel.setChangeListener(new TimerViewModel.OnTimeChangeListener() {
            @Override
            public void OnTimeChanged(final int second) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText("时间：" + second + "秒");
                    }
                });
            }
        });
        viewModel.startTiming();
    }

}