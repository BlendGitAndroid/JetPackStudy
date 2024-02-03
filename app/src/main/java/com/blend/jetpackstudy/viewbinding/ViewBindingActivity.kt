package com.blend.jetpackstudy.viewbinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blend.jetpackstudy.R
import com.blend.jetpackstudy.databinding.ActivityViewBindingBinding

class ViewBindingActivity : BaseActivity<ActivityViewBindingBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding.text.text = "ViewBinding"
    }
}