package com.blend.jetpackstudy.page3

import retrofit2.http.GET
import retrofit2.http.Path


interface TestApi {
    @GET("wenda/list/{pageId}/json")
    suspend fun getData(@Path("pageId") pageId: Int): TestDataBean
}
