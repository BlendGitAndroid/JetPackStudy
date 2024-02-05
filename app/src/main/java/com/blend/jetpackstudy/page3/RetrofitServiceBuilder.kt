package com.blend.jetpackstudy.page3

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitServiceBuilder {
    fun <T> createService(
        clazz: Class<T>,
        baseApi: String = BaseApi.BASE_URL
    ): T? {
        //添加日志拦截器
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                HttpLoggingInterceptor.Logger.DEFAULT.log(message)
            }
        })
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val builder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseApi)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(clazz)
    }
}