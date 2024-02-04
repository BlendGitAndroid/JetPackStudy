package com.blend.jetpackstudy.hit

import android.util.Log
import javax.inject.Inject


class UserManager @Inject constructor() {
    val TAG = "UserManager"
    fun getUserToken() {
        Log.d(TAG, "获取用户token")
    }
}