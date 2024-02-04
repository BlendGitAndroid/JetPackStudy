package com.blend.jetpackstudy.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("imgUrl")
fun setUserPhoto(
    iView: ImageView,
    imageUrl: String?
) {
    Glide.with(iView).load(imageUrl)
        .into(iView)
}