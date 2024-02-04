package com.blend.jetpackstudy.databinding;

import androidx.lifecycle.MutableLiveData

class UserLiveData {

    var userName: MutableLiveData<String> = MutableLiveData()
    var userId: MutableLiveData<String> = MutableLiveData()
    var userPhoto: MutableLiveData<String> = MutableLiveData()
    var userGender: MutableLiveData<Int> = MutableLiveData()

}
