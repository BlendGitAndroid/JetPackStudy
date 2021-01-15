package com.blend.jetpackstudy.databinding;

import androidx.databinding.ObservableField;

// public class TwoWayBindingViewModel2 {
//
//     private ObservableField<LoginModel> mLoginModelObservableField;
//
//     public TwoWayBindingViewModel2() {
//         LoginModel loginModel = new LoginModel();
//         loginModel.userName = "Blend";
//         mLoginModelObservableField = new ObservableField<>();
//         mLoginModelObservableField.set(loginModel);
//     }
//
//     public String getUserName() {
//         return mLoginModelObservableField.get().userName;
//     }
//
//     public void setUserName(String userName) {
//         mLoginModelObservableField.get().userName = userName;
//         mLoginModelObservableField.set(mLoginModelObservableField.get());
//         mLoginModelObservableField.notifyChange();
//     }
// }

public class TwoWayBindingViewModel2 {

    private ObservableField<String> mLoginModelObservableField;

    public TwoWayBindingViewModel2() {
        mLoginModelObservableField = new ObservableField<>();
        mLoginModelObservableField.set("Blend");
    }

    public ObservableField<String> getUserName() {
        return mLoginModelObservableField;
    }

    public void setUserName(String userName) {
        mLoginModelObservableField.set(userName);
    }
}