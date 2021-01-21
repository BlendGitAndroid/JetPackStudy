package com.blend.jetpackstudy.databinding;

import androidx.lifecycle.MutableLiveData;

public class TwoWayBindingViewModel3 {

    private MutableLiveData<LoginModel> mLiveData = new MutableLiveData<>();

    public TwoWayBindingViewModel3() {
        LoginModel loginModel = new LoginModel();
        loginModel.userName = "blend";
        mLiveData.setValue(loginModel);
    }


    public MutableLiveData<LoginModel> getLiveData() {
        return mLiveData;
    }
}
