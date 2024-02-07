/*
 *
 *  * Copyright (C)  HuangLinqing, TravelPrevention Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.blend.module_test_agency.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blend.appbase.config.ArouteConfig
import com.blend.appbase.config.ParametersConfig
import com.blend.appbase.config.RequsetCodeConfig.REQUSET_CODE_SELECT_CITY
import com.blend.appbase.config.ResultCodeConfig
import com.blend.appbase.ui.BaseActivity
import com.blend.appbase.utils.DialogLoadingUtils
import com.blend.module_test_agency.R
import com.blend.module_test_agency.databinding.ActivityAgencyMessageBinding
import com.blend.module_test_agency.ui.adapter.AgencyMessageAdapter
import com.blend.module_test_agency.viewmodel.AgencyViewModel


/**
 * @author：HuangLinqing
 * @blog：https://huanglinqing.blog.csdn.net/?t=1
 * @公众号：Android 技术圈
 * @desc：检测机构信息
 */
@Route(path = ArouteConfig.AGENCY_MESSAGE)
class AgencyMessageActivity : BaseActivity<ActivityAgencyMessageBinding>() {

    /**
     * viewModel
     */
    private val agencyViewModel by viewModels<AgencyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    /**
     * 初始化
     */
    private fun init() {
        initView()
    }


    /**
     * 初始化view
     */
    private fun initView() {
        mViewBinding.toolbar.setOnClickListener {
            finish()
        }
        //选择城市监听事件
        mViewBinding.tvCity.setOnClickListener {
            ARouter.getInstance().build(ArouteConfig.CITY_DATA)
                .navigation(this, REQUSET_CODE_SELECT_CITY)
        }
        val layoutManager = LinearLayoutManager(this)
        mViewBinding.rvData.layoutManager = layoutManager
    }


    /**
     * 加载数据
     */
    private fun loadData(cityId: String) {
        DialogLoadingUtils.showLoading(this, getString(R.string.wait_please))
        agencyViewModel.loadTestAgencyMessage(cityId).observe(this) {
            DialogLoadingUtils.cancel()
            it?.let {
                if (it.error_code == 0) {
                    //请求成功
                    it.result?.data?.let { data ->
                        val agencyMessageAdpter = AgencyMessageAdapter(data,
                            callback = { position, data ->
                                //以后可以在这里做扩展 如拨打电话、导航等
                            })
                        mViewBinding.rvData.adapter = agencyMessageAdpter
                    }
                }
            }
        }
    }


    override fun getViewBinding(): ActivityAgencyMessageBinding {
        return ActivityAgencyMessageBinding.inflate(layoutInflater)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUSET_CODE_SELECT_CITY -> {
                when (resultCode) {
                    ResultCodeConfig.RESULE_CODE_SELECT_CITY_SUCCESS -> {
                        val cityId = data?.getStringExtra(ParametersConfig.CITY_ID)
                        val cityName = data?.getStringExtra(ParametersConfig.CITY_NAME)
                        mViewBinding.tvCity.text = cityName
                        cityId?.let {
                            loadData(it)
                        }

                    }
                }
            }
        }
    }
}