package com.blend.jetpackstudy.hit

import android.os.Bundle
import android.util.Log
import com.blend.jetpackstudy.databinding.ActivityHitBinding
import com.blend.jetpackstudy.viewbinding.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class HitMainActivity : BaseActivity<ActivityHitBinding>() {

    private val TAG = "HitMainActivity"

    // 由Hilt注入的字段（如这里的UserManager）不能为私有类型，否则会在编译阶段产生错误。
    @Inject
    lateinit var userManager: UserManager

    @Inject
    lateinit var mobilePhone: MobilePhone

    @OkHttpClientStandard
    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userManager.getUserToken()

        mobilePhone.dialNumber()

        val request = okhttp3.Request.Builder()
            .url("https://www.baidu.com")
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "onResponse: ${response.body?.string()}")
            }
        })
    }

}