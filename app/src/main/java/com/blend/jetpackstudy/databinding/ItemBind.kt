package com.blend.jetpackstudy.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter(
    value = ["android:imgUrl", "android:gender"],
    requireAll = false
)
fun setUserPhoto(
    iView: ImageView,
    imageUrl: String?,
    gender: Int?
) {
    Glide.with(iView).load(imageUrl)
        .into(iView)
}