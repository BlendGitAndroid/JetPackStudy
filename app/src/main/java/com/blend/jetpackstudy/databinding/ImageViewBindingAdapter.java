package com.blend.jetpackstudy.databinding;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.blend.jetpackstudy.MyApplication;
import com.bumptech.glide.Glide;

public class ImageViewBindingAdapter {

    @BindingAdapter("image")
    public static void setImage(ImageView imageView, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(MyApplication.getConText()).load(imageUrl).into(imageView);
        } else {
            imageView.setBackgroundColor(Color.RED);
        }
    }

    @BindingAdapter("image")
    public static void setImage(ImageView imageView, int imageResource) {
        imageView.setImageResource(imageResource);
    }

    @BindingAdapter(value = {"image2", "defaultImageResource"}, requireAll = false)
    public static void setImage(ImageView imageView, String imageUrl, int imageResource) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(MyApplication.getConText()).load(imageUrl).into(imageView);
        } else {
            imageView.setImageResource(imageResource);
        }
    }

}
