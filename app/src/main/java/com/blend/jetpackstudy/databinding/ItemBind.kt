package com.blend.jetpackstudy.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

// 只要是一个静态方法，且第一个参数是View，第二个参数是绑定的值，就可以使用@BindingAdapter注解
@BindingAdapter(
    value = ["android:imgUrl", "android:gender"], requireAll = false
)
fun setUserPhoto(
    iView: ImageView,
    imageUrl: String?,
    gender: Int?
) {
    Glide.with(iView).load(imageUrl).into(iView)
}