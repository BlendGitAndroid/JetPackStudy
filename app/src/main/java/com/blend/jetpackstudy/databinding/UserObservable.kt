package com.blend.jetpackstudy.databinding;

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.blend.jetpackstudy.BR

class UserObservable : BaseObservable() {
    @get:Bindable
    var userName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.userName)
        }

    @get:Bindable
    var userId: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.userId)
        }

    var userPhoto: String? = null

    var userGender: Int? = null
}
