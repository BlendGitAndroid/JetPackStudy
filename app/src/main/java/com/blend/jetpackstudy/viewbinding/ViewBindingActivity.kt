package com.blend.jetpackstudy.viewbinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blend.jetpackstudy.databinding.ActivityViewBindingBinding

class ViewBindingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityViewBindingBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}