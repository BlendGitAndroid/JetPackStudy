package com.blend.jetpackstudy.databinding.other;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.MutableLiveData;

import com.blend.jetpackstudy.R;
import com.blend.jetpackstudy.databinding.ActivityNewDatabindingMainBinding;

import java.util.Random;

public class NewDatabindingMainActivity extends AppCompatActivity {

    private ActivityNewDatabindingMainBinding mBinding;
    private Goods goods;
    private ObservableGoods mObservableGoods;
    private ViewModelLiveData mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_databinding_main);

        //其实这个不是单向绑定，这个只是利用了xml中的variable变量，通过数据来对视图进行赋值
        User user = new User("Blend", "123456");
        //可以通过两种方式来进行赋值，其实下面的这种方式在源码中也是使用setVariable
        mBinding.setVariable(BR.userInfo, user);
        // mBinding.setUserInfo(user);

        //单向绑定就是当数据改变的时候，刷新视图
        //单向绑定BaseObservable
        goods = new Goods("code1", "android2", 24);
        mBinding.setGoods(goods);
        mBinding.setGoodsHandler(new GoodsHandler());

        //单向绑定ObservableField
        mObservableGoods = new ObservableGoods("code2", "android2", 25);
        mBinding.setObservableGoods(mObservableGoods);
        mBinding.setObserverGoodsHandler(new ObservableGoodsHandler());

        //双向绑定就是当视图发生变化的时候，也能通知到数据
        //双向绑定比单向绑定多了一个=，@={}，因为双向绑定会调用属性的get方法，所以还要提供属性的get方法
        //可以有三种方式来设置，但是现在使用的是LiveData,系统帮助我们设置了LiveData
        mBinding.setLifecycleOwner(this);
        mViewModel = new ViewModelLiveData();
        mBinding.setViewModelHandle(new LiveDataHandler());
        mBinding.setViewModel(mViewModel);
    }

    public class GoodsHandler {

        public void changeGoodsName() {
            goods.setName("code" + new Random().nextInt(100));
            goods.setPrice(new Random().nextInt(100));
        }

        public void changeGoodsDetails() {
            goods.setDetails("hi" + new Random().nextInt(100));
            goods.setPrice(new Random().nextInt(100));
        }
    }

    public class ObservableGoodsHandler {

        public void changeGoodsName() {
            mObservableGoods.setName("code" + new Random().nextInt(100));
            mObservableGoods.setPrice(new Random().nextInt(100));
        }
    }

    public class LiveDataHandler {
        public void changeLiveData() {
            mViewModel.setLiveDta("blend" + new Random().nextInt(100));
        }
    }

    public static class ViewModelLiveData extends androidx.lifecycle.ViewModel {

        private MutableLiveData<String> mLiveData = new MutableLiveData<>();

        public MutableLiveData<String> getLiveData() {
            return mLiveData;
        }

        public void setLiveDta(String password) {
            mLiveData.setValue(password);
        }

    }
}