package com.blend.jetpackstudy.hit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ActivityComponent::class)
class NetWorkUtil {

    @OkHttpClientStandard
    // 使用@Provides注解提供获取方法，方法名getOkHttpClient可以任意取，不影响使用
    @Provides
    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @OtherOkHttpClient
    @Provides
    fun getOtherOkHttpClient(): OkHttpClient {
        var okHttpClient = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()
        return okHttpClient
    }
}