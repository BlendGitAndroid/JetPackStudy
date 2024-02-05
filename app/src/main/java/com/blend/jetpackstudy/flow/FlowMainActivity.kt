package com.blend.jetpackstudy.flow

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.blend.jetpackstudy.databinding.ActivityFlowBinding
import com.blend.jetpackstudy.viewbinding.BaseActivity
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 在团队协作开发中，我们可能会给其他开发者提供一些公用的方法，如果提供的方法是一个耗时操作，那么可以加上suspend关键字，
 * 以此来提醒其他开发成员这个方法需要在协程中执行。所以suspend关键字在Kotlin协程中仅仅起到提醒作用。
 */
class FlowMainActivity : BaseActivity<ActivityFlowBinding>() {

    private val TAG = "FlowMainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope1()

        CoroutineScope2()

        withContext1()

        loadDataFromNetWork()

        retrofitTest()


        // viewModelScope或lifecycleScope是与生命周期绑定的，所以当Activity销毁时，会自动取消协程操作，无须开发者进行额外的操作
        lifecycleScope.launch {
            loadData().collect {
                Log.e(TAG, "flow: $it")
            }
        }

        lifecycleScope.launchWhenCreated {
        }
        lifecycleScope.launchWhenResumed {
        }

    }

    // 在协程中通过async或withContext挂起函数可以返回单个数据值，数据流（Flow）以协程为基础构建，可以按顺序发出多个值。
    // 因为Flow是冷流。什么是冷流呢？冷流就是在数据被订阅后，发布者才开始执行发射数据流的代码，并且若有多个订阅者，
    // 那么每一个订阅者与发布者都是一对一的关系，也就是说，每个订阅者都会收到发布者完整的数据。
    private fun loadData() = flow {
        Log.d(TAG, "--进入loadData方法----")
        for (i in 1..5) {
            delay(1000)
            emit(i)
        }
    }.flowOn(Dispatchers.IO).filter {// 如果需要切换线程操作，则要使用flowOn来代替
        it % 2 == 0 // 过滤偶数
    }.map {
        it * it // 对数据进行转换
    }

    private fun retrofitTest() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        val job = Job()
        // 为什么网络请求可以正常执行呢？这是因为Retrofit自动为开发者做了这样一个处理：当接口声明为suspend类型时，
        // Retrofit会自动切换到子线程中执行。因此这里也没有必要指定apiService.queryData()代码运行子线程中。
        CoroutineScope(job).launch(Dispatchers.Main) {
            val result = apiService.queryData()
            mViewBinding.tvFlow.text = Gson().toJson(result)
        }
    }

    // 与async函数不同的是，withContext函数会强制要求传入一个线程参数，参数值类型有Dispatchers.Default、
    // Dispatchers.IO、Dispatchers.Main这三种，Dispatchers.Default常用于计算密集型任务，
    // Dispatchers.IO常用于网络请求、文件读写等操作，Dispatchers.Main则表示程序在主线程中执行，
    // 所以当开启协程的时候，协程作用域中的代码不一定是执行在子线程的，这取决于这个线程参数的值。
    private fun withContext1() {
        val job = Job()
        CoroutineScope(job).launch {
            val result = withContext(Dispatchers.IO) {
                delay(2000)
                "操作成功"
            }
            Log.d(TAG, result)
        }
    }

    private fun loadDataFromNetWork() {
        val job = Job()
        CoroutineScope(job).launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                delay(2000)
                "操作成功"
            }
            Log.e(TAG, "loadDataFromNetWork on UI: $result")
        }
    }

    private fun CoroutineScope2() {
        val job = Job()
        CoroutineScope(job).launch {
            val startTime = System.currentTimeMillis()
            val result = async {
                delay(2000)
                "操作成功"
            }
            val result2 = async {
                delay(1000)
                "获取成功"
            }
            Log.d(TAG, "执行结果:${result.await()}-${result2.await()}")
            val endTime = System.currentTimeMillis()
            Log.d(TAG, "执行时间:" + (endTime - startTime))
        }
    }

    private fun CoroutineScope1() {
        val job = Job()
        CoroutineScope(job).launch {
            // async函数同样可以构建一个协程作用域并返回Deferred对象，但是与Coroutine-Scope函数不同的是，
            // async函数必须在协程作用域中才能调用
            val result = async {
                //模拟耗时操作
                delay(3000)
                "操作成功"
            }.await()   // 当程序调用await方法时，await方法会阻塞当前协程，直到获取执行结果。
            Log.d(TAG, result)
        }
    }
}