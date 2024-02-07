package com.blend.jetpackstudy.project

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.alibaba.android.arouter.launcher.ARouter
import com.blend.appbase.config.ArouteConfig
import com.blend.appbase.network.BaseApi
import com.blend.appbase.ui.BaseActivity
import com.blend.appbase.utils.ToastUtil
import com.blend.jetpackstudy.R
import com.blend.jetpackstudy.databinding.ActivityProjectMainBinding


class ProjectMainActivity : BaseActivity<ActivityProjectMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initClick()
        checkKey()
    }

    /**
     * 检测key值
     */
    private fun checkKey() {
        if (TextUtils.isEmpty(BaseApi.KEY)) {
            ToastUtil.shortShow(getString(R.string.enter_key_please))
        }
    }

    /**
     * 监听事件
     */
    private fun initClick() {
        //查询核酸检测机构
        mViewBinding.llAgency.setOnClickListener {
            ARouter.getInstance().build(ArouteConfig.AGENCY_MESSAGE)
                //传递String类型的值
                .withString("a", "a")
                //传递布尔类型的值
                .withBoolean("b", false)
                //指定启动模式
                .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .navigation()
        }
        //查询风险等级地区
        mViewBinding.llRiskLevel.setOnClickListener {
            ARouter.getInstance().build(ArouteConfig.RISK_LEVEL)
                .navigation()
        }
        //查询出行政策
        mViewBinding.llTravel.setOnClickListener {
            ARouter.getInstance().build(ArouteConfig.TRAVEL_POLICY)
                .navigation()
        }
    }

    override fun getViewBinding(): ActivityProjectMainBinding {
        return ActivityProjectMainBinding.inflate(
            layoutInflater
        )
    }

}