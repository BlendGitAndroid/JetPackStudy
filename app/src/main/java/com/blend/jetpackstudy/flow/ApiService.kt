package com.blend.jetpackstudy.flow

import retrofit2.http.GET


interface ApiService {
    @GET("users")
    suspend fun queryData(): List<UserBean>
}