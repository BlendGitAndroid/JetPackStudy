package com.blend.jetpackstudy.databinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.blend.jetpackstudy.BR;

/**
 * 在构造器中为字段userName设置了默认值，接着为该字段写了Getter和Setter方法。请注意，这里在Getter方法前加上了＠Bindable标签，
 * 这是在告诉编译器，希望对这个字段进行双向绑定。而Setter方法会在用户编辑EditText中的内容时被自动调用，需要在该方法内对userName
 * 字段进行手动更新.
 * <p>
 * 注意，在对字段更新之前，需要判断新值与旧值是否不同。因为在更新后，会调用notifyPropertyChanged()方法通知观察者，数据已经更新。
 * 观察者在收到通知后，会对Setter方法进行调用。因此，如果没有对值进行判断，那么则会引发循环调用的问题。
 */
public class TwoWayBindingViewModel extends BaseObservable {

    private LoginModel mLoginModel;

    public TwoWayBindingViewModel() {
        mLoginModel = new LoginModel();
        mLoginModel.userName = "Blend";
    }

    @Bindable
    public String getUserName() {
        return mLoginModel.userName;
    }

    public void setUserName(String userName) {
        if (userName != null && !userName.equals(mLoginModel.userName)) {
            mLoginModel.userName = userName;
            //可以处理与一些业务相关的逻辑，例如保存userName字段
            notifyPropertyChanged(BR.userName);
        }
    }
}
