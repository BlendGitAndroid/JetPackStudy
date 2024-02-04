package com.blend.jetpackstudy.hit

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OkHttpClientStandard

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OtherOkHttpClient