package com.blend.jetpackstudy.page3

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.blend.jetpackstudy.databinding.ActivityPage3Binding
import com.blend.jetpackstudy.viewbinding.BaseActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Paging3组件的使用流程比较固定，它主要是在Respository中调用PagingSource，在ViewModel中调用Respository并返
 * 回Flow<PagingData>到UI层，最后UI层接收数据并进行展示
 */
class Page3MainActivity : BaseActivity<ActivityPage3Binding>() {

    companion object {
        private const val TAG = "Page3MainActivity"
    }

    private lateinit var testViewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    private fun initData() {
        testViewModel = ViewModelProvider(this).get(TestViewModel::class.java)

        val testPagingDataAdapter = TestPagingDataAdapter()

        mViewBinding.rv.layoutManager = LinearLayoutManager(this)

        // 底部失败重试按钮
        mViewBinding.rv.adapter = testPagingDataAdapter.withLoadStateFooter(
            footer = LoadStateFootAdapter(
                retry = {
                    testPagingDataAdapter.retry()
                }
            )
        )

        // 由于 collect 是一个挂起函数，所以这里需要在协程中操作。
        // 接收到数据后，调用 PagingAdapter 的 submitData 方法，Paging3 开始工作，他会将数据显示在页面上。
        lifecycleScope.launch {
            try {
                testViewModel.loadStudentMessage().collect {
                    testPagingDataAdapter.submitData(it)
                }
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }
        }

        // 通过PagingAdapter的addLoadStateListener方法进行监听
        testPagingDataAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> {
                    Log.d(TAG, "正在加载")
                }

                is LoadState.Error -> {
                    Log.d(TAG, "加载错误")
                }

                is LoadState.NotLoading -> {
                    Log.d(TAG, "未加载，无错误")
                }
            }
        }
    }

}